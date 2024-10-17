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

    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = tail = newNode;
            head.next = head.prev = newNode; // Circular reference
        } else {
            newNode.next = head;
            newNode.prev = tail;
            head.prev = newNode;
            tail.next = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (tail == null) {
            addFirst(data); // If empty, use addFirst
        } else {
            newNode.prev = tail;
            newNode.next = head;
            tail.next = newNode;
            head.prev = newNode;
            tail = newNode;
            size++;
        }
    }

    public T removeFirst() {
        if (head == null) throw new NoSuchElementException("List is empty.");
        T data = head.data;
        if (head == tail) { // Only one element
            head = tail = null;
        } else {
            head = head.next;
            head.prev = tail;
            tail.next = head;
        }
        size--;
        return data;
    }

    public T removeLast() {
        if (tail == null) throw new NoSuchElementException("List is empty.");
        T data = tail.data;
        if (head == tail) { // Only one element
            return removeFirst();
        } else {
            tail = tail.prev;
            tail.next = head;
            head.prev = tail;
            size--;
        }
        return data;
    }

    public T getCurrent() {
        if (current == null) throw new NoSuchElementException("No current element set.");
        return current.data;
    }

    public void resetCurrent() {
        current = head;
    }

    public void moveNext() {
        if (current != null) current = current.next;
    }

    public void movePrev() {
        if (current != null) current = current.prev;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
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
