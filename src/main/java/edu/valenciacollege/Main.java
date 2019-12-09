package edu.valenciacollege;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Mark Hall
 * December 9th 2019
 * Advanced Java COP 2805C
 * Final Java Project
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/index.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setTitle("Addition Quiz");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

// TODO Next question if time runs out
// TODO Final question
// TODO Stack answers
// TODO Database
// TODO Clean Code
