#include <iostream>
#include <fstream>
#include <cmath>
#include <thread>
#include <chrono>
using namespace std;
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
void convolutionPtrs(int **matrix, int **conv, int **output, int row, int col, int i, int j, int k)
{
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
void wrapperConvPtrs(int **matrix, int **conv, int **output, int row, int col, int i, int k, int p)
{
    int start = i * row / p;
    int end = (i + 1) * row / p;
    // go by cols, not rows
    // int start = i * col / p;
    // int end = (i + 1) * col / p;

    // convolution centered on cell i,j; conv is kxk
    for (int x = start; x < end; x++)
    {
        for (int y = 0; y < col; y++)
        {
            convolutionPtrs(matrix, conv, output, row, col, x, y, k);
        }
    }
}
void wrapperConv(void *matrixM, void *convM, void *outputM, int row, int col, int i, int k, int p)
{
    int start = i * row / p;
    int end = (i + 1) * row / p;
    // go by cols, not rows
    // int start = i * col / p;
    // int end = (i + 1) * col / p;
    // convolution centered on cell i,j; conv is kxk
    for (int x = start; x < end; x++)
    {
        for (int y = 0; y < col; y++)
        {
            convolution(matrixM, convM, outputM, row, col, x, y, k);
        }
    }
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
// read from given file
void readFile(void *object, int row, int col, string fileName)
{
    // casting the object to int matrix
    int(*matrix)[col] = (int(*)[col])object;
    ifstream file;
    file.open(fileName);
    if (file.is_open())
    {
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; ++j)
                file >> matrix[i][j];
        }
        file.close();
    }
    else
    {
        cout << "Unable to open file" << endl;
        return;
    }
}
// wrapper function to execute the program
int64_t executeStaticAlloc(int row, int col, int k, int p)
{
    int matrix[row][col], conv[k][k], output[row][col];
    // init output with 0
    for (int i = 0; i < row; i++)
        for (int j = 0; j < col; j++)
            output[i][j] = 0;
    string matrixFilename = "matrix_" + to_string(row) + "x" + to_string(col) + ".txt";
    string convFilename = "matrix_" + to_string(k) + "x" + to_string(k) + ".txt";
    // reading from files
    readFile(matrix, row, col, matrixFilename);
    readFile(conv, k, k, convFilename);

    // Initiating the p threads
    thread threads[p];
    // each thread will have to do row/p rows
    // translate matrices to void pointers
    void *matrixPtr = (void *)matrix;
    void *convPtr = (void *)conv;
    void *outputPtr = (void *)output;
    auto start = chrono::high_resolution_clock::now();
    for (int i = 0; i < p; i++)
    {
        threads[i] = thread(wrapperConv, matrixPtr, convPtr, outputPtr, row, col, i, k, p);
    }
    for (int i = 0; i < p; i++)
        threads[i].join();
    auto end = chrono::high_resolution_clock::now();

    // print output
    int64_t time = chrono::duration_cast<chrono::nanoseconds>(end - start).count();
    // cout << "Static allocation: " << row << "x" << col << " matrix, " << k << "x" << k << " convolution, " << p << " threads, time: " << time << "ns"
    //      << " or " << time / 1e6 << "ms" << endl;
    return time;
}
int64_t executeDynamicAlloc(int row, int col, int k, int p)
{
    int **matrix, **conv, **output;
    matrix = new int *[row];
    conv = new int *[k];
    output = new int *[row];
    // for (int i = 0; i < row; i++)
    // {
    //     matrix[i] = new int[col];
    //     output[i] = new int[col];
    // }
    // for (int i = 0; i < k; i++)
    // {
    //     conv[i] = new int[k];
    // }
    // init output with 0
    for (int i = 0; i < row; i++)
        output[i] = new int[col];
    for (int i = 0; i < row; i++)
        for (int j = 0; j < col; j++)
            output[i][j] = 0;
    string matrixFilename = "matrix_" + to_string(row) + "x" + to_string(col) + ".txt";
    string convFilename = "matrix_" + to_string(k) + "x" + to_string(k) + ".txt";
    // reading from files
    readFilePtrs(matrix, row, col, matrixFilename);
    readFilePtrs(conv, k, k, convFilename);
    thread threads[p];
    // each thread will have to do row/p rows
    // translate matrices to void pointers
    auto start = chrono::high_resolution_clock::now();
    for (int i = 0; i < p; i++)
    {
        threads[i] = thread(wrapperConvPtrs, matrix, conv, output, row, col, i, k, p);
    }
    for (int i = 0; i < p; i++)
        threads[i].join();
    auto end = chrono::high_resolution_clock::now();
    int64_t time = chrono::duration_cast<chrono::nanoseconds>(end - start).count();
    // clear memory
    for (int i = 0; i < row; i++)
    {
        delete[] matrix[i];
        delete[] output[i];
    }
    for (int i = 0; i < k; i++)
    {
        delete[] conv[i];
    }
    delete[] matrix;
    delete[] conv;
    delete[] output;
    return time;
}

