package upce.bdats;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ObceGUI extends Application {

    private ProgObyvatele progObyvatele = new ProgObyvatele();  // Use ProgObyvatele logic
    private ListView<String> obceListView = new ListView<>();   // For displaying obce

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Správa obcí");

        // Menu Bar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem importItem = new MenuItem("Importovat Data");
        fileMenu.getItems().add(importItem);
        menuBar.getMenus().add(fileMenu);

        // Import Data Button Action
        importItem.setOnAction(e -> importData(primaryStage));



        // Main Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(menuBar, obceListView);

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Import Data Method (GUI)
    private void importData(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Vybrat soubor CSV");
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            int imported = progObyvatele.importData(file.getAbsolutePath());
            showAlert("Import Dat", imported + " záznamů bylo úspěšně importováno.");
        }
    }


    // Utility method to show alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
