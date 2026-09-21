import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class App {  
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));

        String[] simbol = {"PA", "T", "K", "P", "UTS", "UAS"};
        String[] nama = {"Partisipatif", "Tugas", "Kuis", "Proyek", "UTS", "UAS"};
        int[] bobot = new int[6];
        int[] totalBobot = new int[6];
        int[] totalPerolehan = new int[6];

        int sumBobot = 0;
        for (int i = 0; i < 6; i++) {
            if (!scanner.hasNextLine()) {
                break;
            }

            try {
                bobot[i] = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                bobot[i] = 0;
            }
            sumBobot += bobot[i];
        }

        if (sumBobot != 100) {
            System.out.println("Total bobot harus 100");
            scanner.close();
            return;
        }

        while (scanner.hasNextLine()) {
            String baris = scanner.nextLine().trim();
            if (baris.equals("---")) {
                break;
            }

            String[] parts = baris.split("\\|", -1);
            if (parts.length != 3) {
                System.out.println("Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai");
                continue;
            }

            String sym = parts[0].trim();
            int b, p;
            try {
                b = Integer.parseInt(parts[1].trim());
                p = Integer.parseInt(parts[2].trim());
            } catch (NumberFormatException e) {
                System.out.println("Data tidak valid. Silahkan menggunakan format: Simbol|Bobot|Perolehan-Nilai");
                continue;
            }

            int idx = indexOf(simbol, sym);
            if (idx < 0) {
                System.out.println("Simbol tidak dikenal");
                continue;
            }

            p = Math.max(0, Math.min(p, b));
            totalBobot[idx] += b;
            totalPerolehan[idx] += p;
        }
        scanner.close();

        double nilaiAkhir = 0;
        
        StringBuilder sb = new StringBuilder();
        sb.append("Perolehan Nilai:\n");
        for (int i = 0; i < 6; i++) {
            int persen = totalBobot[i] == 0 ? 0 : (int) Math.floor((double) totalPerolehan[i] / totalBobot[i] * 100 + 1e-9);
            double kontribusi = persen * bobot[i] / 100.0;
            nilaiAkhir += kontribusi;
            sb.append(String.format(Locale.US, ">> %s: %d/100 (%.2f/%d)%n", nama[i], persen, kontribusi, bobot[i]));
        }

        sb.append(String.format(Locale.US, "%n>> Nilai Akhir: %.2f%n", nilaiAkhir));
        sb.append(">> Grade: ").append(grade(nilaiAkhir)).append("\n");
        System.out.print(sb.toString());
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
