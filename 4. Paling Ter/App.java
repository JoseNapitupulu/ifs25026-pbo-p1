import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    private static final String END_MARKER = "---";

    public static void main(String[] args) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File input.txt tidak dapat dibaca");
            return;
        }
        Map<Integer, Integer> frequencies = readFrequencies(scanner);
        scanner.close();

        if (frequencies.isEmpty()) {
            return;
        }

        printStatistics(frequencies);
    }

    private static Map<Integer, Integer> readFrequencies(Scanner scanner) {
        Map<Integer, Integer> frequencies = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals(END_MARKER)) {
                break;
            }
            addValue(frequencies, line);
        }
        return frequencies;
    }

    private static void addValue(Map<Integer, Integer> frequencies, String line) {
        if (line.isEmpty()) {
            return;
        }
        try {
            int value = Integer.parseInt(line);
            frequencies.put(value, frequencies.getOrDefault(value, 0) + 1);
        } catch (NumberFormatException ignored) {
            // Invalid values are ignored according to the input contract.
        }
    }

    private static void printStatistics(Map<Integer, Integer> frequencies) {
        int highest = findHighest(frequencies);
        int lowest = findLowest(frequencies);
        int mostFrequent = findByFrequency(frequencies, true);
        int leastFrequent = findByFrequency(frequencies, false);
        int highestProduct = findByProduct(frequencies, true);
        int lowestProduct = findByProduct(frequencies, false);

        System.out.println("Tertinggi: " + highest);
        System.out.println("Terendah: " + lowest);
        System.out.println("Terbanyak: " + mostFrequent + " (" + frequencies.get(mostFrequent) + "x)");
        System.out.println("Tersedikit: " + leastFrequent + " (" + frequencies.get(leastFrequent) + "x)");
        System.out.println("Jumlah Tertinggi: " + formatProduct(highestProduct, frequencies));
        System.out.println("Jumlah Terendah: " + formatProduct(lowestProduct, frequencies));
    }

    private static int findHighest(Map<Integer, Integer> frequencies) {
        return frequencies.keySet().stream().max(Integer::compareTo).get();
    }

    private static int findLowest(Map<Integer, Integer> frequencies) {
        return frequencies.keySet().stream().min(Integer::compareTo).get();
    }

    private static int findByFrequency(Map<Integer, Integer> frequencies, boolean highest) {
        int selectedValue = 0;
        int selectedFrequency = highest ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry : frequencies.entrySet()) {
            int value = entry.getKey();
            int frequency = entry.getValue();
            boolean betterFrequency = highest ? frequency > selectedFrequency : frequency < selectedFrequency;
            boolean sameFrequency = frequency == selectedFrequency;
            // Seri frekuensi memilih nilai terbesar untuk tertinggi dan terkecil untuk tersedikit.
            if (betterFrequency || (sameFrequency && (highest ? value > selectedValue : value < selectedValue))) {
                selectedValue = value;
                selectedFrequency = frequency;
            }
        }
        return selectedValue;
    }

    private static int findByProduct(Map<Integer, Integer> frequencies, boolean highest) {
        int selectedValue = 0;
        long selectedProduct = highest ? Long.MIN_VALUE : Long.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry : frequencies.entrySet()) {
            int value = entry.getKey();
            long product = (long) value * entry.getValue();
            boolean betterProduct = highest ? product > selectedProduct : product < selectedProduct;
            boolean sameProduct = product == selectedProduct;
            // Seri hasil kali memakai aturan nilai yang sama agar hasil tidak bergantung pada HashMap.
            if (betterProduct || (sameProduct && (highest ? value > selectedValue : value < selectedValue))) {
                selectedValue = value;
                selectedProduct = product;
            }
        }
        return selectedValue;
    }

    private static String formatProduct(int value, Map<Integer, Integer> frequencies) {
        int frequency = frequencies.get(value);
        return value + " * " + frequency + " = " + value * frequency;
    }
}
