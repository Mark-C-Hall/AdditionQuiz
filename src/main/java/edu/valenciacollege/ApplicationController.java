package edu.valenciacollege;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ApplicationController {
    @FXML Text nameLabel, timeLabel;
    String name;
    int points;

    // Starting Parameters for first question.
    void initialize(String name) {
        this.name = name;
        this.points = 0;
        nameLabel.setText("Name: " + name);
        resetClock();
    }

    private void resetClock() {
        Countdown countdown = new Countdown();
        countdown.restartClock();
        timeLabel.textProperty().bindBidirectional(countdown.timeProperty);
    }

    public void submitAnswer() {
        resetClock();
    }
}
