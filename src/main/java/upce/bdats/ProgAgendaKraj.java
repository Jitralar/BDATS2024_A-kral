package upce.bdats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class ProgAgendaKraj extends JFrame {
    private AgendaKraj agendaKraj;
    private JTextField searchField, nameField, postalCodeField, menField, womenField;
    private JTextArea resultDisplay;
    private Iterator<Obec> iterator;
    private Obec currentObec;

    public ProgAgendaKraj() {
        agendaKraj = new AgendaKraj();

        setTitle("Agenda Kraj");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this::handleSearch);
        searchPanel.add(new JLabel("Search by Municipality Name: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Input Panel for Municipality Data
        JPanel inputPanel = new JPanel(new GridLayout(3, 4, 5, 5));
        nameField = new JTextField(10);
        postalCodeField = new JTextField(6);
        menField = new JTextField(5);
        womenField = new JTextField(5);
        JButton addButton = new JButton("Add Municipality");
        addButton.addActionListener(this::handleAdd);
        JButton deleteButton = new JButton("Delete Municipality");
        deleteButton.addActionListener(this::handleDelete);
        inputPanel.add(new JLabel("Name: "));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Postal Code: "));
        inputPanel.add(postalCodeField);
        inputPanel.add(new JLabel("Men: "));
        inputPanel.add(menField);
        inputPanel.add(new JLabel("Women: "));
        inputPanel.add(womenField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        // Action Panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton generateButton = new JButton("Generate Data");
        generateButton.addActionListener(this::handleGenerate);
        JButton buildButton = new JButton("Build Balanced Tree");
        buildButton.addActionListener(this::handleBuildTree);
        actionPanel.add(generateButton);
        actionPanel.add(buildButton);

        // Iterator Control Panel
        JPanel iteratorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton previousButton = new JButton("Previous Municipality");
        previousButton.addActionListener(this::handlePrevious);
        JButton nextButton = new JButton("Next Municipality");
        nextButton.addActionListener(this::handleNext);
        iteratorPanel.add(previousButton);
        iteratorPanel.add(nextButton);

        // Result Display Area
        resultDisplay = new JTextArea(10, 50);
        resultDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultDisplay);

        // Layout
        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
        add(iteratorPanel, BorderLayout.EAST);
        add(scrollPane, BorderLayout.WEST);
    }

    // Button Actions

    private void handleSearch(ActionEvent event) {
        String name = searchField.getText();
        Obec obec = agendaKraj.najdi(name);
        if (obec != null) {
            resultDisplay.setText(obec.toString());
        } else {
            resultDisplay.setText("Municipality not found.");
        }
    }

    private void handleAdd(ActionEvent event) {
        String name = nameField.getText();
        String postalCode = postalCodeField.getText();
        int men = Integer.parseInt(menField.getText());
        int women = Integer.parseInt(womenField.getText());
        Obec obec = new Obec(name, postalCode, men, women);
        agendaKraj.vloz(obec);
        resultDisplay.setText("Added: " + obec);
    }

    private void handleDelete(ActionEvent event) {
        String name = nameField.getText();
        Obec removed = agendaKraj.odeber(name);
        if (removed != null) {
            resultDisplay.setText("Deleted: " + removed);
        } else {
            resultDisplay.setText("Municipality not found.");
        }
    }

    private void handleGenerate(ActionEvent event) {
        agendaKraj.generuj();
        resultDisplay.setText("Sample data generated.");
    }

    private void handleBuildTree(ActionEvent event) {
        agendaKraj.vybuduj();
        resultDisplay.setText("Balanced tree built.");
    }

    private void handleNext(ActionEvent event) {
        if (iterator == null) {
            iterator = agendaKraj.vytvorIterator();
        }
        if (iterator.hasNext()) {
            currentObec = iterator.next();
            resultDisplay.setText(currentObec.toString());
        } else {
            resultDisplay.setText("End of municipalities.");
        }
    }

    private void handlePrevious(ActionEvent event) {
        if (iterator == null || currentObec == null) {
            resultDisplay.setText("No previous municipality available.");
            return;
        }
        // Simple logic to reset and re-iterate for the previous item
        iterator = agendaKraj.vytvorIterator();
        Obec previousObec = null;
        while (iterator.hasNext()) {
            Obec obec = iterator.next();
            if (obec.equals(currentObec)) break;
            previousObec = obec;
        }
        if (previousObec != null) {
            currentObec = previousObec;
            resultDisplay.setText(previousObec.toString());
        } else {
            resultDisplay.setText("No previous municipality available.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProgAgendaKraj().setVisible(true));
    }
}
