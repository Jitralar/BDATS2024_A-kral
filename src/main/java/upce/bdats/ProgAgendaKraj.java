package upce.bdats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Iterator;

public class ProgAgendaKraj extends JFrame {
    private AgendaKraj agendaKraj;
    private JTextField searchField, pscField, nameField, menField, womenField;
    private JTextArea resultDisplay;
    private JTable obecTable;
    private Iterator<Obec> iterator;

    public ProgAgendaKraj() {
        agendaKraj = new AgendaKraj();

        setTitle("Agenda Kraj");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this::handleSearch);
        searchPanel.add(new JLabel("Search by Municipality Name: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Input Panel for Municipality Data
        JPanel inputPanel = new JPanel();
        pscField = new JTextField(5);
        nameField = new JTextField(10);
        menField = new JTextField(5);
        womenField = new JTextField(5);
        JButton addButton = new JButton("Add Municipality");
        addButton.addActionListener(this::handleAdd);
        JButton deleteButton = new JButton("Delete Municipality");
        deleteButton.addActionListener(this::handleDelete);
        inputPanel.add(new JLabel("PSC: "));
        inputPanel.add(pscField);
        inputPanel.add(new JLabel("Name: "));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Men: "));
        inputPanel.add(menField);
        inputPanel.add(new JLabel("Women: "));
        inputPanel.add(womenField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        // Data Generation and Tree Building Panel
        JPanel actionPanel = new JPanel();
        JButton generateButton = new JButton("Generate Data");
        generateButton.addActionListener(this::handleGenerate);
        JButton buildButton = new JButton("Build Balanced Tree");
        buildButton.addActionListener(this::handleBuildTree);
        actionPanel.add(generateButton);
        actionPanel.add(buildButton);

        // Display and Export Panel
        resultDisplay = new JTextArea(8, 40);
        resultDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultDisplay);
        JButton saveButton = new JButton("Save to File");
        saveButton.addActionListener(this::handleSaveToFile);
        JButton loadButton = new JButton("Load from File");
        loadButton.addActionListener(this::handleLoadFromFile);

        // Iterator Control Panel
        JPanel iteratorPanel = new JPanel();
        JButton nextButton = new JButton("Next Municipality");
        nextButton.addActionListener(this::handleNext);
        iteratorPanel.add(nextButton);

        // Layout
        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.WEST);
        add(iteratorPanel, BorderLayout.EAST);
        add(saveButton, BorderLayout.PAGE_END);
        add(loadButton, BorderLayout.PAGE_START);
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
        String psc = pscField.getText();
        String name = nameField.getText();
        int men = Integer.parseInt(menField.getText());
        int women = Integer.parseInt(womenField.getText());
        Obec obec = new Obec(psc,name, men, women);
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
        if (iterator == null) iterator = agendaKraj.vytvorIterator();
        if (iterator.hasNext()) {
            Obec next = iterator.next();
            resultDisplay.setText(next.toString());
        } else {
            resultDisplay.setText("End of municipalities.");
        }
    }

    private void handleSaveToFile(ActionEvent event) {
        // Serialize data to a file (example code)
    }

    private void handleLoadFromFile(ActionEvent event) {
        // Load data from a file (example code)
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProgAgendaKraj().setVisible(true));
    }
}