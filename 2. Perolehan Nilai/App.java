import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class App {  
    private static final int COMPONENT_COUNT = 6;
    private static final int REQUIRED_TOTAL_WEIGHT = 100;
    private static final String END_MARKER = "---";
    private static final String INVALID_DATA = "Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai";

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));

        String[] symbols = {"PA", "T", "K", "P", "UTS", "UAS"};
        String[] names = {"Partisipatif", "Tugas", "Kuis", "Proyek", "UTS", "UAS"};
        int[] weights = readWeights(scanner);
        if (sum(weights) != REQUIRED_TOTAL_WEIGHT) {
            System.out.println("Total bobot harus 100");
            scanner.close();
            return;
        }

        int[][] totals = readScores(scanner, symbols);
        scanner.close();

        printResults(names, weights, totals[0], totals[1]);
    }

    private static int[] readWeights(Scanner scanner) {
        int[] weights = new int[COMPONENT_COUNT];
        for (int i = 0; i < COMPONENT_COUNT && scanner.hasNextLine(); i++) {
            try {
                weights[i] = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                weights[i] = 0;
            }
        }
        return weights;
    }

    private static int[][] readScores(Scanner scanner, String[] symbols) {
        int[] totalWeights = new int[COMPONENT_COUNT];
        int[] totalScores = new int[COMPONENT_COUNT];
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals(END_MARKER)) {
                break;
            }
            processScoreLine(line, symbols, totalWeights, totalScores);
        }
        return new int[][] {totalWeights, totalScores};
    }

    private static void processScoreLine(String line, String[] symbols, int[] totalWeights, int[] totalScores) {
        String[] parts = line.split("\\|", -1);
        if (parts.length != 3) {
            System.out.println(INVALID_DATA);
            return;
        }

        int index = indexOf(symbols, parts[0].trim());
        if (index < 0) {
            System.out.println("Simbol tidak dikenal");
            return;
        }

        try {
            int weight = Integer.parseInt(parts[1].trim());
            int score = Integer.parseInt(parts[2].trim());
            totalWeights[index] += weight;
            totalScores[index] += clamp(score, 0, weight);
        } catch (NumberFormatException e) {
            System.out.println(INVALID_DATA);
        }
    }

    private static int clamp(int value, int minimum, int maximum) {
        return Math.max(minimum, Math.min(value, maximum));
    }

    private static int sum(int[] values) {
        int result = 0;
        for (int value : values) {
            result += value;
        }
        return result;
    }

    private static void printResults(String[] names, int[] weights, int[] totalWeights, int[] totalScores) {
        double finalScore = calculateFinalScore(names, weights, totalWeights, totalScores);
        StringBuilder output = new StringBuilder("Perolehan Nilai:\n");
        for (int i = 0; i < COMPONENT_COUNT; i++) {
            int percentage = calculatePercentage(totalWeights[i], totalScores[i]);
            double contribution = percentage * weights[i] / 100.0;
            output.append(String.format(Locale.US, ">> %s: %d/100 (%.2f/%d)%n", names[i], percentage, contribution, weights[i]));
        }
        output.append(String.format(Locale.US, "%n>> Nilai Akhir: %.2f%n", finalScore));
        output.append(">> Grade: ").append(grade(finalScore)).append("\n");
        System.out.print(output);
    }

    private static double calculateFinalScore(String[] names, int[] weights, int[] totalWeights, int[] totalScores) {
        double result = 0;
        for (int i = 0; i < names.length; i++) {
            result += calculatePercentage(totalWeights[i], totalScores[i]) * weights[i] / 100.0;
        }
        return result;
    }

    private static int calculatePercentage(int totalWeight, int totalScore) {
        return totalWeight == 0 ? 0 : (int) Math.floor((double) totalScore / totalWeight * 100 + 1e-9);
    }

    static int indexOf(String[] arr, String s) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(s)) {
                return i;
            }
        }
        return -1;
    }

    static String grade(double nilai) {
        if (nilai >= 79.5) return "A";
        if (nilai >= 72) return "AB";
        if (nilai >= 64.5) return "B";
        if (nilai >= 57) return "BC";
        if (nilai >= 49.5) return "C";
        if (nilai >= 34) return "D";
        return "E";
    }
}
