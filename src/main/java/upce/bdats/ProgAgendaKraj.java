
package upce.bdats;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class ProgAgendaKraj {
    private static JTextArea displayArea = new JTextArea();

    public static void main(String[] args) {
        BinarySearchTree<Integer, Kraj> tree = new BinarySearchTree<>();
        AbstrDoubleList<Kraj> krajeList = new AbstrDoubleList<>();

        JFrame frame = new JFrame("Agenda Krajů - Strom a Prioritní Fronta");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        JPanel treeControlPanel = new JPanel();
        JTextField inputField = new JTextField(10);
        JButton loadCSVButton = new JButton("Načíst CSV");

        treeControlPanel.add(loadCSVButton);
        treeControlPanel.add(new JLabel("Klíč=Hodnota:"));
        treeControlPanel.add(inputField);

        loadCSVButton.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(frame, "Zadejte název CSV souboru:");
            if (fileName != null && !fileName.isEmpty()) {
                importData(fileName, krajeList);
                JOptionPane.showMessageDialog(frame, "Data z CSV byla načtena.");
            }
        });

        frame.add(treeControlPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void importData(String fileName, AbstrDoubleList<Kraj> krajeList) {
        int recordCount = 0;
        String folderPath = "zadani";
        String filePath = Paths.get(folderPath, fileName).toString();

        displayArea.append("Načítám data z: " + filePath + "\\n");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields.length == 7) {
                    String idKraje = fields[0];
                    String krajName = fields[1];
                    String psc = fields[2];
                    String obecName = fields[3];
                    int pocetMuzu = Integer.parseInt(fields[4]);
                    int pocetZen = Integer.parseInt(fields[5]);

                    Obec obec = new Obec(psc, obecName, pocetMuzu, pocetZen);
                    Kraj kraj = findKrajById(krajeList, Integer.parseInt(idKraje));
                    if (kraj == null) {
                        kraj = new Kraj(Integer.parseInt(idKraje), krajName);
                        krajeList.vlozPosledni(kraj);
                    }

                    kraj.getObceList().vlozPosledni(obec);
                    recordCount++;
                } else {
                    System.out.println("Přeskakuji neplatný řádek: " + line);
                }
            }
            displayArea.append("Data byla úspěšně načtena. Počet záznamů: " + recordCount + "\\n");
        } catch (IOException e) {
            displayArea.append("Chyba při čtení souboru: " + e.getMessage() + "\\n");
        }
    }

    private static Kraj findKrajById(AbstrDoubleList<Kraj> krajeList, int id) {
        for (Kraj kraj : krajeList) {
            if (kraj.getId() == id) {
                return kraj;
            }
        }
        return null;
    }
}

