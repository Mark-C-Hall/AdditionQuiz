package edu.valenciacollege;

import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class QuestionAndAnswerState {
    SimpleStringProperty object1 = new SimpleStringProperty(this, "object1");
    SimpleStringProperty object2 = new SimpleStringProperty(this, "object2");
    SimpleStringProperty answer1 = new SimpleStringProperty(this, "answer1");
    SimpleStringProperty answer2 = new SimpleStringProperty(this, "answer2");
    SimpleStringProperty answer3 = new SimpleStringProperty(this, "answer3");
    SimpleStringProperty answer4 = new SimpleStringProperty(this, "answer4");
    private int questionNumber = 0;
    private  ArrayList<ArrayList<String>> listOfQuestionsAndAnswers;

    QuestionAndAnswerState() throws IOException {
        listOfQuestionsAndAnswers = new ArrayList<>();
        listOfQuestionsAndAnswers = getAllQuestionsAndAnswers();
        setQuestionsAndAnswers(produceQuestionAndAnswerList(listOfQuestionsAndAnswers));
    }

    /*
     * This method reads from a file and returns a list of lists. As the file is read, the two addition objects are
     * randomly selected to the two first indexes of the sublist. Then the next four answer objects are randomly placed
     * in the 3rd-6th indexes. The 10 sublists are then created and shuffled into the master list of lists.
     */
    private ArrayList<ArrayList<String>> getAllQuestionsAndAnswers() throws IOException {
        File file = new File("C:\\Users\\Mark\\Documents\\Valencia\\2019-2020\\Fall-2019\\AdvJava\\AdditionQuiz\\src\\main\\resources\\input.txt");
        Scanner scanner = new Scanner(file);
        ArrayList<ArrayList<String>> resultList = new ArrayList<>();

        // Loop through file lines two at a time
        for (int i = 0; i < 10; i++) {
            ArrayList<String> tempList = new ArrayList<>();  // Temp list to hold the question objects.
            ArrayList<String> answerList = new ArrayList<>();    // Temp list to hold the answer objects.

            // Question lines.
            tempList.add(scanner.next());
            tempList.add(scanner.next());
            Collections.shuffle(tempList);
            // Answer lines.
            answerList.add(scanner.next());
            answerList.add(scanner.next());
            answerList.add(scanner.next());
            answerList.add(scanner.next());
            Collections.shuffle(answerList);

            // This list contains two questions and four answers
            tempList.addAll(answerList);
            resultList.add(tempList);
        }
        Collections.shuffle(resultList);
        return resultList;
    }

    private ArrayList<String> produceQuestionAndAnswerList(ArrayList<ArrayList<String>> list)  {
        return list.get(questionNumber);
    }

    private void setQuestionsAndAnswers(ArrayList<String> list) {
        this.object1.set(list.get(0));
        this.object2.set(list.get(1));
        this.answer1.set(list.get(2));
        this.answer2.set(list.get(3));
        this.answer3.set(list.get(4));
        this.answer4.set(list.get(5));
    }

    private void newQuestion() {
        questionNumber += 1;
        setQuestionsAndAnswers(produceQuestionAndAnswerList(listOfQuestionsAndAnswers));
    }
}
