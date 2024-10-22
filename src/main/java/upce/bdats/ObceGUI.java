package upce.bdats;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ObceGUI extends Application {

    private ProgObyvatele progObyvatele = new ProgObyvatele();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Správa obcí");

        // Menu Bar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem importItem = new MenuItem("Import Data");
        fileMenu.getItems().add(importItem);
        menuBar.getMenus().add(fileMenu);

        // Import Data Button
        Button importButton = new Button("Import Data");
        importButton.setOnAction(e -> {
            String filePath = "path_to_your_csv";  // Replace with the actual file path
            int imported = progObyvatele.importData(filePath);
            showAlert("Data Import", imported + " záznamů bylo importováno.");
        });

        // Display Municipalities Button
        Button displayButton = new Button("Zobrazit všechny obce");
        displayButton.setOnAction(e -> {
            displayObce();
        });

        // Main Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(menuBar, importButton, displayButton);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Show alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Display all municipalities in a dialog or list
    private void displayObce() {
        // Create a new window or a dialog to display municipalities
        Stage obceStage = new Stage();
        obceStage.setTitle("Seznam obcí");

        ListView<String> listView = new ListView<>();
        for (Obec obec : progObyvatele.getAllObce()) {
            listView.getItems().add(obec.toString());  // Assuming Obec has a meaningful toString method
        }

        VBox layout = new VBox(10);
        layout.getChildren().add(listView);

        Scene scene = new Scene(layout, 300, 400);
        obceStage.setScene(scene);
        obceStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
