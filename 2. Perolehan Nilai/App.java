import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class App {  
    private static final int COMPONENT_COUNT = 6;
    private static final int REQUIRED_TOTAL_WEIGHT = 100;
    private static final String END_MARKER = "---";
    private static final String INVALID_DATA = "Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai";

    public static void main(String[] args) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File input.txt tidak dapat dibaca");
            return;
        }

        String[] symbols = {"PA", "T", "K", "P", "UTS", "UAS"};
        String[] names = {"Partisipatif", "Tugas", "Kuis", "Proyek", "UTS", "UAS"};
        int[] weights = readWeights(scanner);
        if (weights == null || sum(weights) != REQUIRED_TOTAL_WEIGHT) {
            System.out.println("Total bobot harus 100");
            scanner.close();
            return;
        }

        int[][] totals = readScores(scanner, symbols);
        scanner.close();

        int[] percentages = calculatePercentages(totals[0], totals[1]);
        double[] contributions = calculateContributions(weights, percentages);
        printResults(names, weights, percentages, contributions);
    }

    private static int[] readWeights(Scanner scanner) {
        int[] weights = new int[COMPONENT_COUNT];
        for (int i = 0; i < COMPONENT_COUNT; i++) {
            if (!scanner.hasNextLine()) {
                return null;
            }
            try {
                weights[i] = Integer.parseInt(scanner.nextLine().trim());
                if (weights[i] < 0) {
                    return null;
                }
            } catch (NumberFormatException e) {
                return null;
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
                if (weight < 0) {
                    System.out.println(INVALID_DATA);
                    return;
                }
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

    private static int[] calculatePercentages(int[] totalWeights, int[] totalScores) {
        int[] percentages = new int[COMPONENT_COUNT];
        for (int i = 0; i < COMPONENT_COUNT; i++) {
            percentages[i] = calculatePercentage(totalWeights[i], totalScores[i]);
        }
        return percentages;
    }

    private static double[] calculateContributions(int[] weights, int[] percentages) {
        double[] contributions = new double[COMPONENT_COUNT];
        for (int i = 0; i < COMPONENT_COUNT; i++) {
            contributions[i] = percentages[i] * weights[i] / 100.0;
        }
        return contributions;
    }

    private static void printResults(String[] names, int[] weights, int[] percentages, double[] contributions) {
        double finalScore = sum(contributions);
        StringBuilder output = new StringBuilder("Perolehan Nilai:\n");
        for (int i = 0; i < COMPONENT_COUNT; i++) {
            output.append(String.format(Locale.US, ">> %s: %d/100 (%.2f/%d)%n", names[i], percentages[i], contributions[i], weights[i]));
        }
        output.append(String.format(Locale.US, "%n>> Nilai Akhir: %.2f%n", finalScore));
        output.append(">> Grade: ").append(grade(finalScore)).append("\n");
        System.out.print(output);
    }

    private static int calculatePercentage(int totalWeight, int totalScore) {
        return totalWeight == 0 ? 0 : (int) Math.floor((double) totalScore / totalWeight * 100 + 1e-9);
    }

    private static double sum(double[] values) {
        double result = 0;
        for (double value : values) {
            result += value;
        }
        return result;
    }

    private static int indexOf(String[] arr, String s) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(s)) {
                return i;
            }
        }
        return -1;
    }

    private static String grade(double nilai) {
        if (nilai >= 79.5) return "A";
        if (nilai >= 72) return "AB";
        if (nilai >= 64.5) return "B";
        if (nilai >= 57) return "BC";
        if (nilai >= 49.5) return "C";
        if (nilai >= 34) return "D";
        return "E";
    }
}
