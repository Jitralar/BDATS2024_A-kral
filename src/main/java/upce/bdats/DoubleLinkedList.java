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

    // Základní ovládání (Basic operations)

    public void zrus() {
        head = tail = current = null;
        size = 0;
    }

    public boolean jePrazdny() {
        return size == 0;
    }

    public void vlozPrvni(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = tail = newNode;
            head.next = head.prev = newNode;
        } else {
            newNode.next = head;
            newNode.prev = tail;
            head.prev = newNode;
            tail.next = newNode;
            head = newNode;
        }
        size++;
    }

    public void vlozPosledni(T data) {
        if (tail == null) {
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

    public void vlozNaslednika(T data) {
        if (current == null) throw new NoSuchElementException("Aktuální prvek není nastaven.");
        Node<T> newNode = new Node<>(data);
        newNode.next = current.next;
        newNode.prev = current;
        current.next.prev = newNode;
        current.next = newNode;
        if (current == tail) tail = newNode;
        size++;
    }

    public void vlozPredchudce(T data) {
        if (current == null) throw new NoSuchElementException("Aktuální prvek není nastaven.");
        Node<T> newNode = new Node<>(data);
        newNode.next = current;
        newNode.prev = current.prev;
        current.prev.next = newNode;
        current.prev = newNode;
        if (current == head) head = newNode;
        size++;
    }

    // Zpřístupnění seznamu (Accessing list elements)

    public T zpristupniAktualni() {
        if (current == null) throw new NoSuchElementException("Aktuální prvek není nastaven.");
        return current.data;
    }

    public T zpristupniPrvni() {
        if (head == null) throw new NoSuchElementException("Seznam je prázdný.");
        current = head;
        return current.data;
    }

    public T zpristupniPosledni() {
        if (tail == null) throw new NoSuchElementException("Seznam je prázdný.");
        current = tail;
        return current.data;
    }

    public T zpristupniNaslednika() {
        if (current == null || current.next == head) throw new NoSuchElementException("Následník neexistuje.");
        current = current.next;
        return current.data;
    }

    public T zpristupniPredchudce() {
        if (current == null || current.prev == tail) throw new NoSuchElementException("Předchůdce neexistuje.");
        current = current.prev;
        return current.data;
    }

    // Odebírání prvků (Removing elements)

    public T odeberAktualni() {
        if (current == null) throw new NoSuchElementException("Aktuální prvek není nastaven.");
        T data = current.data;
        if (current == head) return odeberPrvni();
        if (current == tail) return odeberPosledni();
        current.prev.next = current.next;
        current.next.prev = current.prev;
        current = head;
        size--;
        return data;
    }

    public T odeberPrvni() {
        if (head == null) throw new NoSuchElementException("Seznam je prázdný.");
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

    public T odeberPosledni() {
        if (tail == null) throw new NoSuchElementException("Seznam je prázdný.");
        T data = tail.data;
        if (head == tail) {
            return odeberPrvni();
        } else {
            tail = tail.prev;
            tail.next = head;
            head.prev = tail;
            current = head;
        }
        size--;
        return data;
    }

    public T odeberNaslednika() {
        zpristupniNaslednika();
        return odeberAktualni();
    }

    public T odeberPredchudce() {
        zpristupniPredchudce();
        return odeberAktualni();
    }

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
}
