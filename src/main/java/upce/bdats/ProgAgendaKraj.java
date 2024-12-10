package upce.bdats;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProgAgendaKraj {
    public static void main(String[] args) {
        // Inicializace stromu a haldy
        BinarySearchTree<Integer, Kraj> tree = new BinarySearchTree<>();
        ABSTRHEAP heap = new ABSTRHEAP();

        // Frame
        JFrame frame = new JFrame("Binary Search Tree and Priority Queue");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        // Tree panel
        TreePanel treePanel = new TreePanel(tree);
        frame.add(treePanel, BorderLayout.CENTER);

        // Control panel for Binary Search Tree
        JPanel treeControlPanel = new JPanel();
        JTextField inputField = new JTextField(10);
        JButton insertButton = new JButton("Insert");
        JButton removeButton = new JButton("Remove");
        JButton toggleTraversalButton = new JButton("Toggle LIFO/FIFO");

        treeControlPanel.add(new JLabel("Key=Value:"));
        treeControlPanel.add(inputField);
        treeControlPanel.add(insertButton);
        treeControlPanel.add(removeButton);
        treeControlPanel.add(toggleTraversalButton);

        insertButton.addActionListener(e -> {
            String[] parts = inputField.getText().split("=");
            if (parts.length == 2) {
                try {
                    int key = Integer.parseInt(parts[0]);
                    String value = parts[1];
                    tree.insert(key, new Kraj(key, value));
                    treePanel.repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid key format.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid input format. Use Key=Value.");
            }
        });

        removeButton.addActionListener(e -> {
            try {
                int key = Integer.parseInt(inputField.getText());
                tree.remove(key);
                treePanel.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid key format.");
            }
        });

        toggleTraversalButton.addActionListener(e -> {
            tree.toggleTraversalMethod();
            JOptionPane.showMessageDialog(frame, "Traversal toggled to: " + (tree.isUsingLIFO() ? "LIFO" : "FIFO"));
        });

        frame.add(treeControlPanel, BorderLayout.NORTH);

        // Control panel for Priority Queue (Heap)
        JPanel heapControlPanel = new JPanel();
        JButton buildHeapButton = new JButton("Build Heap");
        JButton reorganizeHeapButton = new JButton("Reorganize");
        JButton insertHeapButton = new JButton("Insert");
        JButton removeMaxHeapButton = new JButton("Remove Max");
        JButton printHeapButton = new JButton("Print");
        JRadioButton prioritizeByPopulation = new JRadioButton("Population", true);
        JRadioButton prioritizeByName = new JRadioButton("Name");

        ButtonGroup priorityGroup = new ButtonGroup();
        priorityGroup.add(prioritizeByPopulation);
        priorityGroup.add(prioritizeByName);

        heapControlPanel.add(buildHeapButton);
        heapControlPanel.add(reorganizeHeapButton);
        heapControlPanel.add(insertHeapButton);
        heapControlPanel.add(removeMaxHeapButton);
        heapControlPanel.add(printHeapButton);
        heapControlPanel.add(new JLabel("Priority:"));
        heapControlPanel.add(prioritizeByPopulation);
        heapControlPanel.add(prioritizeByName);

        buildHeapButton.addActionListener(e -> {
            List<Obec> obce = generateRandomObce(); // Vygeneruje nebo načte seznam obcí
            heap.buildHeap(obce);
            JOptionPane.showMessageDialog(frame, "Heap built successfully.");
        });

        reorganizeHeapButton.addActionListener(e -> {
            heap.reorganize();
            JOptionPane.showMessageDialog(frame, "Heap reorganized successfully.");
        });

        insertHeapButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter Obec (Name=Men,Women):");
            if (input != null) {
                String[] parts = input.split("=");
                if (parts.length == 2) {
                    String name = parts[0];
                    String[] population = parts[1].split(",");
                    if (population.length == 2) {
                        try {
                            int men = Integer.parseInt(population[0]);
                            int women = Integer.parseInt(population[1]);
                            heap.insert(new Obec("", name, men, women));
                            JOptionPane.showMessageDialog(frame, "Obec inserted successfully.");
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid population format.");
                        }
                    }
                }
            }
        });

        removeMaxHeapButton.addActionListener(e -> {
            Obec removed = heap.removeMax();
            if (removed != null) {
                JOptionPane.showMessageDialog(frame, "Removed: " + removed);
            } else {
                JOptionPane.showMessageDialog(frame, "Heap is empty.");
            }
        });

        printHeapButton.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Heap contents:\n");
            heap.getHeap().forEach(obec -> {
                if (obec != null) sb.append(obec).append("\n");
            });
            JOptionPane.showMessageDialog(frame, sb.toString());
        });

        prioritizeByPopulation.addActionListener(e -> heap.setPriorityByPopulation(true));
        prioritizeByName.addActionListener(e -> heap.setPriorityByPopulation(false));

        frame.add(heapControlPanel, BorderLayout.SOUTH);

        // Display frame
        frame.setVisible(true);
    }

    private static List<Obec> generateRandomObce() {
        List<Obec> obce = new ArrayList<>();
        obce.add(new Obec("111", "Praha", 100000, 120000));
        obce.add(new Obec("222", "Brno", 50000, 60000));
        obce.add(new Obec("333", "Ostrava", 40000, 45000));
        obce.add(new Obec("444", "Plzeň", 30000, 35000));
        obce.add(new Obec("555", "Liberec", 25000, 30000));
        return obce;
    }
}
