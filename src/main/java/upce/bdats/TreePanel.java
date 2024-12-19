
package upce.bdats;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TreePanel extends JPanel {
    private final BinarySearchTree<Integer, Kraj> tree;
    private static final int NODE_RADIUS = 20;
    private static final int HORIZONTAL_SPACING = 50;
    private static final int VERTICAL_SPACING = 70;

    private final Map<Integer, Color> regionColors; // Map of region IDs to colors

    public TreePanel(BinarySearchTree<Integer, Kraj> tree) {
        this.tree = tree;
        this.regionColors = initializeRegionColors();
        setBackground(Color.WHITE);
    }

    private Map<Integer, Color> initializeRegionColors() {
        Map<Integer, Color> colors = new HashMap<>();
        colors.put(1, new Color(211, 211, 211)); // Example region colors
        colors.put(2, new Color(144, 238, 144));
        colors.put(3, new Color(255, 239, 213));
        // Add more region colors as needed
        return colors;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (tree.getRoot() != null) {
            drawTree(g, tree.getRoot(), getWidth() / 2, 50, getWidth() / 4);
        }
        drawLegend(g);
    }

    private void drawTree(Graphics g, BinarySearchTree<Integer, Kraj>.Node node, int x, int y, int offsetX) {
        if (node == null) return;

        // Draw left child connection
        if (node.left != null) {
            g.drawLine(x, y, x - offsetX, y + VERTICAL_SPACING);
            drawTree(g, node.left, x - offsetX, y + VERTICAL_SPACING, offsetX / 2);
        }

        // Draw right child connection
        if (node.right != null) {
            g.drawLine(x, y, x + offsetX, y + VERTICAL_SPACING);
            drawTree(g, node.right, x + offsetX, y + VERTICAL_SPACING, offsetX / 2);
        }

        // Draw node
        Color color = regionColors.getOrDefault(node.key, Color.ORANGE);
        g.setColor(color);
        g.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        g.setColor(Color.BLACK);
        g.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        // Draw text (ID and name)
        String text = node.key + ": " + ((Kraj) node.value).getNazev();
        g.drawString(text, x - g.getFontMetrics().stringWidth(text) / 2, y + 5);
    }

    private void drawLegend(Graphics g) {
        int legendX = 10;
        int legendY = 10;
        int legendWidth = 20;
        int legendHeight = 20;
        int yOffset = 0;

        g.setColor(Color.BLACK);
        g.drawString("Legenda:", legendX, legendY + yOffset);
        yOffset += 20;

        for (Map.Entry<Integer, Color> entry : regionColors.entrySet()) {
            g.setColor(entry.getValue());
            g.fillRect(legendX, legendY + yOffset - 10, legendWidth, legendHeight - 10);
            g.setColor(Color.BLACK);
            g.drawRect(legendX, legendY + yOffset - 10, legendWidth, legendHeight - 10);

            String regionName = "Region " + entry.getKey(); // Placeholder region names
            g.drawString(regionName, legendX + legendWidth + 10, legendY + yOffset);
            yOffset += 20;
        }
    }
}
