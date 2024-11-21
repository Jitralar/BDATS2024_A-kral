package upce.bdats;

import javax.swing.*;

public class ProgAgendaKraj {
    public static void main(String[] args) {
        BinarySearchTree<String, String> tree = new BinarySearchTree<>();

        // Insert nodes
        tree.insert("C", "Ceske Budejovice");
        tree.insert("B", "Brno");
        tree.insert("A", "As");
        tree.insert("D", "Decin");
        tree.insert("E", "Eger");

        // Swing UI
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Binary Search Tree Operations");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JTextField inputField = new JTextField();
            JButton findButton = new JButton("Find");
            JButton insertButton = new JButton("Insert");
            JButton removeButton = new JButton("Remove");
            JTextArea outputArea = new JTextArea();

            findButton.addActionListener(e -> {
                String key = inputField.getText();
                String result = tree.find(key);
                outputArea.setText(result != null ? "Found: " + result : "Key not found");
            });

            insertButton.addActionListener(e -> {
                String[] parts = inputField.getText().split("=");
                if (parts.length == 2) {
                    tree.insert(parts[0], parts[1]);
                    outputArea.setText("Inserted: " + parts[0] + " -> " + parts[1]);
                } else {
                    outputArea.setText("Invalid format. Use key=value.");
                }
            });

            removeButton.addActionListener(e -> {
                String key = inputField.getText();
                tree.remove(key);
                outputArea.setText("Removed key: " + key);
            });

            JPanel panel = new JPanel();
            panel.add(inputField);
            panel.add(findButton);
            panel.add(insertButton);
            panel.add(removeButton);

            frame.add(panel, "North");
            frame.add(new JScrollPane(outputArea), "Center");
            frame.setVisible(true);
        });
    }
}
