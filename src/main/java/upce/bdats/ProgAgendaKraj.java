package upce.bdats;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

public class ProgAgendaKraj {
    public static void main(String[] args) {
        // Define file path
        String krajeFile = "kraje.csv";
        String folderPath = "zadani"; // Adjust the path if necessary
        String krajeFilePath = Paths.get(folderPath, krajeFile).toString();

        // Build the tree
        BinarySearchTree<Integer, Kraj> tree = KrajTreeBuilder.buildTree(krajeFilePath);

        // Create GUI
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Binary Search Tree Visualization");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            TreePanel treePanel = new TreePanel(tree);
            frame.add(treePanel, BorderLayout.CENTER);

            JPanel controlPanel = new JPanel();
            JTextField inputField = new JTextField(10);
            JButton insertButton = new JButton("Insert");
            JButton removeButton = new JButton("Remove");
            JButton toggleButton = new JButton("Toggle LIFO/FIFO");

            controlPanel.add(new JLabel("Key=Value:"));
            controlPanel.add(inputField);
            controlPanel.add(insertButton);
            controlPanel.add(removeButton);
            controlPanel.add(toggleButton);

            insertButton.addActionListener(e -> {
                String[] parts = inputField.getText().split("=");
                if (parts.length == 2) {
                    try {
                        int key = Integer.parseInt(parts[0]);
                        tree.insert(key, new Kraj(key, parts[1]));
                        treePanel.repaint();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid key format.");
                    }
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

            toggleButton.addActionListener(e -> {
                tree.toggleTraversalMethod();
                JOptionPane.showMessageDialog(frame, "Traversal toggled to: " + (tree.isUsingLIFO() ? "LIFO" : "FIFO"));
            });

            frame.add(controlPanel, BorderLayout.NORTH);
            frame.setVisible(true);
        });
    }
}
