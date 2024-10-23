package upce.bdats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class ProgObyvatele extends JFrame {

    private JTextArea displayArea;
    private JComboBox<String> krajComboBox;
    private JButton loadButton, displayButton, avgButton, addButton, removeButton;

    public ProgObyvatele() {
        // Set up the main frame
        setTitle("Správa Obcí");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Create and add buttons to the panel
        loadButton = new JButton("Načíst Data");
        displayButton = new JButton("Zobrazit Obce");
        avgButton = new JButton("Zobrazit Průměry");
        addButton = new JButton("Přidat Obec");
        removeButton = new JButton("Odebrat Obec");

        buttonPanel.add(loadButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(avgButton);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        // Add action listeners for buttons
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //FIXME, make sure something is added
                // Call the method to load data from kraje.csv
                importData("kraje.csv"); //new method, without chooser
                //importDataPick(); //chooser
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //TODO: not working, fix this
                // Display all municipalities
                displayMunicipalities();
            }
        });

        avgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display average population of municipalities
                displayAveragePopulation();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add a municipality manually
                addMunicipality();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove a municipality
                removeMunicipality(); //FIXME implement this method
            }
        });

        // Create a text area to display information
        displayArea = new JTextArea(10, 50);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // ComboBox to select region
        krajComboBox = new JComboBox<>(new String[]{"Vyberte Kraj", "Praha", "Středočeský", "Jihočeský", "Plzeňský", "Karlovarský", "Ústecký", "Liberecký", "Královéhradecký", "Pardubický", "Vysočina", "Jihomoravský", "Olomoucký", "Zlínský", "Moravskoslezský"});
        buttonPanel.add(krajComboBox);

        // Add the panels to the main frame
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Stub methods for functionality to be implemented
    private void importDataPick() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            displayArea.append("Načítám data z: " + selectedFile.getAbsolutePath() + "\n");

            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] fields = line.split(";");
                    if (fields.length == 5) {
                        String psc = fields[0];
                        String obecName = fields[1];
                        int pocetMuzu = Integer.parseInt(fields[2]);
                        int pocetZen = Integer.parseInt(fields[3]);
                        String krajName = fields[4];

                        Obec obec = new Obec(psc, obecName, pocetMuzu, pocetZen);

                        Kraj kraj = findKrajByName(krajName);
                        if (kraj == null) {
                            kraj = new Kraj(krajName);
                            krajeList.vlozPosledni(kraj);
                        }

                        kraj.getObceList().vlozPosledni(obec);
                    }
                }
                displayArea.append("Data byla úspěšně načtena.\n");
            } catch (IOException e) {
                displayArea.append("Chyba při čtení souboru: " + e.getMessage() + "\n");
            }
        }
    }

    private void displayMunicipalities() { //TODO: implement this method
        displayArea.append("Zobrazování všech obcí...\n");
        // Implement logic to display all municipalities

        for (Kraj kraj : krajeList) {
            displayArea.append("Kraj: " + kraj.getNazev() + "\n");
            for (Obec obec : kraj.getObceList()) {
                displayArea.append(obec.toString() + "\n");
            }
        }


    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProgObyvatele frame = new ProgObyvatele();
            frame.setVisible(true);
        });
    }


    private AbstrDoubleList<Kraj> krajeList = new AbstrDoubleList<>();


    private void importData(String fileName) {
        // Assuming the file is located in a folder named 'data' inside your project directory
        int recordCount = 0;  // Track how many records are inserted
        String folderPath = "zadani"; // Change this to your specific folder
        String filePath = Paths.get(folderPath, fileName).toString();

        displayArea.append("Načítám data z: " + filePath + "\n");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields.length == 5) {
                    // Example fields: PSC;Obec;PocetMuzu;PocetZen;Kraj
                    String psc = fields[0];
                    String obecName = fields[1];
                    int pocetMuzu = Integer.parseInt(fields[2]);
                    int pocetZen = Integer.parseInt(fields[3]);
                    String krajName = fields[4];

                    // Create a new municipality (Obec)
                    Obec obec = new Obec(psc, obecName, pocetMuzu, pocetZen);

                    // Find the right Kraj in the list, or create it if it doesn't exist
                    Kraj kraj = findKrajByName(krajName);
                    if (kraj == null) {
                        kraj = new Kraj(krajName);
                        krajeList.vlozPosledni(kraj);
                    }

                    // Add the municipality to the appropriate region (Kraj)
                    kraj.getObceList().vlozPosledni(obec);
                    recordCount++;  // Increment the count for each valid record
                }
            }
            displayArea.append("Data byla úspěšně načtena. Počet záznamů: " + recordCount + "\n");
            displayMunicipalities();
        } catch (IOException e) {
            displayArea.append("Chyba při čtení souboru: " + e.getMessage() + "\n");
        }
    }




    // Find a Kraj by its name
    private Kraj findKrajByName(String name) {
        for (Kraj kraj : krajeList) {
            if (kraj.getNazev().equalsIgnoreCase(name)) {
                return kraj;
            }
        }
        return null;
    }



    private void displayAveragePopulation() {
        String selectedKraj = (String) krajComboBox.getSelectedItem();
        displayArea.setText("");  // Clear the display area

        if (selectedKraj.equals("Vyberte Kraj")) {
            // Calculate and display averages for all regions
            for (Kraj kraj : krajeList) {
                float average = calculateAverage(kraj);
                displayArea.append("Kraj: " + kraj.getNazev() + " - Průměrný počet obyvatel: " + average + "\n");
            }
        } else {
            // Calculate and display average for the selected region
            Kraj kraj = findKrajByName(selectedKraj);
            if (kraj != null) {
                float average = calculateAverage(kraj);
                displayArea.append("Kraj: " + kraj.getNazev() + " - Průměrný počet obyvatel: " + average + "\n");
            } else {
                displayArea.append("Kraj nebyl nalezen.\n");
            }
        }
    }

    private float calculateAverage(Kraj kraj) {
        int totalPopulation = 0;
        int numberOfMunicipalities = 0;

        for (Obec obec : kraj.getObceList()) {
            totalPopulation += obec.getPocetMuzu() + obec.getPocetZen(); //FIXME: missing getters
            numberOfMunicipalities++;
        }

        return numberOfMunicipalities > 0 ? (float) totalPopulation / numberOfMunicipalities : 0;
    }

    private void addMunicipality() {
        // Check if a Kraj is selected
        String selectedKraj = (String) krajComboBox.getSelectedItem();
        if (selectedKraj.equals("Vyberte Kraj")) {
            displayArea.append("Musíte vybrat kraj, do kterého chcete přidat obec.\n");
            return;
        }

        // Show input dialog to get municipality details
        String psc = JOptionPane.showInputDialog(this, "Zadejte PSČ obce:");
        String name = JOptionPane.showInputDialog(this, "Zadejte název obce:");
        String pocetMuzuStr = JOptionPane.showInputDialog(this, "Zadejte počet mužů:");
        String pocetZenStr = JOptionPane.showInputDialog(this, "Zadejte počet žen:");

        // Validate input
        if (psc == null || name == null || pocetMuzuStr == null || pocetZenStr == null ||
                psc.isEmpty() || name.isEmpty() || pocetMuzuStr.isEmpty() || pocetZenStr.isEmpty()) {
            displayArea.append("Neplatný vstup! Všechna pole musí být vyplněna.\n");
            return;
        }

        try {
            int pocetMuzu = Integer.parseInt(pocetMuzuStr);
            int pocetZen = Integer.parseInt(pocetZenStr);

            // Create a new Obec object
            Obec newObec = new Obec(psc, name, pocetMuzu, pocetZen);

            // Find the selected Kraj and add the new municipality
            Kraj kraj = findKrajByName(selectedKraj);
            if (kraj != null) {
                kraj.getObceList().vlozPosledni(newObec);
                displayArea.append("Obec " + name + " byla přidána do kraje " + selectedKraj + ".\n");
            } else {
                displayArea.append("Kraj nebyl nalezen.\n");
            }
        } catch (NumberFormatException e) {
            displayArea.append("Neplatný formát čísel! Zadejte správné číselné hodnoty pro počty mužů a žen.\n");
        }
    }

    private void removeMunicipality() {
        // Check if a Kraj is selected
        String selectedKraj = (String) krajComboBox.getSelectedItem();
        if (selectedKraj.equals("Vyberte Kraj")) {
            displayArea.append("Musíte vybrat kraj, ze kterého chcete odebrat obec.\n");
            return;
        }

        // Show input dialog to get the municipality name
        String obecName = JOptionPane.showInputDialog(this, "Zadejte název obce, kterou chcete odebrat:");

        // Validate input
        if (obecName == null || obecName.isEmpty()) {
            displayArea.append("Neplatný vstup! Název obce musí být zadán.\n");
            return;
        }

        // Find the selected Kraj and remove the municipality
        Kraj kraj = findKrajByName(selectedKraj);
        if (kraj != null) {
            boolean removed = false;
            // Loop through the list of municipalities (Obce) in the selected Kraj
            for (Obec obec : kraj.getObceList()) {
                if (obec.getName().equalsIgnoreCase(obecName)) {
                    kraj.getObceList().odeberAktualni();  // Remove current item in the iterator
                    removed = true;
                    displayArea.append("Obec " + obecName + " byla odebrána z kraje " + selectedKraj + ".\n");
                    break;
                }
            }
            if (!removed) {
                displayArea.append("Obec " + obecName + " nebyla nalezena v kraji " + selectedKraj + ".\n");
            }
        } else {
            displayArea.append("Kraj nebyl nalezen.\n");
        }
    }





}

