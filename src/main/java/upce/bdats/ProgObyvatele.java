package upce.bdats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProgObyvatele {
    private List<Kraj> kraje;  // Use an ArrayList instead of a fixed array

    // Constructor initializes the list of regions (Kraj)
    public ProgObyvatele() {
        kraje = new ArrayList<>();  // Initialize the dynamic list
    }

    // Import data from CSV file
    public int importData(String filename) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");  // Change split delimiter to semicolon
                // Assuming the format: regionIndex, KrajName, PSC, Name, Men, Women, TotalPopulation
                int regionIndex = Integer.parseInt(parts[0]) - 1; // Convert 1-based index to 0-based
                Obec obec = new Obec(parts[2], parts[3], Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));

                // Ensure the list is large enough to accommodate the region index
                while (kraje.size() <= regionIndex) {
                    kraje.add(new Kraj());
                }

                kraje.get(regionIndex).addObec(obec);  // Add the obec to the correct region (Kraj)
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number: " + e.getMessage());
            e.printStackTrace();
        }
        return count;
    }



    // Display all municipalities (Obce) for all regions (Kraje)
    public void displayAllObce() {
        for (int i = 0; i < kraje.size(); i++) {
            System.out.println("Kraj " + (i + 1) + ":");
            kraje.get(i).displayObce(); // Display obce in the specific region
            System.out.println();
        }
    }

    // Display average population for a specific region (Kraj)
    public void displayAveragePopulationForKraj(int index) {
        if (index >= 0 && index < kraje.size()) {
            System.out.println("Average population for Kraj " + (index + 1) + ": " + kraje.get(index).getAveragePopulation());
        } else {
            System.out.println("Invalid Kraj index.");
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        ProgObyvatele program = new ProgObyvatele();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Population Management ---");
            System.out.println("1. Import data from file");
            System.out.println("2. Display all municipalities");
            System.out.println("3. Display average population for a specific region (kraj)");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    //System.out.print("Enter the file path: ");
                    String filePath = ("C:\\Users\\jirco\\Documents\\GitHub\\kral_BDATS-A_2024\\zadani\\kraje.csv");
                    int imported = program.importData(filePath);
                    System.out.println(imported + " records imported.");
                    break;

                case 2:
                    program.displayAllObce();
                    break;

                case 3:
                    System.out.print("Enter the region (kraj) index (1-14): ");
                    int index = scanner.nextInt() - 1; // Convert 1-based index to 0-based
                    program.displayAveragePopulationForKraj(index);
                    break;

                case 4:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        scanner.close();
    }
}
