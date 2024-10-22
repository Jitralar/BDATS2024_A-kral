package upce.bdats;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleLinkedList<T> implements Iterable<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private Node<T> current = null;
    private int size = 0;

    private static class Node<T> {
        T data;
        Node<T> next, prev;

        Node(T data) {
            this.data = data;
        }
    }

    // Clear the list
    public void zrus() {
        head = tail = current = null;
        size = 0;
    }

    // Check if the list is empty
    public boolean jePrazdny() {
        return size == 0;
    }

    // Insert at the first position
    public void vlozPrvni(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // Insert at the last position
    public void vlozPosledni(T data) {
        if (tail == null) {
            vlozPrvni(data);
        } else {
            Node<T> newNode = new Node<>(data);
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            size++;
        }
    }

    // Insert after the current node
    public void vlozNaslednika(T data) {
        if (current == null) throw new NoSuchElementException("No current element.");
        Node<T> newNode = new Node<>(data);
        newNode.next = current.next;
        newNode.prev = current;
        if (current.next != null) current.next.prev = newNode;
        current.next = newNode;
        if (current == tail) tail = newNode;
        size++;
    }

    // Insert before the current node
    public void vlozPredchudce(T data) {
        if (current == null) throw new NoSuchElementException("No current element.");
        Node<T> newNode = new Node<>(data);
        newNode.prev = current.prev;
        newNode.next = current;
        if (current.prev != null) current.prev.next = newNode;
        current.prev = newNode;
        if (current == head) head = newNode;
        size++;
    }

    // Access the current node
    public T zpristupniAktualni() {
        if (current == null) throw new NoSuchElementException("No current element.");
        return current.data;
    }

    // Access the first node
    public T zpristupniPrvni() {
        if (head == null) throw new NoSuchElementException("The list is empty.");
        current = head;
        return current.data;
    }

    // Access the last node
    public T zpristupniPosledni() {
        if (tail == null) throw new NoSuchElementException("The list is empty.");
        current = tail;
        return current.data;
    }

    // Access the next node after the current one
    public T zpristupniNaslednika() {
        if (current == null || current.next == null) throw new NoSuchElementException("No next element.");
        current = current.next;
        return current.data;
    }

    // Access the previous node before the current one
    public T zpristupniPredchudce() {
        if (current == null || current.prev == null) throw new NoSuchElementException("No previous element.");
        current = current.prev;
        return current.data;
    }

    // Remove the current node
    public T odeberAktualni() {
        if (current == null) throw new NoSuchElementException("No current element.");
        T data = current.data;
        if (current == head) {
            return odeberPrvni();
        } else if (current == tail) {
            return odeberPosledni();
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
            current = head; // Reset to first after removal
            size--;
            return data;
        }
    }

    // Remove the first node
    public T odeberPrvni() {
        if (head == null) throw new NoSuchElementException("The list is empty.");
        T data = head.data;
        if (head == tail) { // Single element case
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        current = head;
        size--;
        return data;
    }

    // Remove the last node
    public T odeberPosledni() {
        if (tail == null) throw new NoSuchElementException("The list is empty.");
        T data = tail.data;
        if (head == tail) { // Single element case
            return odeberPrvni();
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        current = head;
        size--;
        return data;
    }

    // Remove the next node after the current one
    public T odeberNaslednika() {
        zpristupniNaslednika();
        return odeberAktualni();
    }

    // Remove the previous node before the current one
    public T odeberPredchudce() {
        zpristupniPredchudce();
        return odeberAktualni();
    }

    // Iterator for traversing the list
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> iterCurrent = head;
            private boolean isFirst = true;

            @Override
            public boolean hasNext() {
                return iterCurrent != null && (isFirst || iterCurrent != head);
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T data = iterCurrent.data;
                iterCurrent = iterCurrent.next;
                isFirst = false;
                return data;
            }
        };
    }

    // Get the size of the list
    public int size() {
        return size;
    }
}
