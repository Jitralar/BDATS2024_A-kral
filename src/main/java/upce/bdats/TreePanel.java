package upce.bdats;

import javax.swing.*;
import java.awt.*;

public class TreePanel extends JPanel {
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

        // Draw the left subtree
        if (node.left != null) {
            g.drawLine(x, y, x - offsetX, y + VERTICAL_SPACING);
            drawTree(g, node.left, x - offsetX, y + VERTICAL_SPACING, offsetX / 2);
        }

        // Draw the right subtree
        if (node.right != null) {
            g.drawLine(x, y, x + offsetX, y + VERTICAL_SPACING);
            drawTree(g, node.right, x + offsetX, y + VERTICAL_SPACING, offsetX / 2);
        }

        // Draw the current node (Kraj)
        g.setColor(Color.ORANGE);
        g.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        g.setColor(Color.BLACK);
        g.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        String text = node.key + ": " + node.value;
        g.drawString(text, x - g.getFontMetrics().stringWidth(text) / 2, y + 5);

        // Draw the Obce (municipalities) as leaf nodes
        int leafY = y + VERTICAL_SPACING * 2; // Position below the Kraj node
        int leafXOffset = NODE_RADIUS * 3;   // Horizontal spacing between leaves

        for (int i = 0; i < node.obce.size(); i++) {
            Obec obec = node.obce.get(i);
            int leafX = x - (node.obce.size() - 1) * leafXOffset / 2 + i * leafXOffset;

            // Draw a line connecting the Kraj to the Obec
            g.drawLine(x, y + NODE_RADIUS, leafX, leafY - NODE_RADIUS);

            // Draw the Obec node
            g.setColor(Color.GREEN);
            g.fillOval(leafX - NODE_RADIUS, leafY - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
            g.setColor(Color.BLACK);
            g.drawOval(leafX - NODE_RADIUS, leafY - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

            // Draw the Obec details (name and total population)
            String leafText = obec.getName() + " (" + obec.getTotalPopulation() + ")";
            g.drawString(leafText, leafX - g.getFontMetrics().stringWidth(leafText) / 2, leafY + 5);
        }
    }
}
