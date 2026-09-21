import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner;
        File file = new File("input.txt");
        if (file.exists()) {
            scanner = new Scanner(file);
        } else {
            scanner = new Scanner(System.in);
        }

        if (!scanner.hasNextLine()) {
            scanner.close();
            return;
        }

        String jamAwal = scanner.nextLine().trim();
        String[] parts = jamAwal.split(":", -1);
        int jam, menit;
        try {
            if (parts.length != 2) {
                throw new NumberFormatException();
            }
            jam = Integer.parseInt(parts[0].trim());
            menit = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            System.out.println("Jam tidak valid");
            scanner.close();
            return;
        }

        if (jam < 0 || jam > 23 || menit < 0 || menit > 59) {
            System.out.println("Jam tidak valid");
            scanner.close();
            return;
        }

        int current = jam * 60 + menit;
        int totalGeser = 0;
        int pergantianHari = 0;

        while (scanner.hasNextLine()) {
            String baris = scanner.nextLine().trim();
            if (baris.equals("---")) {
                break;
            }

            char tanda = baris.isEmpty() ? ' ' : baris.charAt(0);
            int n;
            try {
                if (tanda != '+' && tanda != '-') {
                    throw new NumberFormatException();
                }
                String angka = baris.substring(1);
                if (!angka.matches("\\d+")) {
                    throw new NumberFormatException();
                }
                n = Integer.parseInt(angka);
                if (tanda == '-') {
                    n = -n;
                }
            } catch (NumberFormatException e) {
                System.out.println("Perintah tidak valid");
                continue;
            }

            int raw = current + n;
            pergantianHari += Math.abs(Math.floorDiv(raw, 1440));
            current = Math.floorMod(raw, 1440);
            totalGeser += n;
        }
        scanner.close();

        String tandaTotal = totalGeser > 0 ? "+" : "";
        System.out.printf("Jam Awal: %02d:%02d%n", jam, menit);
        System.out.printf("Jam Akhir: %02d:%02d%n", current / 60, current % 60);
        System.out.println("Total Menit: " + tandaTotal + totalGeser);
        System.out.println("Pergantian Hari: " + pergantianHari);
    }
}
