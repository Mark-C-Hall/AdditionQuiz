package edu.valenciacollege;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ApplicationController {
    @FXML Text nameLabel, timeLabel;
    String name;
    int points;
    Countdown countdown;

    // Starting Parameters for first question.
    void initialize(String name) {
        this.name = name;
        this.points = 0;
        nameLabel.setText("Name: " + name);
        startClock();
    }

    // Starts the first Countdown Thread.
    private void startClock() {
        countdown = new Countdown();
        timeLabel.textProperty().bindBidirectional(countdown.timeProperty);
    }

    // Ends current thread and starts new one.
    private void resetClock() {
        countdown.stop();
        startClock();
    }

    // Method for pressing answer button.
    public void submitAnswer() {
        resetClock();
    }
}