void executeStatic()
{
    int64_t time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeStaticAlloc(10, 10, 3, 4);
    }
    cout << "Configuration " << 10 << "x" << 10 << " matrix, " << 3 << "x" << 3 << " convolution, " << 4 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeStaticAlloc(1000, 1000, 5, 1);
    }
    cout << "Configuration " << 1000 << "x" << 1000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 1 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeStaticAlloc(1000, 1000, 5, 2);
    }
    cout << "Configuration " << 1000 << "x" << 1000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 2 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeStaticAlloc(1000, 1000, 5, 4);
    }
    cout << "Configuration " << 1000 << "x" << 1000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 4 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeStaticAlloc(1000, 1000, 5, 8);
    }
    cout << "Configuration " << 1000 << "x" << 1000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 8 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeStaticAlloc(1000, 1000, 5, 16);
    }
    cout << "Configuration " << 1000 << "x" << 1000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 16 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeStaticAlloc(10, 10000, 5, 1);
    }
    cout << "Configuration " << 10 << "x" << 10000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 1 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeStaticAlloc(10, 10000, 5, 2);
    }
    cout << "Configuration " << 10 << "x" << 10000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 2 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeStaticAlloc(10, 10000, 5, 4);
    }
    cout << "Configuration " << 10 << "x" << 10000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 4 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeStaticAlloc(10, 10000, 5, 8);
    }
    cout << "Configuration " << 10 << "x" << 10000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 8 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeStaticAlloc(10, 10000, 5, 16);
    }
    cout << "Configuration " << 10 << "x" << 10000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 16 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeStaticAlloc(10000, 10, 5, 1);
    }
    cout << "Configuration " << 10000 << "x" << 10 << " matrix, " << 5 << "x" << 5 << " convolution, " << 1 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeStaticAlloc(10000, 10, 5, 2);
    }
    cout << "Configuration " << 10000 << "x" << 10 << " matrix, " << 5 << "x" << 5 << " convolution, " << 2 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeStaticAlloc(10000, 10, 5, 4);
    }
    cout << "Configuration " << 10000 << "x" << 10 << " matrix, " << 5 << "x" << 5 << " convolution, " << 4 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeStaticAlloc(10000, 10, 5, 8);
    }
    cout << "Configuration " << 10000 << "x" << 10 << " matrix, " << 5 << "x" << 5 << " convolution, " << 8 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time +=
            executeStaticAlloc(10000, 10, 5, 16);
    }
    cout << "Configuration " << 10000 << "x" << 10 << " matrix, " << 5 << "x" << 5 << " convolution, " << 16 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << endl;
}

void executeDynamic()
{
    int64_t time = 0;
    for (int i = 0; i < 10; i++)
    {
    }
    time += executeDynamicAlloc(10, 10, 3, 4);
    cout << "Configuration " << 10 << "x" << 10 << " matrix, " << 3 << "x" << 3 << " convolution, " << 4 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeDynamicAlloc(1000, 1000, 5, 1);
    }
    cout << "Configuration " << 1000 << "x" << 1000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 1 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeDynamicAlloc(1000, 1000, 5, 2);
    }
    cout << "Configuration " << 1000 << "x" << 1000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 2 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeDynamicAlloc(1000, 1000, 5, 4);
    }
    cout << "Configuration " << 1000 << "x" << 1000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 4 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeDynamicAlloc(1000, 1000, 5, 8);
    }
    cout << "Configuration " << 1000 << "x" << 1000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 8 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeDynamicAlloc(1000, 1000, 5, 16);
    }
    cout << "Configuration " << 1000 << "x" << 1000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 16 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeDynamicAlloc(10, 10000, 5, 1);
    }
    cout << "Configuration " << 10 << "x" << 10000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 1 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeDynamicAlloc(10, 10000, 5, 2);
    }
    cout << "Configuration " << 10 << "x" << 10000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 2 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeDynamicAlloc(10, 10000, 5, 4);
    }
    cout << "Configuration " << 10 << "x" << 10000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 4 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeDynamicAlloc(10, 10000, 5, 8);
    }
    cout << "Configuration " << 10 << "x" << 10000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 8 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeDynamicAlloc(10, 10000, 5, 16);
    }
    cout << "Configuration " << 10 << "x" << 10000 << " matrix, " << 5 << "x" << 5 << " convolution, " << 16 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeDynamicAlloc(10000, 10, 5, 1);
    }
    cout << "Configuration " << 10000 << "x" << 10 << " matrix, " << 5 << "x" << 5 << " convolution, " << 1 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeDynamicAlloc(10000, 10, 5, 2);
    }
    cout << "Configuration " << 10000 << "x" << 10 << " matrix, " << 5 << "x" << 5 << " convolution, " << 2 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeDynamicAlloc(10000, 10, 5, 4);
    }
    cout << "Configuration " << 10000 << "x" << 10 << " matrix, " << 5 << "x" << 5 << " convolution, " << 4 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time += executeDynamicAlloc(10000, 10, 5, 8);
    }
    cout << "Configuration " << 10000 << "x" << 10 << " matrix, " << 5 << "x" << 5 << " convolution, " << 8 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << "ms" << endl;
    time = 0;
    for (int i = 0; i < 10; i++)
    {
        time +=
            executeDynamicAlloc(10000, 10, 5, 16);
    }
    cout << "Configuration " << 10000 << "x" << 10 << " matrix, " << 5 << "x" << 5 << " convolution, " << 16 << " threads, average time: " << time / 10 << "ns"
         << " or " << time / 10 / 1e6 << endl;
}
int main()
{

    // executeStatic();
    // executeDynamic();
    cout << executeDynamicAlloc(1000, 1000, 5, 4) / 1e6;

    return 0;
}