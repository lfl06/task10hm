import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

class BankFinder {

    private static final String FILE_URL =
            "https://ewib.nbp.pl/plewibnra?dokNazwa=plewibnra.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter first 3 digits of bank number: ");
        String digits = sc.nextLine().trim();

        if (!digits.matches("\\d{3}")) {
            System.out.println("Error: give exactly 3 digits.");
            return;
        }

        String bankName = findBank(digits);

        if (bankName != null) {
            System.out.println("Your account is in bank: " + bankName);
        } else {
            System.out.println("No bank found for number: " + digits);
        }
    }


    private static String findBank(String digits) {
        try {
            URL url = new URL(FILE_URL);

            // try-with-resources: stream closes by itself
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream(), "Cp852"))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\t");
                    if (parts.length < 2) {
                        continue;
                    }

                    String bankNumber = parts[0].trim();
                    String name = parts[1].trim();

                    if (bankNumber.equals(digits)) {
                        return name;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return null;
    }
}