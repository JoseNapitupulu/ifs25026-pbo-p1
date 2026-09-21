import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    private static final int NIM_LENGTH = 8;
    private static final int PREFIX_LENGTH = 3;
    private static final Map<String, String> PROGRAM_STUDIES = createProgramStudies();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        String nim = readNim(scanner);
        scanner.close();

        if (!hasValidLength(nim)) {
            System.out.println("NIM harus 8 karakter");
            return;
        }

        String prodi = PROGRAM_STUDIES.get(nim.substring(0, PREFIX_LENGTH));
        if (prodi == null) {
            System.out.println("Kode tidak tersedia");
            return;
        }

        if (!hasValidNumericPart(nim)) {
            System.out.println("Data NIM tidak valid");
            return;
        }

        printInformation(nim, prodi);
    }

    private static String readNim(Scanner scanner) {
        return scanner.hasNextLine() ? scanner.nextLine().trim() : "";
    }

    private static boolean hasValidLength(String nim) {
        return nim.length() == NIM_LENGTH;
    }

    private static boolean hasValidNumericPart(String nim) {
        return nim.substring(PREFIX_LENGTH).matches("\\d{5}");
    }

    private static void printInformation(String nim, String prodi) {
        int angkatan = Integer.parseInt("20" + nim.substring(3, 5));
        int urutan = Integer.parseInt(nim.substring(5, 8));

        System.out.println("Informasi NIM " + nim + ": ");
        System.out.println(">> Program Studi: " + prodi);
        System.out.println(">> Angkatan: " + angkatan);
        System.out.println(">> Urutan: " + urutan);
    }

    private static Map<String, String> createProgramStudies() {
        Map<String, String> studies = new HashMap<>();
        studies.put("11S", "Sarjana Informatika");
        studies.put("12S", "Sarjana Sistem Informasi");
        studies.put("13S", "Sarjana Teknik Elektro");
        studies.put("21S", "Sarjana Manajemen Rekayasa");
        studies.put("22S", "Sarjana Teknik Metalurgi");
        studies.put("31S", "Sarjana Teknik Bioproses");
        studies.put("32S", "Sarjana Bioteknologi");
        studies.put("114", "Diploma 4 Teknologi Rekayasa Perangkat Lunak");
        studies.put("113", "Diploma 3 Teknologi Informasi");
        studies.put("133", "Diploma 3 Teknologi Komputer");
        return studies;
    }
}
