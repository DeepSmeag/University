import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

public class Main {
    private static class MyBarrier {
        private final CyclicBarrier barrier;

        public MyBarrier(int count) {
            barrier = new CyclicBarrier(count);
        }

        public void waitBarrier() {
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void convolution(int[][] matrix, int[][] conv, int row, int col, int start, int end,
            MyBarrier barrier) {
        int[][] temp = new int[3][col];

        for (int j = 0; j < col; j++) {
            temp[0][j] = matrix[start > 0 ? (start - 1) : start][j];
            temp[2][j] = matrix[end < (row - 1) ? (end + 1) : end][j];
        }

        barrier.waitBarrier();
        boolean up = true;
        for (int i = start; i < end - 1; i++) {
            if (up) {
                // using temp[0] us the "above" vector and temp[1] as the "current" vector
                for (int j = 0; j < col; j++) {
                    temp[1][j] = matrix[i][j];
                    // doing the convolution row by row
                    int sum = 0;
                    // first row
                    for (int m = 0; m < 3; m++) {
                        int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
                        correctCol = (correctCol < col ? correctCol : col - 1);
                        sum += temp[0][correctCol] * conv[0][m];
                        sum += temp[1][correctCol] * conv[1][m];
                        sum += matrix[start + 1][correctCol] * conv[2][m];
                    }
                    // // second row
                    // for (int m = 0; m < 3; m++) {
                    // int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
                    // correctCol = (correctCol < col ? correctCol : col - 1);
                    // }
                    // // third row
                    // for (int m = 0; m < 3; m++) {
                    // int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
                    // correctCol = (correctCol < col ? correctCol : col - 1);
                    // }
                    matrix[start][j] = sum;
                    // updating the temp vectors
                }
            } else {
                // using temp[0] us the "current" vector and temp[1] as the "above" vector
                for (int j = 0; j < col; j++) {
                    temp[0][j] = matrix[i][j];
                    // doing the convolution row by row
                    int sum = 0;
                    // first row
                    for (int m = 0; m < 3; m++) {
                        int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
                        correctCol = (correctCol < col ? correctCol : col - 1);
                        sum += temp[1][correctCol] * conv[0][m];
                        sum += temp[0][correctCol] * conv[1][m];
                        sum += matrix[start + 1][correctCol] * conv[2][m];
                    }
                    // // second row
                    // for (int m = 0; m < 3; m++) {
                    // int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
                    // correctCol = (correctCol < col ? correctCol : col - 1);
                    // }
                    // // third row
                    // for (int m = 0; m < 3; m++) {
                    // int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
                    // correctCol = (correctCol < col ? correctCol : col - 1);
                    // }
                    matrix[start][j] = sum;
                }
            }

            up = !up;
        }
        // doing the last row, that is end - 1
        for (int j = 0; j < col; j++) {
            // same process, we know up's value

            int sum = 0;
            for (int m = 0; m < 3; m++) {
                int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
                correctCol = (correctCol < col ? correctCol : col - 1);
                sum += temp[up ? 0 : 1][correctCol] * conv[0][m];
                sum += temp[up ? 1 : 0][correctCol] * conv[1][m];
                sum += temp[2][correctCol] * conv[2][m];
            }
            // for (int m = 0; m < 3; m++) {
            // int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
            // correctCol = (correctCol < col ? correctCol : col - 1);
            // }
            // for (int m = 0; m < 3; m++) {
            // int correctCol = (j + m - 1) < 0 ? j : (j + m - 1);
            // correctCol = (correctCol < col ? correctCol : col - 1);
            // }
            matrix[end][j] = sum;
        }
    }

    // function to read matrix from file given dimensions and file name, and return
    // it
    public static int[][] readMatrixFromFile(int rows, int columns, String fileName) {
        int[][] matrix = new int[rows][columns];
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < rows; i++) {
                String[] line = scanner.nextLine().trim().split(" ");
                for (int j = 0; j < columns; j++) {
                    matrix[i][j] = Integer.parseInt(line[j]);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return matrix;
    }

    // function to do main calculations given matrix dimensions, conv dimension and
    // no. of threads

    public static void main(String[] args) {
        int row = Integer.parseInt(args[0]);
        int col = Integer.parseInt(args[1]);
        int p = Integer.parseInt(args[2]);
        String matrixFilename = "matrix_" + row + "x" + col + ".txt";
        String convFilename = "matrix_3x3.txt";

        int[][] matrix = readMatrixFromFile(row, col, matrixFilename);
        int[][] conv = readMatrixFromFile(3, 3, convFilename);
        // int[][] matrix = new int[row][col];
        // int[][] conv = new int[3][3];

        MyBarrier barrier = new MyBarrier(p);
        Thread[] threads = new Thread[p];

        long start = System.nanoTime();

        for (int i = 0; i < p; i++) {
            int startRow = i * row / p;
            int endRow = (i + 1) * row / p - 1;
            threads[i] = new Thread(() -> convolution(matrix, conv, row, col, startRow, endRow, barrier));
            threads[i].start();
        }

        for (int i = 0; i < p; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.nanoTime();
        System.out.println((end - start) / 1000000 + " ms");
    }
}
