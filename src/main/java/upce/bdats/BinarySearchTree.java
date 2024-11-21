package upce.bdats;

import java.util.Iterator;

public class BinarySearchTree<K extends Comparable<K>, V> {
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

    // Add a key-value pair to the tree
    public void insert(K key, V value) {
        root = insertRecursive(root, key, value);
    }

    private Node insertRecursive(Node node, K key, V value) {
        if (node == null) return new Node(key, value);

        if (key.compareTo(node.key) < 0) {
            node.left = insertRecursive(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insertRecursive(node.right, key, value);
        }
        return node;
    }

    // Find a value by key
    public V find(K key) {
        Node node = findRecursive(root, key);
        return node != null ? node.value : null;
    }

    private Node findRecursive(Node node, K key) {
        if (node == null || node.key.equals(key)) return node;

        if (key.compareTo(node.key) < 0) {
            return findRecursive(node.left, key);
        } else {
            return findRecursive(node.right, key);
        }
    }

    // Remove a node by key
    public void remove(K key) {
        root = removeRecursive(root, key);
    }

    private Node removeRecursive(Node node, K key) {
        if (node == null) return null;

        if (key.compareTo(node.key) < 0) {
            node.left = removeRecursive(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = removeRecursive(node.right, key);
        } else {
            // Node with one child or no child
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Node with two children: Get inorder successor
            Node temp = findMin(node.right);
            node.key = temp.key;
            node.value = temp.value;
            node.right = removeRecursive(node.right, temp.key);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // In-order iterator using custom LIFO (stack)
    public Iterator<V> inOrderIterator() {
        return new InOrderIterator(root);
    }

    private class InOrderIterator implements Iterator<V> {
        private final ABSTRLIFO<Node> stack = new ABSTRLIFO<>();

        InOrderIterator(Node root) {
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
}

