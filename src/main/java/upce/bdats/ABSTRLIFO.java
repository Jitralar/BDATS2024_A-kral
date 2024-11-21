package upce.bdats;

import java.util.LinkedList;

public class ABSTRLIFO<T> {
    private final LinkedList<T> stack = new LinkedList<>();

    public void vloz(T data) {
        stack.addFirst(data);
    }

    public T odeber() {
        return stack.pollFirst();
    }

    public boolean jePrazdny() {
        return stack.isEmpty();
    }
}
