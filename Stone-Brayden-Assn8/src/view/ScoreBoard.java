package view;

import javafx.application.Application;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Brayden on 11/22/2016.
 */
public class ScoreBoard extends HBox{
    Button startBtn = new Button("Start");
    public int timeVal = 0;
    Text timeText = new Text("Time: " + timeVal);
    Text minesText = new Text("100");
    Text minesLabel = new Text("Mines:");
    public Timer timer;

    public ScoreBoard()
    {
        this.getChildren().addAll(minesLabel, minesText, startBtn, timeText);
        this.setSpacing(50);
        this.setPadding(new Insets(10, 10, 10, 10));
    }

    /**
     * function used to start the timer on the scoreboard. Initializes the timeVal at 0 and creates
     * a new timer, scheduling it to increment timeVal every second.
     */
    public void startTimer()
    {

        timeVal = 0;
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeVal++;
                timeText.setText("Time: " + timeVal);
            }
        }, 0, 1000);
    }

    /**
     * function used to simply cancel the current timer
     */
    public void stopTimer()
    {
        timer.cancel();
    }
}
