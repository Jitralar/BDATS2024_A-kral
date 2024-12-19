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
        ABSTRHEAP heap = new ABSTRHEAP();

        JFrame frame = new JFrame("Agenda Krajů - Strom a Prioritní Fronta");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        JPanel controlPanel = new JPanel();
        JButton loadCSVButton = new JButton("Načíst CSV");
        JButton displayHeapButton = new JButton("Zobraz Frontu");
        JButton reorganizeHeapButton = new JButton("Reorganizovat Frontu");

        controlPanel.add(loadCSVButton);
        controlPanel.add(displayHeapButton);
        controlPanel.add(reorganizeHeapButton);

        loadCSVButton.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(frame, "Zadejte název CSV souboru:");
            if (fileName != null && !fileName.isEmpty()) {
                importData(fileName, tree, heap);
                JOptionPane.showMessageDialog(frame, "Data z CSV byla načtena.");
            }
        });

        displayHeapButton.addActionListener(e -> {
            displayArea.setText("Obsah prioritní fronty:\n");
            for (Obec obec : heap.getHeap()) {
                displayArea.append(obec + "\n");
            }
        });

        reorganizeHeapButton.addActionListener(e -> {
            heap.reorganize();
            JOptionPane.showMessageDialog(frame, "Prioritní fronta byla reorganizována.");
        });

        JPanel priorityPanel = new JPanel();
        JRadioButton prioritizeByPopulation = new JRadioButton("Podle populace", true);
        JRadioButton prioritizeByName = new JRadioButton("Podle názvu");

        ButtonGroup priorityGroup = new ButtonGroup();
        priorityGroup.add(prioritizeByPopulation);
        priorityGroup.add(prioritizeByName);

        priorityPanel.add(new JLabel("Priorita:"));
        priorityPanel.add(prioritizeByPopulation);
        priorityPanel.add(prioritizeByName);

        prioritizeByPopulation.addActionListener(e -> heap.setPriorityByPopulation(true));
        prioritizeByName.addActionListener(e -> heap.setPriorityByPopulation(false));

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(priorityPanel, BorderLayout.SOUTH);
        frame.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void importData(String fileName, BinarySearchTree<Integer, Kraj> tree, ABSTRHEAP heap) {
        int recordCount = 0;
        String folderPath = "zadani";
        String filePath = Paths.get(folderPath, fileName).toString();

        displayArea.append("Načítám data z: " + filePath + "\n");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields.length == 7) {
                    int krajId = Integer.parseInt(fields[0]);
                    String krajName = fields[1];
                    String psc = fields[2];
                    String obecName = fields[3];
                    int men = Integer.parseInt(fields[4]);
                    int women = Integer.parseInt(fields[5]);

                    Obec obec = new Obec(psc, obecName, men, women);
                    heap.insert(obec); // Insert into the heap

                    Kraj kraj = findOrCreateKraj(tree, krajId, krajName);
                    kraj.getObceList().vlozPosledni(obec);
                    recordCount++;
                }
            }
            displayArea.append("Data byla úspěšně načtena. Počet záznamů: " + recordCount + "\n");
        } catch (IOException e) {
            displayArea.append("Chyba při čtení souboru: " + e.getMessage() + "\n");
        }
    }

    private static Kraj findOrCreateKraj(BinarySearchTree<Integer, Kraj> tree, int krajId, String krajName) {
        Kraj kraj = tree.find(krajId);
        if (kraj == null) {
            kraj = new Kraj(krajId, krajName);
            tree.insert(krajId, kraj);
        }
        return kraj;
    }
}
