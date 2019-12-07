package edu.valenciacollege;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;


public class Countdown implements Runnable {
    // TimeProperty binds the numerical value to the visual text.
    StringProperty timeProperty = new SimpleStringProperty(this, "timeProperty", "5.0");
    // Flag for exiting Thread.
    private volatile boolean exit;

    Countdown() {
        this.exit = false;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        this.runTimer();
    }

    public void stop() {
        this.exit = true;
    }

    // Deacreases time value every 1/10th of a second.
    private void runTimer() {
        double time = Double.parseDouble(timeProperty.getValue());

        while (time > 0 && !exit) {
            try {
                time -= 0.1;
                timeProperty.setValue(String.valueOf(time).substring(0, 3));  // Sets float to string and selects
                Thread.sleep(100);                                      // first for characters.
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

