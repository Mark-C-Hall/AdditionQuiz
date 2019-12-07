package edu.valenciacollege;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class IndexController {
    @FXML TextField nameField;

    // Changes scene to the application window if name is entered.
    public void changeSceneToApplication(ActionEvent event) throws IOException {
        if (isNameValid()) {
            changeScene(event);
        } else {
            throwInvalidEntryWindow();
        }
    }

    // Checks if name field is left blank.
    private boolean isNameValid() {
        return !nameField.getText().equals("");
    }

    // Handles JavaFX scene change.
    private void changeScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application.fxml"));
        Parent applicationViewParent = loader.load();

        ApplicationController controller = loader.getController();

        controller.initialize(nameField.getText());
        Scene applicationViewScene = new Scene(applicationViewParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(applicationViewScene);
        window.show();
    }

    // Handles JavaFX popup error window.
    private void throwInvalidEntryWindow() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Error");
        window.setMinWidth(200);

        Label label = new Label("Please enter your name.");
        VBox layout = new VBox();
        layout.getChildren().add(label);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 200, 100);

        window.setScene(scene);
        window.showAndWait();
    }
}
