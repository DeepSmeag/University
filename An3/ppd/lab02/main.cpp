#include <iostream>
#include <mutex>
#include <condition_variable>
#include <fstream>
#include <cmath>
#include <thread>
#include <chrono>
#include <vector>
using namespace std;
class my_barrier
{

public:
    my_barrier(int count)
        : thread_count(count), counter(0), waiting(0)
    {
    }

    void wait()
    {
        // fence mechanism
        std::unique_lock<std::mutex> lk(m);
        ++counter;
        ++waiting;
        cv.wait(lk, [&]
                { return counter >= thread_count; });
        cv.notify_one();
        --waiting;
        if (waiting == 0)
        {
            // reset barrier
            counter = 0;
        }
        lk.unlock();
    }

private:
    std::mutex m;
    std::condition_variable cv;
    int counter;
    int waiting;
    int thread_count;
};
void convolution(void *matrixM, void *convM, void *outputM, int row, int col, int i, int j, int k)
{
    // casting the object to int matrix
    int(*matrix)[col] = (int(*)[col])matrixM;
    int(*conv)[k] = (int(*)[k])convM;
    int(*output)[col] = (int(*)[col])outputM;
    // convolution centered on cell i,j; conv is kxk
    for (int x = 0; x < k; x++)
    {
        for (int y = 0; y < k; y++)
        {
            int correctX = (i + x - k / 2 > 0) ? i + x - k / 2 : 0;
            correctX = (correctX < row) ? correctX : row - 1;
            int correctY = (j + y - k / 2 > 0) ? j + y - k / 2 : 0;
            correctY = (correctY < col) ? correctY : col - 1;
            output[i][j] += matrix[correctX][correctY] * conv[x][y];
        }
    }
}
void convolution(int **matrix, int **conv, int row, int col, int start, int end, my_barrier &barrier)
{
    // matrix is the input matrix, the output is written in it directly
    // conv is the convolution matrix, I only read from it
    // row and col are the dimensions of the matrix
    // start and end are the rows start and end range that the thread will work on
    // defining my 3 temporary vectors to help
    int **temp = new int *[row];
    for (int i = 0; i < 3; i++)
        temp[i] = new int[col];
    // temp[0] and temp[1] will alternate for current and previous row; temp[2] is the row directly beneath the end row
    // copying the rows and having a barrier for it

    for (int j = 0; j < col; j++)
    {
        temp[0][j] = matrix[start > 0 ? (start - 1) : start][j];
        temp[2][j] = matrix[end < (row - 1) ? (end + 1) : end][j];
    }
    // barrier to make sure all threads have copied the rows
    barrier.wait();
    // can now start doing the convolution itself
    bool up = true;
    for (int i = start; i < end - 1; i++)
    {
        if (up)
        {
            // using temp[0] us the "above" vector and temp[1] as the "current" vector
            for (int j = 0; j < col; j++)
            {
                temp[1][j] = matrix[i][j];
                // doing the convolution row by row
                int sum = 0;
                // first row
                for (int m = 0; m < 3; m++)
                {
                    int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
                    correctCol = (correctCol < col ? correctCol : col - 1);
                    sum += temp[0][correctCol] * conv[0][m];
                    sum += temp[1][correctCol] * conv[1][m];
                    sum += matrix[start + 1][correctCol] * conv[2][m];
                }
                // // second row
                // for (int m = 0; m < 3; m++)
                // {
                // }
                // // third row
                // for (int m = 0; m < 3; m++)
                // {
                // }
                matrix[i][j] = sum;
                // updating the temp vectors
            }
        }
        else
        {
            // using temp[0] us the "current" vector and temp[1] as the "above" vector
            for (int j = 0; j < col; j++)
            {
                temp[0][j] = matrix[i][j];
                // doing the convolution row by row
                int sum = 0;
                // first row
                for (int m = 0; m < 3; m++)
                {
                    int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
                    correctCol = (correctCol < col ? correctCol : col - 1);
                    sum += temp[1][correctCol] * conv[0][m];
                    sum += temp[0][correctCol] * conv[1][m];
                    sum += matrix[start + 1][correctCol] * conv[2][m];
                }
                // second row
                // for (int m = 0; m < 3; m++)
                // {
                // }
                // // third row
                // for (int m = 0; m < 3; m++)
                // {
                // }
                matrix[i][j] = sum;
            }
        }

        up = !up;
    }
    // doing the last row, that is end - 1
    for (int j = 0; j < col; j++)
    {
        // same process, we know up's value
        int sum = 0;
        for (int m = 0; m < 3; m++)
        {
            int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
            correctCol = (correctCol < col ? correctCol : col - 1);
            sum += temp[up ? 0 : 1][correctCol] * conv[0][m];
            sum += temp[up ? 1 : 0][correctCol] * conv[1][m];
            sum += temp[2][correctCol] * conv[2][m];
        }
        // for (int m = 0; m < 3; m++)
        // {
        // }
        // for (int m = 0; m < 3; m++)
        // {
        // }
        matrix[end][j] = sum;
    }

    // clear memory
    for (int i = 0; i < 3; i++)
        delete[] temp[i];
    delete[] temp;
}

void readFilePtrs(int **object, int row, int col, string fileName)
{
    ifstream file;
    file.open(fileName);
    if (file.is_open())
    {
        for (int i = 0; i < row; i++)
        {
            object[i] = new int[col];
            for (int j = 0; j < col; ++j)
                file >> object[i][j];
        }
        file.close();
    }
    else
    {
        cout << "Unable to open file" << endl;
        return;
    }
}

int main(int argc, char **argv)
{
    int row, col, p;
    row = atoi(argv[1]);
    col = atoi(argv[2]);
    p = atoi(argv[3]);
    int **matrix, **conv;
    matrix = new int *[row];
    conv = new int *[3];
    for (int i = 0; i < row; i++)
        matrix[i] = new int[col];
    for (int i = 0; i < 3; i++)
        conv[i] = new int[3];
    string matrixFilename = "matrix_" + to_string(row) + "x" + to_string(col) + ".txt";
    string convFilename = "matrix_" + to_string(3) + "x" + to_string(3) + ".txt";
    // reading from files
    readFilePtrs(matrix, row, col, matrixFilename);
    readFilePtrs(conv, 3, 3, convFilename);
    // initializing the barrier and threads
    my_barrier barrier(p);
    std::vector<std::thread> threads;
    // start the timer
    auto start = std::chrono::high_resolution_clock::now();
    for (int i = 0; i < p; ++i)
    {
        int start = i * row / p;
        int end = (i + 1) * row / p - 1;
        threads.push_back(thread([&]()
                                 { convolution(matrix, conv, row, col, start, end, std::ref(barrier)); }));
    }

    for (std::thread &t : threads)
    {
        t.join();
    }
    // end the timer
    auto end = std::chrono::high_resolution_clock::now();

    for (int i = 0; i < row; i++)
    {
        delete[] matrix[i];
    }
    for (int i = 0; i < 3; i++)
    {
        delete[] conv[i];
    }
    delete[] matrix;
    delete[] conv;
    // executeStatic();
    // executeDynamic();
    cout << std::chrono::duration_cast<std::chrono::nanoseconds>(end - start).count() / 1e6 << " ms" << endl;
    // write matrix to file "output.txt"

    return 0;
}