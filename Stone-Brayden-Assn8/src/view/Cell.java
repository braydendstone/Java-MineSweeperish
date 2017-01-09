package view;

import javafx.scene.control.Button;

/**
 * Created by Brayden on 11/22/2016.
 */
public class Cell extends Button {
    public int numMinesSurrounding = 0;
    private boolean isMine;
    public boolean isArmed;
    public int iPos;
    public int jPos;
    public Cell(String type)
    {
        this.setPrefWidth(40);
        this.setPrefHeight(40);
        this.setMaxHeight(40);
        this.setMaxWidth(40);
        this.setMinHeight(40);
        this.setMinWidth(40);
        this.setText("");

        isArmed = true;

        if(type == "")
        {
            isMine = false;
        }

        if(type == "mine")
        {
            isMine = true;
            this.setId("bomb");
        }

        this.getStylesheets().add("resource/custom.css");
    }

    /**
     * function is used to return the isMine property
     * @return
     */
    public boolean getIsMine()
    {
        return isMine;
    }


}
