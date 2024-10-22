package upce.bdats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        if (kraje.isEmpty()) {
            System.out.println("No data found.");
        }
    }

    // Display average population for a specific region (Kraj)
    public void displayAveragePopulationForKraj(int index) {
        if (index >= 0 && index < kraje.size()) {
            System.out.println("Average population for Kraj " + (index + 1) + ": " + kraje.get(index).getAveragePopulation());
        } else if (kraje.isEmpty()) {
            System.out.println("No data in system.");
        } else {
            System.out.println("Invalid Kraj index.");
        }
    }

}
