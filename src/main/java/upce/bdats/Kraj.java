package upce.bdats;

import upce.bdats.DoubleLinkedList;
import upce.bdats.Obec;


public class Kraj {
    private DoubleLinkedList<Obec> obceList = new DoubleLinkedList<>();

    public void addObec(Obec obec) {
        obceList.addLast(obec);
    }

    public DoubleLinkedList<Obec> getObce() {
        return obceList;
    }

    // Method to calculate average population
    public float getAveragePopulation() {
        int totalPopulation = 0;
        int count = 0;

        for (Obec obec : obceList) {
            totalPopulation += obec.getTotalPopulation();
            count++;
        }

        return count > 0 ? (float) totalPopulation / count : 0;
    }

    public void displayObce() {
        for (Obec obec : obceList) {
            System.out.println(obec);
        }
    }
}
