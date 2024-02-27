import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void convolution(int[][] matrix, int[][] conv, int[][] output, int row, int col, int i, int j,
            int k) {
        int sum = 0;
        for (int x = 0; x < k; x++) {
            for (int y = 0; y < k; y++) {
                int correctX = (i + x - k / 2 > 0) ? i + x - k / 2 : 0;
                correctX = (correctX < row) ? correctX : row - 1;
                int correctY = (j + y - k / 2 > 0) ? j + y - k / 2 : 0;
                correctY = (correctY < col) ? correctY : col - 1;
                sum += matrix[correctX][correctY] * conv[x][y];
            }
        }
        output[i][j] = sum;
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
    public static long execute(int row, int col, int k, int p) {
        // measure time taken
        String matrixFileName = "matrix_" + row + "x" + col + ".txt";
        String convFilename = "matrix_" + k + "x" + k + ".txt";
        // read matrices from files
        int[][] matrix = readMatrixFromFile(row, col, matrixFileName);
        int[][] conv = readMatrixFromFile(k, k, convFilename);
        int[][] output = new int[row][col];
        // implement thread creation and joining
        Thread[] threads = new Thread[p];
        for (int i = 0; i < p; i++) {
            threads[i] = new Thread(() -> {
                for (int th = 0; th < p; th++) {
                    // int start = th * row / p;
                    // int end = (th + 1) * row / p;
                    int start = th * col / p;
                    int end = (th + 1) * col / p;
                    // for (int x = start; x < end; x++) {
                    // for (int y = 0; y < col; y++) {
                    // convolution(matrix, conv, output, row, col, x, y, k);
                    // }
                    // }
                    for (int x = 0; x < row; x++) {
                        for (int y = start; y < end; y++) {
                            convolution(matrix, conv, output, row, col, x, y, k);
                        }
                    }
                }
            });
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < p; i++) {
            threads[i].start();
        }
        for (int i = 0; i < p; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    public static void main(String[] args) {
        System.out.println(execute(10, 10, 3, 1));
    }
}
