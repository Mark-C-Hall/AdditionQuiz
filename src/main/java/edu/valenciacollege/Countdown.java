package edu.valenciacollege;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Timer;
import java.util.TimerTask;

public class Countdown  {
    StringProperty timeProperty  = new SimpleStringProperty(this, "timeProperty", "5.00");

    public void restartClock() {
        Timer timer = new Timer();
        MyTimerTask task = new MyTimerTask(timer);

        timer.schedule(task, 0, 100);
    }

    private class MyTimerTask extends TimerTask {
        private final Timer timer;
        Double time;

        public MyTimerTask(Timer timer) {
            this.timer = timer;
            time = Double.valueOf(timeProperty.getValue());
        }

        @Override
        public void run() {
            time -= 0.1;
            if (time <= 0) {
                timer.cancel();
            }
            timeProperty.setValue(String.valueOf(time).substring(0, 3));
        }
    }
}

