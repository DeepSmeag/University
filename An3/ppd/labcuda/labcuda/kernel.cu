#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include <time.h>
#include <stdio.h>

#define MATRIX_SIZE 10
#define MASK_SIZE 3
#define TILE_WIDTH 10
#define NO_THREADS 5

__global__ void convolutionKernel(int* matrix, int* convolution)
{
    // Shared memory to store the matrix tile
    __shared__ int conv[MASK_SIZE][MASK_SIZE];
    int temp[3][MATRIX_SIZE];
    // Temporary vectors for convolution
    int startRow = threadIdx.x * (MATRIX_SIZE / NO_THREADS);
    int endRow = startRow + MATRIX_SIZE / NO_THREADS - 1;
    for (int i = 0; i < MASK_SIZE * MASK_SIZE; i++)
    {
        conv[i / MASK_SIZE][i % MASK_SIZE] = convolution[i];
    }
    for (int i = 0; i < MASK_SIZE; i++)
    {
        temp[0][i] = matrix[(startRow - 1 >= 0 ? startRow - 1 : 0) * MATRIX_SIZE + i];
        temp[1][i] = matrix[startRow * MATRIX_SIZE + i];
        temp[2][i] = matrix[(endRow + 1 < MATRIX_SIZE ? endRow + 1 : MATRIX_SIZE - 1) * MATRIX_SIZE + i];

	}
    __syncthreads();

    bool up = true;

    for (int i = startRow; i < endRow; i++)
    {
        if (up)
        {
            // using temp[0] us the "above" vector and temp[1] as the "current" vector
            for (int j = 0; j < MATRIX_SIZE; j++)
            {
                temp[1][j] = matrix[i * MATRIX_SIZE + j];
                // doing the convolution row by row
                int sum = 0;
                // first row
                for (int m = 0; m < 3; m++)
                {
                    int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
                    correctCol = (correctCol < MATRIX_SIZE ? correctCol : MATRIX_SIZE - 1);
                    sum += temp[0][correctCol] * conv[0][m];
                    sum += temp[1][correctCol] * conv[1][m];
                    sum += matrix[(i+1) * MATRIX_SIZE + correctCol] * conv[2][m];
                }
                matrix[i * MATRIX_SIZE + j] = sum;
            }
        }
        else
        {
            // using temp[0] us the "current" vector and temp[1] as the "above" vector
            for (int j = 0; j < MATRIX_SIZE; j++)
            {
                temp[0][j] = matrix[i * MATRIX_SIZE + j];
                // doing the convolution row by row
                int sum = 0;
                // first row
                for (int m = 0; m < 3; m++)
                {
                    int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
                    correctCol = (correctCol < MATRIX_SIZE ? correctCol : MATRIX_SIZE - 1);
                    sum += temp[1][correctCol] * conv[0][m];
                    sum += temp[0][correctCol] * conv[1][m];
                    sum += matrix[(i + 1) * MATRIX_SIZE + correctCol] * conv[2][m];
                }
                matrix[i * MATRIX_SIZE + j] = sum;
            }
        }

        up = !up;
    }
    // Synchronize to make sure all threads have finished convolution
    __syncthreads();
    /*if (!up && threadIdx.x == 0) {
        printf("Thread %d\n", threadIdx.x);
        for (int j = 0; j < MATRIX_SIZE; j++) {
            printf("%d ", temp[1][j]);
        }
        printf("\n");
        for (int j = 0; j < MATRIX_SIZE; j++) {
            printf("%d ", temp[0][j]);
        }
        printf("-------------\n");
    }*/
    // doing the last row, that is end - 1
    for (int j = 0; j < MATRIX_SIZE; j++)
    {
        //temp[up? 1 : 0][j] = matrix[endRow * MATRIX_SIZE + j];
        // same process, we know up's value
        int sum = 0;
        for (int m = 0; m < 3; m++)
        {
            int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
            correctCol = (correctCol < MATRIX_SIZE ? correctCol : MATRIX_SIZE - 1);
            sum += temp[up ? 0 : 1][correctCol] * conv[0][m];
            //sum += temp[up ? 1 : 0][correctCol] * conv[1][m];
            sum += matrix[endRow * MATRIX_SIZE + correctCol] * conv[1][m];
            sum += temp[2][correctCol] * conv[2][m];
        }
        matrix[endRow * MATRIX_SIZE + j] = sum;
    }
   

    
}

int main()
{
    const int matrixSize = MATRIX_SIZE;
    const int matrixTotalSize = matrixSize * matrixSize;
    int matrix[matrixTotalSize];
    int convolution[9] = { 0, 0, 0, 0, 2, 0, 0, 0, 0 };
    clock_t start_time = clock();
    // Initialize matrix with values for testing
    for (int i = 0; i < matrixTotalSize; ++i) {
        matrix[i] = i;
    }

    int* dev_matrix = 0;
    int* dev_conv = 0;
    cudaMalloc((void**)&dev_matrix, matrixTotalSize * sizeof(int));
    cudaMalloc((void**)&dev_conv, 3 * 3 * sizeof(int));
    cudaMemcpy(dev_matrix, matrix, matrixTotalSize * sizeof(int), cudaMemcpyHostToDevice);
    cudaMemcpy(dev_conv, convolution, 3 * 3 * sizeof(int), cudaMemcpyHostToDevice);

    // Launch the convolution kernel with 1 block of 10 threads
    convolutionKernel << <1, NO_THREADS >> > (dev_matrix, dev_conv);
    cudaDeviceSynchronize();

    // Copy the result back to the host
    cudaMemcpy(matrix, dev_matrix, matrixTotalSize * sizeof(int), cudaMemcpyDeviceToHost);
    clock_t end_time = clock();
    double elapsed_time = ((double)(end_time - start_time)) / CLOCKS_PER_SEC;

    // Print the elapsed time
    printf("Elapsed time: %f seconds\n", elapsed_time);
    // Print the result
    for (int i = 0; i < matrixSize; ++i) {
        for (int j = 0; j < matrixSize; ++j) {
            printf("%d\t", matrix[i * matrixSize + j]);
        }
        printf("\n");
    }

    cudaFree(dev_matrix);

    return 0;
}
