import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        //Scanner scanner = new Scanner (System.in);

        String nim = scanner.nextLine().trim();
        scanner.close();

        if (nim.length() != 8) {
            System.out.println("NIM harus 8 karakter");
            return;
        }

        String prefix = nim.substring(0, 3);
        String prodi;
        switch (prefix) {
            case "11S":
                prodi = "Sarjana Informatika";
                break;
            case "12S":
                prodi = "Sarjana Sistem Informasi";
                break;
            case "13S":
                prodi = "Sarjana Teknik Elektro";
                break;
            case "21S":
                prodi = "Sarjana Manajemen Rekayasa";
                break;
            case "22S":
                prodi = "Sarjana Teknik Metalurgi";
                break;
            case "31S":
                prodi = "Sarjana Teknik Bioproses";
                break;
            case "32S":
                prodi = "Sarjana Bioteknologi";
                break;
            case "114":
                prodi = "Diploma 4 Teknologi Rekayasa Perangkat Lunak";
                break;
            case "113":
                prodi = "Diploma 3 Teknologi Informasi";
                break;
            case "133":
                prodi = "Diploma 3 Teknologi Komputer";
                break;
            default:
                System.out.println("Kode tidak tersedia");
                return;
        }

        if (!nim.substring(3).matches("\\d{5}")) {
            System.out.println("Data NIM tidak valid");
            return;
        }

        int angkatan = Integer.parseInt("20" + nim.substring(3, 5));
        int urutan = Integer.parseInt(nim.substring(5, 8));

        System.out.println("Informasi NIM " + nim + ": ");
        System.out.println(">> Program Studi: " + prodi);
        System.out.println(">> Angkatan: " + angkatan);
        System.out.println(">> Urutan: " + urutan);
    }
}
