package upce.bdats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class TreePanel extends JPanel {
    private final BinarySearchTree<Integer, Kraj> tree;
    private static final int NODE_RADIUS_KRAJ = 20; // Radius for Kraj nodes
    private static final int NODE_RADIUS_OBEC = 10; // Radius for Obec nodes
    private static final int MAX_OBEC_DISPLAY = 5; // Max Obec nodes displayed per Kraj
    private int horizontalSpacing = 50;
    private int verticalSpacing = 50;

    private final Map<Rectangle, String> nodeTextMap = new HashMap<>();
    private String hoveredText = null;

    public TreePanel(BinarySearchTree<Integer, Kraj> tree) {
        this.tree = tree;
        setBackground(Color.WHITE);

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point mousePoint = e.getPoint();
                hoveredText = null;
                for (Map.Entry<Rectangle, String> entry : nodeTextMap.entrySet()) {
                    if (entry.getKey().contains(mousePoint)) {
                        hoveredText = entry.getValue();
                        break;
                    }
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        nodeTextMap.clear();

        horizontalSpacing = Math.max(50, getWidth() / (getMaxTreeWidth(tree.getRoot()) + 1));
        verticalSpacing = Math.max(50, getHeight() / (getTreeHeight(tree.getRoot()) + 2));

        if (tree.getRoot() != null) {
            drawTree(g, tree.getRoot(), getWidth() / 2, verticalSpacing, getWidth() / 4);
        }

        if (hoveredText != null) {
            g.setColor(Color.BLACK);
            g.drawString(hoveredText, 10, getHeight() - 20);
        }
    }

    private void drawTree(Graphics g, BinarySearchTree<Integer, Kraj>.Node node, int x, int y, int offsetX) {
        if (node == null) return;

        if (node.left != null) {
            g.drawLine(x, y, x - offsetX, y + verticalSpacing);
            drawTree(g, node.left, x - offsetX, y + verticalSpacing, offsetX / 2);
        }

        if (node.right != null) {
            g.drawLine(x, y, x + offsetX, y + verticalSpacing);
            drawTree(g, node.right, x + offsetX, y + verticalSpacing, offsetX / 2);
        }

        g.setColor(Color.ORANGE);
        g.fillOval(x - NODE_RADIUS_KRAJ, y - NODE_RADIUS_KRAJ, NODE_RADIUS_KRAJ * 2, NODE_RADIUS_KRAJ * 2);
        g.setColor(Color.BLACK);
        g.drawOval(x - NODE_RADIUS_KRAJ, y - NODE_RADIUS_KRAJ, NODE_RADIUS_KRAJ * 2, NODE_RADIUS_KRAJ * 2);
        String text = node.key + ": " + node.value.getNazev();
        g.drawString(text, x - g.getFontMetrics().stringWidth(text) / 2, y - NODE_RADIUS_KRAJ - 5);

        int leafY = y + verticalSpacing * 2;
        int leafXOffset = Math.max(30, offsetX / Math.max(1, node.value.getObceList().size()));

        int numObce = Math.min(node.value.getObceList().size(), MAX_OBEC_DISPLAY);
        int startX = x - (numObce - 1) * leafXOffset / 2;

        for (int i = 0; i < numObce; i++) {
            Obec obec = node.value.getObceList().get(i);
            int leafX = startX + i * leafXOffset;

            g.drawLine(x, y + NODE_RADIUS_KRAJ, leafX, leafY - NODE_RADIUS_OBEC);

            g.setColor(Color.GREEN);
            g.fillOval(leafX - NODE_RADIUS_OBEC, leafY - NODE_RADIUS_OBEC, NODE_RADIUS_OBEC * 2, NODE_RADIUS_OBEC * 2);
            g.setColor(Color.BLACK);
            g.drawOval(leafX - NODE_RADIUS_OBEC, leafY - NODE_RADIUS_OBEC, NODE_RADIUS_OBEC * 2, NODE_RADIUS_OBEC * 2);

            Rectangle rect = new Rectangle(leafX - NODE_RADIUS_OBEC, leafY - NODE_RADIUS_OBEC, NODE_RADIUS_OBEC * 2, NODE_RADIUS_OBEC * 2);
            nodeTextMap.put(rect, obec.getName() + " (" + obec.getTotalPopulation() + ")");
        }

        if (node.value.getObceList().size() > MAX_OBEC_DISPLAY) {
            String moreText = "... (" + (node.value.getObceList().size() - MAX_OBEC_DISPLAY) + " more)";
            g.drawString(moreText, x - g.getFontMetrics().stringWidth(moreText) / 2, leafY + 20);
        }
    }

    private int getTreeHeight(BinarySearchTree<Integer, Kraj>.Node node) {
        if (node == null) return 0;
        return 1 + Math.max(getTreeHeight(node.left), getTreeHeight(node.right));
    }

    private int getMaxTreeWidth(BinarySearchTree<Integer, Kraj>.Node node) {
        if (node == null) return 0;
        return 1 + getMaxTreeWidth(node.left) + getMaxTreeWidth(node.right);
    }
}
