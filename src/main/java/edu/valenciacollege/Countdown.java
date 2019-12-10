package edu.valenciacollege;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/*
 * Class for implementing timer countdown. NOTE: While testing this class over several days, the program would sometimes
 * crash catastrophically, without any replicable factors. I don't know why this would happen and could not
 *  troubleshoot. At the time of submission, program would not crash, but honestly, I have no idea why.
 */
public class Countdown implements Runnable {
    // JavaFX Property.
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
        // Configure JavaFx property.
        double time = Double.parseDouble(timeProperty.getValue());

        // Checks if time is positve, and that exit condition is not met.
        while (time > 0 && !exit) {
            try {
                time -= 0.1;
                timeProperty.setValue(String.valueOf(time).substring(0, 3));  // Sets float to string and selects
                Thread.sleep(100);                                      // first three characters.
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

