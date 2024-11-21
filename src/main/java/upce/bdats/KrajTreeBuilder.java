package upce.bdats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class KrajTreeBuilder {
    public static BinarySearchTree<Integer, String> buildTree(String filePath) {
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields.length >= 2) {
                    // Example fields: id_kraje;kraj_name
                    int idKraje = Integer.parseInt(fields[0]);
                    String krajName = fields[1];

                    // Insert into the tree
                    tree.insert(idKraje, krajName);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return tree;
    }
}
