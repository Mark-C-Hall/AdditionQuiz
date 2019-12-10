package edu.valenciacollege;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Stack;

/*
 * Controller class for main JavaFX Application.
 */
public class ApplicationController {
    // JavaFX Nodes.
    @FXML Text nameLabel, timeLabel, pointsLabel;
    @FXML Text objectLabel1, objectLabel2;
    @FXML Button answerLabel1, answerLabel2, answerLabel3, answerLabel4, reportButton;
    @FXML VBox vbox;

    // Class fields.
    private String name;
    private int points;
    private Countdown countdown;
    private QuestionAndAnswerState qState;
    private Stack<String> timeStamps;
    private QuizDatabase db;

    // Starting Parameters for first question.
    void initialize(String name) {
        this.name = name;
        this.points = 0;

        // Intialize objects.
        qState = new QuestionAndAnswerState();
        timeStamps = new Stack<>();
        db = new QuizDatabase();
        db.connectToDb();


        // Bind JavaFX Properties.
        nameLabel.setText("Name: " + name);
        bindTextToState();
        bindPoints();

        // Begin Timer.
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


    // Binds JavaFX pointsLabel to score.
    private void bindPoints() {
        pointsLabel.textProperty().setValue(String.valueOf(this.points));
    }

    // Starts the first Countdown Thread.
    private void startClock() {
        countdown = new Countdown();
        timeLabel.textProperty().bindBidirectional(countdown.timeProperty);
    }

    // Ends current thread and starts new one.
    private void resetClock() {
        countdown.stop();
        countdown = new Countdown();
        timeLabel.textProperty().bindBidirectional(countdown.timeProperty);
    }

    // Method for pressing answer button.
    public void submitAnswer(ActionEvent event) {
        String answerSelected = ((Button)event.getSource()).getText();
        String correctAnswer = calculateAnswer(qState);
        if (answerSelected.equals(correctAnswer)) {
            correctAnswer();
        } else {
            incorrectAnswer();
        }
        // Check for final question.
        if (qState.getQuestionNumber() == 9) {
            finishQuiz();
        } else {
            qState.newQuestion();
        }
    }

    // Returns computed answer as String.
    private String calculateAnswer(QuestionAndAnswerState state) {
        return state.getCorrectAnswer();
    }

    // Method for selecting correct response.
    private void correctAnswer() {
        points += 1;
        addToStack(true);
        bindPoints();
        resetClock();
    }

    // Method for selecting incorrect response.
    private void incorrectAnswer() {
        addToStack(false);
        resetClock();
    }

    // Adds timestamp info to stack. Takes boolean as parameter whether response was correct or not.
    private void addToStack(boolean isCorrect) {
        String userQuestionNumber = String.valueOf(qState.getQuestionNumber() + 1);
        String questionNumberString;
        if (isCorrect) {
            questionNumberString = "Question " + userQuestionNumber + ": Correct!\n";
        } else {
            questionNumberString = "Question " + userQuestionNumber + ": Incorrect!\n";
        }
        String pointsString = "Points: " + points + "\t";
        String timeStampString = "Time: " + timeLabel.getText() + "\n";

        String result = questionNumberString + pointsString + timeStampString;

        timeStamps.push(result);
    }

    // Method for finalizing quiz after question 10.
    private void finishQuiz() {
        addStackToVbox();
        db.insertUserScore(this.name, this.points);
    }

    // Method for adding Stack values to Front-end.
    private void addStackToVbox() {
        for (String timeStamp: timeStamps) {
            Text text = new Text(timeStamp);
            vbox.getChildren().add(text);
        }
    }

    // Accessing top ten users.
    public void generateReport() {
        openReportWindow(db.getTopTen());
    }

    // Configures pop up report window.
    private void openReportWindow(String results) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Results");
        window.setMinWidth(200);

        Label label = new Label(results);
        VBox layout = new VBox();
        layout.getChildren().add(label);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 250, 300);

        window.setScene(scene);
        window.showAndWait();
    }

}
