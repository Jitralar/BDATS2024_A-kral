package upce.bdats;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A generic Binary Search Tree (BST) implementation
 * @param <K> The type of keys used for ordering the tree (must be Comparable)
 * @param <V> The type of values associated with the keys
 */
public class BinarySearchTree<K extends Comparable<K>, V> {
    // Node class to represent elements in the tree
    public class Node {
        K key;       // The key used to order the tree
        V value;     // The value associated with the key
        Node left;   // Left child
        Node right;  // Right child
        List<Obec> obce; // List of Obec (municipalities) under this Kraj


        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.obce = new ArrayList<>();
        }
    }

    private Node root;          // Root of the tree
    private boolean useLIFO = true; // Toggle for traversal method (LIFO or FIFO)

    /**
     * Toggle the traversal method between LIFO and FIFO
     */
    public void toggleTraversalMethod() {
        useLIFO = !useLIFO;
        System.out.println("Traversal method set to: " + (useLIFO ? "LIFO" : "FIFO"));
    }

    /**
     * Insert a new key-value pair into the tree
     * @param key The key for ordering
     * @param value The value associated with the key
     */
    public void insert(K key, V value) {
        root = insertRecursive(root, key, value);
    }

    private Node insertRecursive(Node node, K key, V value) {
        if (node == null) return new Node(key, value); // Create a new node if the position is empty

        if (key.compareTo(node.key) < 0) {            // Key is smaller, go left
            node.left = insertRecursive(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {     // Key is larger, go right
            node.right = insertRecursive(node.right, key, value);
        } else {                                      // Key already exists, update value
            node.value = value;
        }
        return node;
    }

    /**
     * Find a value by its key
     * @param key The key to search for
     * @return The value associated with the key, or null if not found
     */
    public V find(K key) {
        Node node = findRecursive(root, key);
        return node != null ? node.value : null;
    }

    private Node findRecursive(Node node, K key) {
        if (node == null) return null;               // Key not found
        if (key.equals(node.key)) return node;       // Key found
        if (key.compareTo(node.key) < 0) {           // Key is smaller, search left
            return findRecursive(node.left, key);
        } else {                                     // Key is larger, search right
            return findRecursive(node.right, key);
        }
    }

    /**
     * Find a node by its key
     * @param key The key to search for
     * @return The node with the key, or null if not found
     */
    public Node findNodeByKey(K key) {
        return findRecursive(root, key);
    }

    /**
     * Remove a node by its key
     * @param key The key of the node to remove
     */
    public void remove(K key) {
        root = removeRecursive(root, key);
    }

    private Node removeRecursive(Node node, K key) {
        if (node == null) return null; // Key not found

        if (key.compareTo(node.key) < 0) {           // Key is smaller, go left
            node.left = removeRecursive(node.left, key);
        } else if (key.compareTo(node.key) > 0) {    // Key is larger, go right
            node.right = removeRecursive(node.right, key);
        } else {                                     // Node to remove found
            if (node.left == null) return node.right; // Node with only right child or no children
            if (node.right == null) return node.left; // Node with only left child

            // Node with two children: Find the smallest value in the right subtree
            Node smallest = findMin(node.right);
            node.key = smallest.key;
            node.value = smallest.value;
            node.right = removeRecursive(node.right, smallest.key); // Remove the smallest node
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Print the tree structure (in-order traversal)
     */
    public void printTree() {
        printRecursive(root, 0);
    }

    private void printRecursive(Node node, int depth) {
        if (node == null) return;

        printRecursive(node.right, depth + 1);                 // Print right subtree
        System.out.println(" ".repeat(depth * 4) + node.key);  // Print current node
        printRecursive(node.left, depth + 1);                  // Print left subtree
    }

    /**
     * Get an iterator for in-order traversal based on the toggle (LIFO or FIFO)
     * @return Iterator over values in the tree
     */
    public Iterator<V> inOrderIterator() {
        return useLIFO ? new InOrderIteratorLIFO(root) : new InOrderIteratorFIFO(root);
    }

    // LIFO-based in-order iterator
    private class InOrderIteratorLIFO implements Iterator<V> {
        private final ABSTRLIFO<Node> stack = new ABSTRLIFO<>();

        InOrderIteratorLIFO(Node root) {
            while (root != null) {
                stack.vloz(root);
                root = root.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.jePrazdny();
        }

        @Override
        public V next() {
            Node current = stack.odeber();
            V value = current.value;

            if (current.right != null) {
                Node node = current.right;
                while (node != null) {
                    stack.vloz(node);
                    node = node.left;
                }
            }
            return value;
        }
    }

    // FIFO-based in-order iterator
    private class InOrderIteratorFIFO implements Iterator<V> {
        private final ABSTRFIFO<Node> queue = new ABSTRFIFO<>();

        InOrderIteratorFIFO(Node root) {
            enqueueLeft(root);
        }

        private void enqueueLeft(Node node) {
            while (node != null) {
                queue.vloz(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !queue.jePrazdny();
        }

        @Override
        public V next() {
            Node current = queue.odeber();
            V value = current.value;

            if (current.right != null) {
                enqueueLeft(current.right);
            }
            return value;
        }
    }

    // Add this method to the BinarySearchTree class
    public Node getRoot() {
        return root;
    }

    // Add this method to the BinarySearchTree class
    public boolean isUsingLIFO() {
        return useLIFO;
    }



}
