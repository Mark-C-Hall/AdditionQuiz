package edu.valenciacollege;

import javafx.beans.property.SimpleStringProperty;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*
 * Class produces and Question and Answer state. First, text is read from file. Then, the two addition objects are
 * assigned as well as the four possible answers. The calculated answer is also done in class through generic methods.
 * The state loops through the 10 questions in random order.
 */
public class QuestionAndAnswerState {
    // JavaFX Properties
    SimpleStringProperty object1 = new SimpleStringProperty(this, "object1");
    SimpleStringProperty object2 = new SimpleStringProperty(this, "object2");
    SimpleStringProperty answer1 = new SimpleStringProperty(this, "answer1");
    SimpleStringProperty answer2 = new SimpleStringProperty(this, "answer2");
    SimpleStringProperty answer3 = new SimpleStringProperty(this, "answer3");
    SimpleStringProperty answer4 = new SimpleStringProperty(this, "answer4");

    // Class Properties
    private String correctAnswer;
    private int questionNumber = 0;
    private ArrayList<ArrayList<Object>> listOfQuestionsAndAnswers;
    private Scanner scanner;

    QuestionAndAnswerState() throws IOException {
        listOfQuestionsAndAnswers = new ArrayList<>();
        File file = getFileFromResource();
        scanner = new Scanner(file);
        listOfQuestionsAndAnswers = getAllQuestionsAndAnswers();
        setQuestionsAndAnswers(
                produceQuestionAndAnswerList(
                        listOfQuestionsAndAnswers));
    }

    // Load text file from Resource Folder.
    private File getFileFromResource() {
        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource("input.txt");
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }
    }

    /*
     * This method reads from text file and returns a list of lists. As the file is read, the two addition objects are
     * selected to the two first indexes of the sublist. Then the next four answer objects are randomly placed
     * in the 3rd-6th indexes. Finally, the correct answer is calculated and appended to the end of the sublist. The 10
     *  sublists are then added  and shuffled into the master list of lists.
     */
    private ArrayList<ArrayList<Object>> getAllQuestionsAndAnswers() {
        ArrayList<ArrayList<Object>> resultList = new ArrayList<>();

        // Loop through file lines two at a time
        for (int i = 0; i < 10; i++) {
            ArrayList<Object> tempList = new ArrayList<>();  // Temp list to hold the question objects.
            ArrayList<Object> answerList = new ArrayList<>();    // Temp list to hold the answer objects.

            // Question lines.
            tempList.add(scanNext());
            tempList.add(scanNext());
            // Answer lines.
            answerList.add(scanNext());
            answerList.add(scanNext());
            answerList.add(scanNext());
            answerList.add(scanNext());
            Collections.shuffle(answerList);

            // This list contains two questions and four answers
            tempList.addAll(answerList);

            // Append temp list with calculated answers
            tempList.add(calculateAnswer(tempList));
            resultList.add(tempList);

        }
        Collections.shuffle(resultList);
        return resultList;
    }

    // Method for ensuring data types remain constant.
    private Object scanNext() {
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else if (scanner.hasNextDouble()) {
            return scanner.nextDouble();
        } else {
            // Case handling for float values with "f" suffix
            if (scanner.hasNext(".*f")) {
                return Double.valueOf(scanner.next());
            } else {
                return scanner.next();
            }
        }
    }

    /*
     * Generic method for adding two objects. The tree of if/else statements is to see the class types of the incoming
     * and cast the generic objects to their data types, since string addition, and numerical addition need separte
     * methods.
     */
    private String calculateAnswer(ArrayList<Object> list) {
        if (list.get(0).getClass() == Integer.class && list.get(1).getClass() == Integer.class) {
            int obj1 = (Integer)list.get(0);
            int obj2 = (Integer)list.get(1);
            return calcInts(obj1, obj2);
        } else if (list.get(0).getClass() == Double.class || list.get(1).getClass() == Double.class) {
            if (list.get(0).getClass() == Double.class && list.get(1).getClass() == Double.class) {
                double obj1 = (Double)list.get(0);
                double obj2 = (Double)list.get(1);
                return calcDoubles(obj1, obj2);
            } else if(list.get(0).getClass() == Double.class && list.get(1).getClass() == Integer.class) {
                double obj1 = (Double)list.get(0);
                int obj2 = (Integer) list.get(1);
                return calcDoubles(obj1, obj2);
            } else {
                int obj1 = (Integer) list.get(1);
                double obj2 = (Double)list.get(0);
                return calcDoubles(obj1, obj2);
            }
        } else {
            return concatString(list.get(0), list.get(1));
        }
    }

    // Helper method for adding ints.
    private <T extends Number> String calcInts(T o, T o1) {
        int result = o.intValue() + o1.intValue();
        return Integer.toString(result);
    }

    // Helper method for adding doubles. If double and int, then int is cast to double.
    private <T extends Number> String calcDoubles(T o, T o1) {
        double result = o.doubleValue() + o1.doubleValue();
        return Double.toString(result);
    }

    // Helper method for adding Strings.
    private <T> String concatString(T o, T o1) {
        return o.toString().concat(o1.toString());
    }

    // Method for selecting the question state in list of questions.
    private ArrayList<Object> produceQuestionAndAnswerList(ArrayList<ArrayList<Object>> list)  {
        return list.get(questionNumber);
    }

    // Method for setting the JavaFX properties to question state.
    private void setQuestionsAndAnswers(ArrayList<Object> list) {
        this.object1.set(list.get(0).toString());
        this.object2.set(list.get(1).toString());
        this.answer1.set(list.get(2).toString());
        this.answer2.set(list.get(3).toString());
        this.answer3.set(list.get(4).toString());
        this.answer4.set(list.get(5).toString());

        this.correctAnswer = list.get(6).toString();
    }

    // New Question State.
    void newQuestion() {
        questionNumber += 1;
        setQuestionsAndAnswers(
                produceQuestionAndAnswerList(
                        listOfQuestionsAndAnswers));
    }

    // Getters
    public String getCorrectAnswer() {
        return this.correctAnswer;
    }
}
