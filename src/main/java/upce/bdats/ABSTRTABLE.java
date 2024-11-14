package upce.bdats;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class ABSTRTABLE<K extends Comparable<K>, V> {
    private class Node {
        K key;
        V value;
        Node left, right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;

    // Clears the entire table
    public void zrus() {
        root = null;
    }

    // Checks if the table is empty
    public boolean jePrazdny() {
        return root == null;
    }

    // Finds a value by key
    public V najdi(K key) {
        Node node = najdiNode(root, key);
        return (node == null) ? null : node.value;
    }

    // Inserts a new key-value pair
    public void vloz(K key, V value) {
        root = vlozNode(root, key, value);
    }

    // Removes a value by key
    public V odeber(K key) {
        Node node = najdiNode(root, key);
        if (node != null) {
            root = odeberNode(root, key);
            return node.value;
        }
        return null;
    }

    // Private helper method to find a node by key
    private Node najdiNode(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) return najdiNode(node.left, key);
        else if (cmp > 0) return najdiNode(node.right, key);
        else return node;
    }

    // Private helper method to insert a node
    private Node vlozNode(Node node, K key, V value) {
        if (node == null) return new Node(key, value);
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = vlozNode(node.left, key, value);
        else if (cmp > 0) node.right = vlozNode(node.right, key, value);
        else node.value = value; // Update value if key already exists
        return node;
    }

    // Private helper method to remove a node
    private Node odeberNode(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = odeberNode(node.left, key);
        else if (cmp > 0) node.right = odeberNode(node.right, key);
        else {
            // Node with one child or no child
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Node with two children: Find the inorder successor
            Node temp = node;
            node = min(temp.right);
            node.right = deleteMin(temp.right);
            node.left = temp.left;
        }
        return node;
    }

    // Finds the minimum node in a subtree
    private Node min(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // Deletes the minimum node in a subtree
    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        return node;
    }

    // Iterator for in-order traversal
    public Iterator<V> vytvorIterator() {
        return new InOrderIterator(root);
    }

    // In-order iterator class
    private class InOrderIterator implements Iterator<V> {
        private Stack<Node> stack = new Stack<>();

        InOrderIterator(Node root) {
            pushLeft(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public V next() {
            if (!hasNext()) throw new NoSuchElementException();
            Node node = stack.pop();
            pushLeft(node.right);
            return node.value;
        }

        private void pushLeft(Node node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
    }
}
