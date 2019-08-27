package application;

import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SubSquare extends Button {
    private final double SQUARE_SIZE = 100;
    private int xPosition;
    private int yPosition;

    public SubSquare() {
        super();
        // sets the aesthetic characteristics of the square
        this.setMinSize(SQUARE_SIZE, SQUARE_SIZE);
        this.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 36));
    }

    /**
     * Sets the contents of the button based on which player played
     * 
     * @param player
     */
    public void setContents(int currentPlayer) {
        if (currentPlayer == 1) {
            this.setStyle("-fx-text-fill: #ff0000");
            this.setText("X");
        } else {
            this.setStyle("-fx-text-fill: #0000ff");
            this.setText("O");
        }
    }

    /**
     * returns the x position of the SubSquare in the game board
     * 
     * @return x position of the SubSquare in the game board
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * returns the y position of the SubSquare in the game board
     * 
     * @return y position of the SubSquare in the game board
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * sets the x position of the SubSquare in the board
     * 
     * @param xPosition
     */
    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * sets the y position of the SubSquare in the board
     * 
     * @param yPosition
     */
    public void setYposition(int yPosition) {
        this.yPosition = yPosition;
    }
}
