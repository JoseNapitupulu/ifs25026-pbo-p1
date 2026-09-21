import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));

        int n = Integer.parseInt(scanner.nextLine().trim());
        int[][] m = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] tokens = scanner.nextLine().trim().split("\\s+");
            for (int j = 0; j < n; j++) {
                m[i][j] = Integer.parseInt(tokens[j]);
            }
        }
        scanner.close();

        if (n == 1) {
            System.out.println("Nilai L: Tidak Ada");
            System.out.println("Nilai Kebalikan L: Tidak Ada");
            System.out.println("Nilai Tengah: " + m[0][0]);
            System.out.println("Perbedaan: Tidak Ada");
            System.out.println("Dominan: " + m[0][0]);
            return;
        }

        if (n == 2) {
            int total = m[0][0] + m[0][1] + m[1][0] + m[1][1];
            System.out.println("Nilai L: Tidak Ada");
            System.out.println("Nilai Kebalikan L: Tidak Ada");
            System.out.println("Nilai Tengah: " + total);
            System.out.println("Perbedaan: Tidak Ada");
            System.out.println("Dominan: " + total);
            return;
        }

        int nilaiL = 0;
        for (int i = 0; i < n; i++) {
            nilaiL += m[i][0];
        }
        for (int j = 1; j <= n - 2; j++) {
            nilaiL += m[n - 1][j];
        }

        int nilaiKebalikan = 0;
        for (int i = 0; i < n; i++) {
            nilaiKebalikan += m[i][n - 1];
        }
        for (int j = 1; j <= n - 2; j++) {
            nilaiKebalikan += m[0][j];
        }

        int nilaiTengah;
        if (n % 2 == 1) {
            nilaiTengah = m[n / 2][n / 2];
        } else {
            int t = n / 2;
            nilaiTengah = m[t - 1][t - 1] + m[t - 1][t] + m[t][t - 1] + m[t][t];
        }

        int perbedaan = Math.abs(nilaiL - nilaiKebalikan);
        int dominan = perbedaan == 0 ? nilaiTengah : Math.max(nilaiL, nilaiKebalikan);

        System.out.println("Nilai L: " + nilaiL);
        System.out.println("Nilai Kebalikan L: " + nilaiKebalikan);
        System.out.println("Nilai Tengah: " + nilaiTengah);
        System.out.println("Perbedaan: " + perbedaan);
        System.out.println("Dominan: " + dominan);
    }
}
