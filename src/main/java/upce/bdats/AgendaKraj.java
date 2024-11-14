package upce.bdats;

import java.util.Iterator;

public class AgendaKraj {
    private ABSTRTABLE<String, Obec> table;

    public AgendaKraj() {
        table = new ABSTRTABLE<>();
    }

    // Finds a municipality by name
    public Obec najdi(String name) {
        return table.najdi(name);
    }

    // Inserts a municipality into the table
    public void vloz(Obec obec) {
        table.vloz(obec.getName(), obec);
    }

    // Removes a municipality by name
    public Obec odeber(String name) {
        return table.odeber(name);
    }

    // Builds a balanced tree (placeholder for actual balancing logic)
    public void vybuduj() {
        // Implement tree balancing logic here if needed
    }

    // Generates sample municipalities for testing
    public void generuj() {
        vloz(new Obec("Praha","test1" ,1000000, 1100000));
        vloz(new Obec("Brno","test1" ,400000, 450000));
        vloz(new Obec("Ostrava", "test1",300000, 320000));
        vloz(new Obec("Plzeň", "test1",170000, 180000));
    }

    // Creates an iterator for in-order traversal
    public Iterator<Obec> vytvorIterator() {
        return table.vytvorIterator();
    }
}
