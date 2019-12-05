package edu.valenciacollege;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ApplicationController {
    @FXML Text nameLabel;
    String name;

    void initData(String name) {
        this.name = name;
        nameLabel.setText("Name: " + name);
    }
}
