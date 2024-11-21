package upce.bdats;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class AbstrDoubleList<T> implements Iterable<T> {

    private Node<T> head = null;
    private Node<T> tail = null;
    private Node<T> current = null;
    private int size = 0;

    public T get(int i) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException("Index out of bounds.");
        Node<T> node = head;
        for (int j = 0; j < i; j++) {
            node = node.next;
        }
        return node.data;
    }

    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

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
            newNode.next = newNode;
            newNode.prev = newNode;
        } else {
            newNode.next = head;
            newNode.prev = tail;
            head.prev = newNode;
            tail.next = newNode;
            head = newNode;
        }
        size++;
    }

    // Insert at the last position
    public void vlozPosledni(T data) {
        if (head == null) {
            vlozPrvni(data);
        } else {
            Node<T> newNode = new Node<>(data);
            newNode.prev = tail;
            newNode.next = head;
            tail.next = newNode;
            head.prev = newNode;
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
        current.next.prev = newNode;
        current.next = newNode;
        if (current == tail) {
            tail = newNode;
        }
        size++;
    }

    // Insert before the current node
    public void vlozPredchudce(T data) {
        if (current == null) throw new NoSuchElementException("No current element.");
        Node<T> newNode = new Node<>(data);
        newNode.prev = current.prev;
        newNode.next = current;
        current.prev.next = newNode;
        current.prev = newNode;
        if (current == head) {
            head = newNode;
        }
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
            odeberPrvni();
        } else if (current == tail) {
            odeberPosledni();
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
            current = head;
            size--;
        }
        return data;
    }

    // Remove the first node
    public T odeberPrvni() {
        if (head == null) throw new NoSuchElementException("The list is empty.");
        T data = head.data;
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = tail;
            tail.next = head;
        }
        current = head;
        size--;
        return data;
    }

    // Remove the last node
    public T odeberPosledni() {
        if (tail == null) throw new NoSuchElementException("The list is empty.");
        T data = tail.data;
        if (head == tail) {
            return odeberPrvni();
        } else {
            tail = tail.prev;
            tail.next = head;
            head.prev = tail;
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
