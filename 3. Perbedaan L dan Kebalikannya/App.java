import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    private static final int MIN_MATRIX_SIZE_WITH_L = 3;

    public static void main(String[] args) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File input.txt tidak dapat dibaca");
            return;
        }
        int[][] matrix = readMatrix(scanner);
        scanner.close();

        if (matrix == null) {
            System.out.println("Data matriks tidak valid");
            return;
        }
        printResults(matrix);
    }

    private static int[][] readMatrix(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            return null;
        }
        int size;
        try {
            size = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return null;
        }
        if (size <= 0) {
            return null;
        }
        int[][] matrix = new int[size][size];
        for (int row = 0; row < size; row++) {
            if (!scanner.hasNextLine()) {
                return null;
            }
            String[] values = scanner.nextLine().trim().split("\\s+");
            if (values.length != size) {
                return null;
            }
            for (int column = 0; column < size; column++) {
                try {
                    matrix[row][column] = Integer.parseInt(values[column]);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return matrix;
    }

    private static void printResults(int[][] matrix) {
        int size = matrix.length;
        if (size < MIN_MATRIX_SIZE_WITH_L) {
            printSmallMatrixResults(matrix);
            return;
        }

        int nilaiL = calculateL(matrix);
        int nilaiKebalikan = calculateReverseL(matrix);
        int nilaiTengah = calculateCenter(matrix);
        int perbedaan = Math.abs(nilaiL - nilaiKebalikan);
        int dominan = perbedaan == 0 ? nilaiTengah : Math.max(nilaiL, nilaiKebalikan);

        System.out.println("Nilai L: " + nilaiL);
        System.out.println("Nilai Kebalikan L: " + nilaiKebalikan);
        System.out.println("Nilai Tengah: " + nilaiTengah);
        System.out.println("Perbedaan: " + perbedaan);
        System.out.println("Dominan: " + dominan);
    }

    private static void printSmallMatrixResults(int[][] matrix) {
        int total = sumMatrix(matrix);
        System.out.println("Nilai L: Tidak Ada");
        System.out.println("Nilai Kebalikan L: Tidak Ada");
        System.out.println("Nilai Tengah: " + total);
        System.out.println("Perbedaan: Tidak Ada");
        System.out.println("Dominan: " + total);
    }

    private static int calculateL(int[][] matrix) {
        int lastIndex = matrix.length - 1;
        int result = 0;
        for (int row = 0; row < matrix.length; row++) {
            result += matrix[row][0];
        }
        for (int column = 1; column < lastIndex; column++) {
            result += matrix[lastIndex][column];
        }
        return result;
    }

    private static int calculateReverseL(int[][] matrix) {
        int lastIndex = matrix.length - 1;
        int result = 0;
        for (int row = 0; row < matrix.length; row++) {
            result += matrix[row][lastIndex];
        }
        for (int column = 1; column < lastIndex; column++) {
            result += matrix[0][column];
        }
        return result;
    }

    private static int calculateCenter(int[][] matrix) {
        int size = matrix.length;
        if (size % 2 == 1) {
            return matrix[size / 2][size / 2];
        }
        int center = size / 2;
        return matrix[center - 1][center - 1] + matrix[center - 1][center]
                + matrix[center][center - 1] + matrix[center][center];
    }

    private static int sumMatrix(int[][] matrix) {
        int result = 0;
        for (int[] row : matrix) {
            for (int value : row) {
                result += value;
            }
        }
        return result;
    }
}
