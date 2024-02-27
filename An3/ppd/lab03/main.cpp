#include <mpi.h>
#include <stdio.h>
#include <iostream>
#include <chrono>
using namespace std;
/* MPI commands:
    rc = MPI_Init(NULL, NULL); - can use argc and argv if needed; rc is return code, MPI_SUCCESS is what we want
    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank); - get rank of current process in world_rank
    MPI_Comm_size(MPI_COMM_WORLD, &world_size); - get number of processes in world_size
    MPI_Abort(MPI_COMM_WORLD, errcode); - abort all processes and return errcode


    Blocking comms:
    MPI_Send(&data, count, MPI_INT, dest, tag, MPI_COMM_WORLD); - send data to dest process with tag
    MPI_Recv(&data, count, MPI_INT, source, tag, MPI_COMM_WORLD, &status); - receive data from source process with tag

    Non-blocking comms:
    MPI_Isend(&data, count, MPI_INT, dest, tag, MPI_COMM_WORLD, &request); - send data to dest process with tag
    MPI_Irecv(&data, count, MPI_INT, source, tag, MPI_COMM_WORLD, &request); - receive data from source process with tag

    MPI_Sendrecv(&send_data, send_count, MPI_INT, dest, send_tag, &recv_data, recv_count, MPI_INT, source, recv_tag, MPI_COMM_WORLD, &status); - send and receive data in one command

    Other comms: (reductions are blocking)
    MPI_Barrier(MPI_COMM_WORLD); - wait for all processes to reach this point
    MPI_Bcast(&data, count, MPI_INT, root, MPI_COMM_WORLD); - broadcast data from root process to all other processes
    MPI_Reduce(&data, &result, count, MPI_INT, MPI_SUM, root, MPI_COMM_WORLD); - reduce data from all processes to root process
    MPI_Allreduce(&data, &result, count, MPI_INT, MPI_SUM, MPI_COMM_WORLD); - reduce data from all processes to all processes
    MPI_Finalize();

*/
int *readFile(const char *fileName, int size)
{
    FILE *file = fopen(fileName, "r");
    if (file == NULL)
    {
        cout << "Error opening file" << endl;
        exit(1);
    }
    int *arr = new int[size * size];
    for (int i = 0; i < size * size; i++)
    {
        fscanf(file, "%d", &arr[i]);
    }
    fclose(file);
    return arr;
}
void writeFile(const char *fileName, int *arr, int size)
{
    FILE *file = fopen(fileName, "w");
    if (file == NULL)
    {
        cout << "Error opening file" << endl;
        exit(1);
    }
    for (int i = 0; i < size * size; i++)
    {
        fprintf(file, "%d ", arr[i]);
        if ((i + 1) % size == 0)
            fprintf(file, "\n");
    }
    fclose(file);
}
int main(int argc, char **argv)
{
    MPI_Init(NULL, NULL);
    int world_rank;
    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);
    int world_size;
    MPI_Comm_size(MPI_COMM_WORLD, &world_size);
    if (world_rank == 0)
    {

        int *conv = readFile("matrix_3x3.txt", 3);
        MPI_Bcast(conv, 9, MPI_INT, 0, MPI_COMM_WORLD);
        int *input = new int[1000 * 1000];
        // for each process, read 1000 / (world_size-1) rows from input file
        FILE *file = fopen("matrix_1000x1000.txt", "r");
        // start the timer
        auto start = chrono::high_resolution_clock::now();
        for (int i = 1; i < world_size; i++)
        {
            // read 1000 / (world_size-1) rows from input file
            // send to process i
            for (int j = (i - 1) * 1000 / (world_size - 1); j < i * 1000 / (world_size - 1); j++)
            {
                for (int k = 0; k < 1000; k++)
                {
                    fscanf(file, "%d", &input[j * 1000 + k]);
                }
            }
            // send the portion from the input to process i
            MPI_Send(&input[((i - 1) * 1000 / (world_size - 1)) * 1000], 1000 * 1000 / (world_size - 1), MPI_INT, i, 0, MPI_COMM_WORLD);
        }
        // write all the input to a file "proc_0.txt"
        fclose(file);
        int *output = new int[1000 * 1000];
        // for each process, receive 1000 / (world_size-1) rows and put them in the output vector
        for (int i = 1; i < world_size; i++)
        {
            MPI_Recv(&output[((i - 1) * 1000 / (world_size - 1)) * 1000], 1000 * 1000 / (world_size - 1), MPI_INT, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }
        auto stop = chrono::high_resolution_clock::now();
        // write output to file
        writeFile("output.txt", output, 1000);
        // stop the timer

        auto duration = chrono::duration_cast<chrono::nanoseconds>(stop - start);
        cout << duration.count() / 1e6 << " ms" << endl;
        delete[] conv;
        delete[] input;
        delete[] output;
    }
    else
    {
        // receive the convolution matrix
        int conv[3][3];
        MPI_Bcast(conv, 9, MPI_INT, 0, MPI_COMM_WORLD);
        // we now have the convolution matrix, so we need to receive part of the input matrix
        int input[1000 / (world_size - 1)][1000];
        MPI_Recv(input, 1000 * 1000 / (world_size - 1), MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        // now we have the correct input, we still need to apply the convolution
        // for that, we need 3 extra vectors, 2 of which we borrow from our neighbours
        int tmp[3][1000];
        // odd processes borrow from even processes
        if (world_rank % 2 == 1)
        {
            // receive from process world_rank + 1
            MPI_Recv(tmp[2], 1000, MPI_INT, world_rank + 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            // send last row to process world_rank + 1
            MPI_Send(input[1000 / (world_size - 1) - 1], 1000, MPI_INT, world_rank + 1, 0, MPI_COMM_WORLD);
        }
        else
        {
            // send to process world_rank - 1
            MPI_Send(input, 1000, MPI_INT, world_rank - 1, 0, MPI_COMM_WORLD);
            // receive from process world_rank - 1
            MPI_Recv(tmp[0], 1000, MPI_INT, world_rank - 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }
        // first and last process don't do this now
        if (world_rank > 1 && world_rank < world_size - 1)
        {
            // even processes borrow from the next process
            if (world_rank % 2 == 0)
            {
                // receive from process world_rank + 1
                MPI_Recv(tmp[2], 1000, MPI_INT, world_rank + 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                // send last row to process world_rank + 1
                MPI_Send(input[1000 / (world_size - 1) - 1], 1000, MPI_INT, world_rank + 1, 0, MPI_COMM_WORLD);
            }
            else
            {
                // send to process world_rank - 1
                MPI_Send(input, 1000, MPI_INT, world_rank - 1, 0, MPI_COMM_WORLD);
                // receive from process world_rank - 1
                MPI_Recv(tmp[0], 1000, MPI_INT, world_rank - 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            }
        }
        else
        {
            if (world_rank == 1)
            {
                // tmp[0] is the first row of the input
                for (int i = 0; i < 1000; i++)
                {
                    tmp[0][i] = input[0][i];
                }
            }
            else
            {
                // tmp[2] is the last row of the input
                for (int i = 0; i < 1000; i++)
                {
                    tmp[2][i] = input[1000 / (world_size - 1) - 1][i];
                }
            }
        }
        // now each process has their tmp[0] and [2] filled, the [1] is for the current row and it will alternate between the first two
        // starting to apply convolution
        int output[1000 / (world_size - 1)][1000];
        // for each row in the input
        bool up = true;
        for (int i = 0; i < 1000 / (world_size - 1) - 1; i++)
        {
            if (up)
            {
                // for each column in the input
                for (int j = 0; j < 1000; j++)
                {
                    // copy current element to tmp[1][j]
                    tmp[1][j] = input[i][j];
                    // doing convolution
                    int sum = 0;
                    for (int m = 0; m < 3; m++)
                    {
                        int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
                        correctCol = (correctCol < 1000 ? correctCol : 1000 - 1);
                        sum += tmp[0][correctCol] * conv[0][m];
                        sum += tmp[1][correctCol] * conv[1][m];
                        sum += input[i + 1][correctCol] * conv[2][m];
                    }
                    output[i][j] = sum;
                }
            }
            else
            {
                // using temp[0] us the "current" vector and temp[1] as the "above" vector
                for (int j = 0; j < 1000; j++)
                {
                    tmp[0][j] = input[i][j];
                    // doing the convolution row by row
                    int sum = 0;
                    // first row
                    for (int m = 0; m < 3; m++)
                    {
                        int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
                        correctCol = (correctCol < 1000 ? correctCol : 1000 - 1);
                        sum += tmp[1][correctCol] * conv[0][m];
                        sum += tmp[0][correctCol] * conv[1][m];
                        sum += input[i + 1][correctCol] * conv[2][m];
                    }

                    output[i][j] = sum;
                }
            }
            up = !up;
        }
        // doing the last row, that is end - 1
        for (int j = 0; j < 1000; j++)
        {
            // same process, we know up's value
            int sum = 0;
            for (int m = 0; m < 3; m++)
            {
                int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
                correctCol = (correctCol < 1000 ? correctCol : 1000 - 1);
                sum += tmp[up ? 0 : 1][correctCol] * conv[0][m];
                sum += tmp[up ? 1 : 0][correctCol] * conv[1][m];
                sum += tmp[2][correctCol] * conv[2][m];
            }

            output[1000 / (world_size - 1) - 1][j] = sum;
        }
        // send to process 0
        MPI_Send(output, 1000 * 1000 / (world_size - 1), MPI_INT, 0, 0, MPI_COMM_WORLD);
        // don't need to clear memory cause it was declared locally
    }

    MPI_Finalize();
    return 0;
}