package view;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.NumberStringConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Minesweeperish is a game imitating the class Minesweeper game.
 *
 * Created by Brayden Stone on 11/23/2016.
 * Version 1.0
 */
public class Main extends Application {
    BorderPane mainPane = new BorderPane();
    ScoreBoard scoreBoard = new ScoreBoard();
    GameBoard gameBoard = new GameBoard();



    @Override
    public void start(Stage primaryStage) throws Exception {

        scoreBoard.setAlignment(Pos.CENTER);
        mainPane.setTop(scoreBoard);

        gameBoard.setPrefHeight(800);
        gameBoard.setPrefWidth(800);
        mainPane.setCenter(gameBoard);
        gameBoard.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainPane);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        for(Cell a : gameBoard.cells)
        {
            a.isArmed = false;
        }

        scoreBoard.startTimer();
        scoreBoard.stopTimer();

        Alert welcomeAlert = new Alert(Alert.AlertType.WARNING);
        welcomeAlert.setGraphic(null);
        welcomeAlert.setHeaderText(null);
        welcomeAlert.setTitle("Welcome!");
        welcomeAlert.setContentText("Welcome to Minesweeperish! Press the start button to begin.");
        welcomeAlert.showAndWait();

        scoreBoard.startBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Alert startAlert = new Alert(Alert.AlertType.WARNING);
                startAlert.setHeaderText(null);
                startAlert.setTitle("Start");
                startAlert.setGraphic(null);
                startAlert.setContentText("Prepare, hit OK to begin!");
                startAlert.showAndWait();

                gameBoard = new GameBoard();
                gameBoard.interBoard = scoreBoard;
                mainPane.setCenter(null);
                mainPane.setBottom(gameBoard);
                gameBoard.setAlignment(Pos.CENTER);

                scoreBoard.minesText.textProperty().bind(gameBoard.extNumBombs.asString());
                scoreBoard.startTimer();
                for (Cell a : gameBoard.cells) {
                    a.isArmed = true;
                }
            }
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                scoreBoard.stopTimer();
            }
        });

    }

}

