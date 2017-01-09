package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Brayden on 11/28/2016.
 */
public class GameBoard extends FlowPane {

    public final int NUM_OF_BOMBS = 100;
    int numOfBombs = NUM_OF_BOMBS;
    int numCellsClicked = 0;
    public IntegerProperty extNumBombs;
    Cell array[][] = new Cell[20][20];
    ArrayList<Cell> cells = new ArrayList<>();
    public ScoreBoard interBoard;

    public GameBoard()
    {
        extNumBombs = new SimpleIntegerProperty(NUM_OF_BOMBS);
        for (int i = 0; i < 400; i++) {
            Cell cellTmp;
            if (numOfBombs > 0) {
                cellTmp = new Cell("mine");
                numOfBombs--;
            } else {
                cellTmp = new Cell("");
            }
            setCellHandler(cellTmp);
            cells.add(cellTmp);
        }



        Collections.shuffle(cells);
        int index = 0;

        for(int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                array[i][j] = cells.get(index);
                cells.get(index).iPos = i;
                cells.get(index).jPos = j;
                index++;
            }
        }

        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++)
            {
                if(array[i][j].getIsMine())
                {
                    try {
                        array[i][j - 1].numMinesSurrounding++;
                    }
                    catch(IndexOutOfBoundsException e)
                    {}
                    try {
                        array[i][j + 1].numMinesSurrounding++;
                    }
                    catch(IndexOutOfBoundsException e)
                    {}
                    try {
                        array[i - 1][j - 1].numMinesSurrounding++;
                    }
                    catch(IndexOutOfBoundsException e)
                    {}
                    try {
                        array[i - 1][j + 1].numMinesSurrounding++;
                    }
                    catch(IndexOutOfBoundsException e)
                    {}
                    try {
                        array[i - 1][j].numMinesSurrounding++;
                    }
                    catch(IndexOutOfBoundsException e)
                    {}
                    try {
                        array[i + 1][j + 1].numMinesSurrounding++;
                    }
                    catch(IndexOutOfBoundsException e)
                    {}
                    try {
                        array[i + 1][j - 1].numMinesSurrounding++;

                    }
                    catch(IndexOutOfBoundsException e)
                    {}
                    try {
                        array[i + 1][j].numMinesSurrounding++;
                    }
                    catch(IndexOutOfBoundsException e)
                    {}
                }
            }
        }

        for(Cell[] a : array)
        {
            for(Cell b : a)
            {
                this.getChildren().addAll(b);
            }
        }
    }


    /**
     * setCellHandler defines the basic function of a cell button, including right
     * and left clicks, and winning/losing the game
     *
     * @param cell receives the cell object on which to set the handler
     */
    public void setCellHandler(Cell cell) {
        cell.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(cell.isArmed == true || cell.getText().equals("x") || cell.getText().equals("?")) {
                    if (event.getButton().equals(MouseButton.SECONDARY)) {
                        if (cell.getText().equals("")) {
                            cell.setText("x");
                            cell.setGraphic(new ImageView("resource/flag.png"));
                            cell.isArmed = false;
                            decrementBombs();
                        } else if (cell.getText().equals("x")) {
                            cell.setGraphic(null);
                            cell.setText("?");
                        } else if (cell.getText().equals("?")) {
                            cell.setText("");
                            cell.isArmed = true;
                            incrementBombs();
                        }
                    }
                }
            }
        });
        cell.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(cell.isArmed == true) {
                    if(cell.getIsMine() == false)
                    {
                        if(cell.numMinesSurrounding != 0) {
                            cell.setText(String.valueOf(cell.numMinesSurrounding));
                        }
                        numCellsClicked++;
                        if(numCellsClicked == 400 - NUM_OF_BOMBS)
                        {
                            endGame("win");
                            interBoard.stopTimer();
                            Alert youWin = new Alert(Alert.AlertType.INFORMATION);
                            youWin.setContentText("Winner! It took you " + interBoard.timeVal + " seconds");
                            youWin.setHeaderText(null);
                            youWin.setTitle("Winner!");
                            youWin.setGraphic(null);
                            youWin.showAndWait();
                        }
                        cell.isArmed = false;
                        cell.setDisable(true);
                        if(cell.numMinesSurrounding == 0) {
                            try {
                                if (array[cell.iPos][cell.jPos - 1].getIsMine() == false) {
                                    array[cell.iPos][cell.jPos - 1].fire();
                                }                            }
                            catch(IndexOutOfBoundsException e)
                            {}
                            try {
                                if (array[cell.iPos][cell.jPos + 1].getIsMine() == false) {
                                    array[cell.iPos][cell.jPos + 1].fire();
                                }                            }
                            catch(IndexOutOfBoundsException e)
                            {}
                            try {
                                if (array[cell.iPos - 1][cell.jPos - 1].getIsMine() == false) {
                                    array[cell.iPos - 1][cell.jPos - 1].fire();
                                }                            }
                            catch(IndexOutOfBoundsException e)
                            {}
                            try {
                                if (array[cell.iPos - 1][cell.jPos + 1].getIsMine() == false) {
                                    array[cell.iPos - 1][cell.jPos + 1].fire();
                                }                            }
                            catch(IndexOutOfBoundsException e)
                            {}
                            try {
                                if (array[cell.iPos - 1][cell.jPos].getIsMine() == false) {
                                    array[cell.iPos - 1][cell.jPos].fire();
                                }                            }
                            catch(IndexOutOfBoundsException e)
                            {}
                            try {
                                if (array[cell.iPos + 1][cell.jPos + 1].getIsMine() == false) {
                                    array[cell.iPos + 1][cell.jPos + 1].fire();
                                }                            }
                            catch(IndexOutOfBoundsException e)
                            {}
                            try {
                                if (array[cell.iPos + 1][cell.jPos - 1].getIsMine() == false) {
                                    array[cell.iPos + 1][cell.jPos - 1].fire();
                                }
                            }
                            catch(IndexOutOfBoundsException e)
                            {}
                            try {
                                if (array[cell.iPos + 1][cell.jPos].getIsMine() == false) {
                                    array[cell.iPos + 1][cell.jPos].fire();
                                }                            }
                            catch(IndexOutOfBoundsException e)
                            {}
                        }
                    }
                    else
                    {
                        endGame("lose");

                        interBoard.stopTimer();
                        Alert youLose = new Alert(Alert.AlertType.INFORMATION);
                        youLose.setContentText("Loser! It took you " + interBoard.timeVal + " seconds");
                        youLose.setHeaderText(null);
                        youLose.setTitle("Loser");
                        youLose.setGraphic(null);
                        youLose.showAndWait();
                    }
                }
            }
        });
    }

    /**
     *
     * function allowing the game to end, disarming all buttons and assigning correct colors
     * and images to each cell while leaving the necessary cells unchanged.
     *
     * @param string receives either "lose" or "win" to decide what kind of
     *               ending is needed, mostly in respect to setting colors of buttons
     *               where bombs are located (green for win, more complex system for losing)
     */
    private void endGame(String string)
    {
        if(string == "lose") {
            for (Cell a : cells) {
                a.isArmed = false;
                if (a.getIsMine() == true) {
                    a.setDisable(true);
                }
                if (a.getIsMine() == true && (a.getText() == "x" || a.getText() == "?")) {
                    a.setId("marked");
                }
                if (a.getIsMine() == false && (a.getText() == "x" || a.getText() == "?")) {
                    a.setId("falseMarked");
                }
                if (a.getIsMine() == true) {
                    a.setGraphic(new ImageView("resource/bomb.png"));
                }
            }
        }
        if(string == "win")
        {
            for(Cell a : cells)
            {
                if (a.getIsMine() == true) {
                    a.setGraphic(new ImageView("resource/bomb.png"));
                }
                if (a.getIsMine() == true) {
                    a.setId("marked");
                }
                a.isArmed = false;
                if (a.getIsMine() == true) {
                    a.setDisable(true);
                }
            }
        }
    }

    /**
     * function simply decrements the value held in the extNumBombs integer property
     */
    public void decrementBombs()
    {
        extNumBombs.setValue(extNumBombs.getValue() - 1);
    }
    /**
     * function simply increments the value held in the extNumBombs integer property
     */
    public void incrementBombs()
    {
        extNumBombs.setValue(extNumBombs.getValue() + 1);
    }

}
