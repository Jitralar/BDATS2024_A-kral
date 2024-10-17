package upce.bdats;

public class Obec {
    private String psc;  // Postal Code
    private String name;
    private int men;
    private int women;

    public Obec(String psc, String name, int men, int women) {
        this.psc = psc;
        this.name = name;
        this.men = men;
        this.women = women;
    }

    public int getTotalPopulation() {
        return men + women;
    }

    @Override
    public String toString() {
        return "Obec{" +
                "psc='" + psc + '\'' +
                ", name='" + name + '\'' +
                ", men=" + men +
                ", women=" + women +
                ", total=" + getTotalPopulation() +
                '}';
    }
}


