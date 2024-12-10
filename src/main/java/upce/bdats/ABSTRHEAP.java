package upce.bdats;

import java.util.ArrayList;
import java.util.List;

public class ABSTRHEAP {
    private List<Obec> heap;
    private boolean prioritizeByPopulation = true; // Default priority is by population

    public ABSTRHEAP() {
        heap = new ArrayList<>();
        heap.add(null); // Placeholder for 1-based indexing
    }

    public List<Obec> getHeap() {
        return heap;
    }

    public void setPriorityByPopulation(boolean prioritizeByPopulation) {
        this.prioritizeByPopulation = prioritizeByPopulation;
        reorganize();
    }


    // Build the heap
    public void buildHeap(List<Obec> obce) {
        heap.clear();
        heap.add(null); // Placeholder
        for (Obec obec : obce) {
            insert(obec);
        }
    }

    // Reorganize the heap
    public void reorganize() {
        List<Obec> temp = new ArrayList<>(heap);
        heap.clear();
        heap.add(null); // Placeholder
        for (int i = 1; i < temp.size(); i++) {
            insert(temp.get(i));
        }
    }

    // Check if heap is empty
    public boolean isEmpty() {
        return heap.size() <= 1;
    }

    // Insert an element into the heap
    public void insert(Obec obec) {
        heap.add(obec);
        bubbleUp(heap.size() - 1);
    }

    // Remove the maximum element
    public Obec removeMax() {
        if (isEmpty()) return null;
        Obec max = heap.get(1);
        heap.set(1, heap.remove(heap.size() - 1));
        bubbleDown(1);
        return max;
    }

    // Peek at the maximum element
    public Obec peekMax() {
        return isEmpty() ? null : heap.get(1);
    }

    // Print the heap elements
    public void printHeap() {
        for (int i = 1; i < heap.size(); i++) {
            System.out.println(heap.get(i));
        }
    }

    private void bubbleUp(int index) {
        while (index > 1) {
            int parentIndex = index / 2;
            if (compare(heap.get(index), heap.get(parentIndex)) > 0) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void bubbleDown(int index) {
        while (index * 2 < heap.size()) {
            int childIndex = index * 2;
            if (childIndex + 1 < heap.size() &&
                    compare(heap.get(childIndex + 1), heap.get(childIndex)) > 0) {
                childIndex++;
            }
            if (compare(heap.get(childIndex), heap.get(index)) > 0) {
                swap(index, childIndex);
                index = childIndex;
            } else {
                break;
            }
        }
    }

    private int compare(Obec o1, Obec o2) {
        if (prioritizeByPopulation) {
            return Integer.compare(o1.getTotalPopulation(), o2.getTotalPopulation());
        } else {
            return o1.getName().compareTo(o2.getName());
        }
    }

    private void swap(int i, int j) {
        Obec temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
