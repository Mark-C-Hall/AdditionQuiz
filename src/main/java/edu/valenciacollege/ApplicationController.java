package edu.valenciacollege;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class ApplicationController {
    @FXML Text nameLabel, timeLabel;
    @FXML Text objectLabel1, objectLabel2;
    @FXML Button answerLabel1, answerLabel2, answerLabel3, answerLabel4;
    String name;
    int points;
    Countdown countdown;
    QuestionAndAnswerState qState;

    // Starting Parameters for first question.
    void initialize(String name) {
        this.name = name;
        this.points = 0;
        nameLabel.setText("Name: " + name);
        startClock();
        try {
            qState = new QuestionAndAnswerState();
            bindTextToState();
        } catch (IOException e) {
            System.out.println("Caught: " + e.getMessage());
        }
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

    // Method for setting the questions and answer texts.
    private void bindTextToState() {
        objectLabel1.textProperty().bindBidirectional(qState.object1);
        objectLabel2.textProperty().bindBidirectional(qState.object2);
        answerLabel1.textProperty().bindBidirectional(qState.answer1);
        answerLabel2.textProperty().bindBidirectional(qState.answer2);
        answerLabel3.textProperty().bindBidirectional(qState.answer3);
        answerLabel4.textProperty().bindBidirectional(qState.answer4);
    }

    // Method for pressing answer button.
    public void submitAnswer() {
        resetClock();
    }
}
