import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));

        Map<Integer, Integer> freq = new HashMap<>();
        while (scanner.hasNextLine()) {
            String baris = scanner.nextLine().trim();
            if (baris.equals("---")) {
                break;
            }
            if (baris.isEmpty()) {
                continue;
            }
            int nilai;
            try {
                nilai = Integer.parseInt(baris);
            } catch (NumberFormatException e) {
                continue;
            }
            Integer f = freq.get(nilai);
            freq.put(nilai, f == null ? 1 : f + 1);
        }
        scanner.close();

        if (freq.isEmpty()) {
            return;
        }

        int tertinggi = Integer.MIN_VALUE;
        int terendah = Integer.MAX_VALUE;
        int terbanyak = 0, terbanyakFreq = Integer.MIN_VALUE;
        int tersedikit = 0, tersedikitFreq = Integer.MAX_VALUE;
        int jumlahTertinggi = 0, jumlahTertinggiProd = Integer.MIN_VALUE;
        int jumlahTerendah = 0, jumlahTerendahProd = Integer.MAX_VALUE;

        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            int nilai = e.getKey();
            int f = e.getValue();
            int hasil = nilai * f;

            if (nilai > tertinggi) tertinggi = nilai;
            if (nilai < terendah) terendah = nilai;

            if (f > terbanyakFreq || (f == terbanyakFreq && nilai > terbanyak)) {
                terbanyak = nilai;
                terbanyakFreq = f;
            }
            if (f < tersedikitFreq || (f == tersedikitFreq && nilai < tersedikit)) {
                tersedikit = nilai;
                tersedikitFreq = f;
            }

            if (hasil > jumlahTertinggiProd || (hasil == jumlahTertinggiProd && nilai > jumlahTertinggi)) {
                jumlahTertinggi = nilai;
                jumlahTertinggiProd = hasil;
            }
            if (hasil < jumlahTerendahProd || (hasil == jumlahTerendahProd && nilai < jumlahTerendah)) {
                jumlahTerendah = nilai;
                jumlahTerendahProd = hasil;
            }
        }

        System.out.println("Tertinggi: " + tertinggi);
        System.out.println("Terendah: " + terendah);
        System.out.println("Terbanyak: " + terbanyak + " (" + terbanyakFreq + "x)");
        System.out.println("Tersedikit: " + tersedikit + " (" + tersedikitFreq + "x)");
        System.out.println("Jumlah Tertinggi: " + jumlahTertinggi + " * " + freq.get(jumlahTertinggi) + " = " + jumlahTertinggiProd);
        System.out.println("Jumlah Terendah: " + jumlahTerendah + " * " + freq.get(jumlahTerendah) + " = " + jumlahTerendahProd);
    }
}
