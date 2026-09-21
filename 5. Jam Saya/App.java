import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    private static final int MINUTES_PER_HOUR = 60;
    private static final int MINUTES_PER_DAY = 24 * MINUTES_PER_HOUR;
    private static final String END_MARKER = "---";

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = openInput();

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

    private static Scanner openInput() throws FileNotFoundException {
        File file = new File("input.txt");
        return file.exists() ? new Scanner(file) : new Scanner(System.in);
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
        int currentMinutes = startMinutes;
        int totalShift = 0;
        int dayChanges = 0;
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
            int rawMinutes = currentMinutes + shift;
            dayChanges += Math.abs(Math.floorDiv(rawMinutes, MINUTES_PER_DAY));
            currentMinutes = Math.floorMod(rawMinutes, MINUTES_PER_DAY);
            totalShift += shift;
        }
        return new int[] {currentMinutes, totalShift, dayChanges};
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
