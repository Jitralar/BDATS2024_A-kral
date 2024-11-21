package upce.bdats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class KrajTreeBuilder {
    public static BinarySearchTree<Integer, Kraj> buildTree(String krajeFilePath) {
        BinarySearchTree<Integer, Kraj> tree = new BinarySearchTree<>();

        try (BufferedReader br = new BufferedReader(new FileReader(krajeFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields.length >= 6) {
                    int idKraje = Integer.parseInt(fields[0]); // Region ID
                    String krajName = fields[1];               // Region name
                    String psc = fields[2];                   // Postal code
                    String obecName = fields[3];              // Municipality name
                    int men = Integer.parseInt(fields[4]);    // Male population
                    int women = Integer.parseInt(fields[5]);  // Female population

                    // Insert or find the Kraj node
                    BinarySearchTree<Integer, Kraj>.Node krajNode = tree.findNodeByKey(idKraje);
                    if (krajNode == null) {
                        Kraj kraj = new Kraj(idKraje, krajName);
                        tree.insert(idKraje, kraj);
                        krajNode = tree.findNodeByKey(idKraje);
                    }

                    // Add the Obec to the Kraj
                    if (krajNode != null) {
                        krajNode.value.getObceList().vlozPosledni(new Obec(psc, obecName, men, women));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return tree;
    }
}
