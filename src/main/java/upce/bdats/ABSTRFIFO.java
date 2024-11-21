package upce.bdats;

import java.util.LinkedList;

public class ABSTRFIFO<T> {
    private final LinkedList<T> queue = new LinkedList<>();

    public void vloz(T data) {
        queue.addLast(data);
    }

    public T odeber() {
        return queue.pollFirst();
    }

    public boolean jePrazdny() {
        return queue.isEmpty();
    }
}
