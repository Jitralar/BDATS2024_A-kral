package upce.bdats;

import upce.bdats.AbstrDoubleList;
import upce.bdats.Obec;

public class Kraj {
    private int id;  // ID of the region (county)
    private String nazev;  // Name of the region (county)
    private AbstrDoubleList<Obec> obceList;  // List of municipalities (obce)

    // Constructor
    public Kraj(int id, String nazev) {
        this.id = id;
        this.nazev = nazev;
        this.obceList = new AbstrDoubleList<>();  // Initialize the list of municipalities

    }

    // Getter for the name of the region
    public String getNazev() {
        return nazev;
    }

    public int getId() {
        return id;
    }

    // Getter for the list of municipalities
    public AbstrDoubleList<Obec> getObceList() {
        return obceList;
    }

    @Override
    public String toString() {
        return "Kraj: " + nazev;
    }
}
