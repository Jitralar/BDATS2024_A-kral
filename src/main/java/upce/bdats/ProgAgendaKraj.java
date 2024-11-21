package upce.bdats;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class ProgAgendaKraj {

    public static void main(String[] args) {
        // Initialize the tree
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>();

        // Import data from the CSV file
        String filename = "kraje.csv";
        String folderPath = "zadani"; // Update this path if needed
        String filePath = Paths.get(folderPath, filename).toString();
        importData(filePath, tree);

        // Create GUI
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Binary Search Tree Visualization");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            JPanel controlPanel = new JPanel();
            JTextField inputField = new JTextField(10);
            JButton insertButton = new JButton("Insert");
            JButton removeButton = new JButton("Remove");
            JButton toggleButton = new JButton("Toggle LIFO/FIFO");

            // Tree panel for drawing the tree
            TreePanel treePanel = new TreePanel(tree);
            frame.add(treePanel, BorderLayout.CENTER);

            // Add buttons and input field to the control panel
            controlPanel.add(new JLabel("Key=Value:"));
            controlPanel.add(inputField);
            controlPanel.add(insertButton);
            controlPanel.add(removeButton);
            controlPanel.add(toggleButton);
            frame.add(controlPanel, BorderLayout.NORTH);

            // Button actions
            insertButton.addActionListener(e -> {
                String[] parts = inputField.getText().split("=");
                if (parts.length == 2) {
                    try {
                        int key = Integer.parseInt(parts[0]);
                        String value = parts[1];
                        tree.insert(key, value);
                        treePanel.repaint(); // Redraw the tree
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid key! Must be a number.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid format! Use key=value.");
                }
            });

            removeButton.addActionListener(e -> {
                try {
                    int key = Integer.parseInt(inputField.getText());
                    tree.remove(key);
                    treePanel.repaint(); // Redraw the tree
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid key! Must be a number.");
                }
            });

            toggleButton.addActionListener(e -> {
                tree.toggleTraversalMethod();
                JOptionPane.showMessageDialog(frame, "Traversal method toggled to: " + (tree.isUsingLIFO() ? "LIFO" : "FIFO"));
            });

            frame.setVisible(true);
        });
    }

    private static void importData(String filePath, BinarySearchTree<Integer, String> tree) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields.length >= 2) {
                    int idKraje = Integer.parseInt(fields[0]);
                    String krajName = fields[1];
                    tree.insert(idKraje, krajName);
                }
            }
            System.out.println("Data successfully imported from " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}

/**
 * Panel to visualize the Binary Search Tree.
 */
class TreePanel extends JPanel {
    private final BinarySearchTree<Integer, String> tree;
    private static final int NODE_RADIUS = 20;
    private static final int HORIZONTAL_SPACING = 50;
    private static final int VERTICAL_SPACING = 50;

    public TreePanel(BinarySearchTree<Integer, String> tree) {
        this.tree = tree;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (tree.getRoot() != null) {
            drawTree(g, tree.getRoot(), getWidth() / 2, 50, getWidth() / 4);
        }
    }

    private void drawTree(Graphics g, BinarySearchTree<Integer, String>.Node node, int x, int y, int offsetX) {
        if (node == null) return;

        if (node.left != null) {
            g.drawLine(x, y, x - offsetX, y + VERTICAL_SPACING);
            drawTree(g, node.left, x - offsetX, y + VERTICAL_SPACING, offsetX / 2);
        }

        if (node.right != null) {
            g.drawLine(x, y, x + offsetX, y + VERTICAL_SPACING);
            drawTree(g, node.right, x + offsetX, y + VERTICAL_SPACING, offsetX / 2);
        }

        g.setColor(Color.ORANGE);
        g.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        g.setColor(Color.BLACK);
        g.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        String text = node.key + ":" + node.value;
        g.drawString(text, x - g.getFontMetrics().stringWidth(text) / 2, y + 5);
    }
}
