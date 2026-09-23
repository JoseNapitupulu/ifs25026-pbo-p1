import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    private static final int MINUTES_PER_HOUR = 60;
    private static final int MINUTES_PER_DAY = 24 * MINUTES_PER_HOUR;
    private static final String END_MARKER = "---";

    public static void main(String[] args) {
        Scanner scanner = openInput();
        if (scanner == null) {
            return;
        }

        if (!scanner.hasNextLine()) {
            scanner.close();
            return;
        }

        int[] startTime = parseTime(scanner.nextLine().trim());
        if (startTime == null) {
            System.out.println("Jam tidak valid");
            scanner.close();
            return;
        }

        int startMinutes = toMinutes(startTime[0], startTime[1]);
        int[] result = processCommands(scanner, startMinutes);
        scanner.close();

        printResults(startTime, result);
    }

    private static Scanner openInput() {
        File file = new File("input.txt");
        if (!file.exists()) {
            return new Scanner(System.in);
        }
        try {
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File input.txt tidak dapat dibaca");
            return null;
        }
    }

    private static int[] parseTime(String value) {
        String[] parts = value.split(":", -1);
        if (parts.length != 2) {
            return null;
        }
        try {
            int hour = Integer.parseInt(parts[0].trim());
            int minute = Integer.parseInt(parts[1].trim());
            if (hour < 0 || hour > 23 || minute < 0 || minute >= MINUTES_PER_HOUR) {
                return null;
            }
            return new int[] {hour, minute};
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static int[] processCommands(Scanner scanner, int startMinutes) {
        int totalShift = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals(END_MARKER)) {
                break;
            }
            Integer shift = parseCommand(line);
            if (shift == null) {
                System.out.println("Perintah tidak valid");
                continue;
            }
            totalShift += shift;
        }
        int finalMinutes = startMinutes + totalShift;
        int dayChanges = calculateDayChanges(startMinutes, totalShift);
        return new int[] {Math.floorMod(finalMinutes, MINUTES_PER_DAY), totalShift, dayChanges};
    }

    private static int calculateDayChanges(int startMinutes, int totalShift) {
        // Hari dihitung dari perubahan indeks hari, bukan dari setiap perintah.
        int startDay = Math.floorDiv(startMinutes, MINUTES_PER_DAY);
        int finalDay = Math.floorDiv(startMinutes + totalShift, MINUTES_PER_DAY);
        return Math.abs(finalDay - startDay);
    }

    private static Integer parseCommand(String value) {
        if (value.length() < 2 || (value.charAt(0) != '+' && value.charAt(0) != '-')) {
            return null;
        }
        try {
            int minutes = Integer.parseInt(value.substring(1));
            return value.charAt(0) == '-' ? -minutes : minutes;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static int toMinutes(int hour, int minute) {
        return hour * MINUTES_PER_HOUR + minute;
    }

    private static void printResults(int[] startTime, int[] result) {
        String sign = result[1] > 0 ? "+" : "";
        System.out.printf("Jam Awal: %02d:%02d%n", startTime[0], startTime[1]);
        System.out.printf("Jam Akhir: %02d:%02d%n", result[0] / MINUTES_PER_HOUR, result[0] % MINUTES_PER_HOUR);
        System.out.println("Total Menit: " + sign + result[1]);
        System.out.println("Pergantian Hari: " + result[2]);
    }
}
