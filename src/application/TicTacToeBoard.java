package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TicTacToeBoard extends BorderPane {
	private final int ROWS = 3, COLUMNS = 3;
	private int roundsPlayed, currentPlayer;
	private SubSquare[][] squares = new SubSquare[ROWS][COLUMNS];
	private Statistics gameStats;
	private PlayerPrompter playerPrompter;
	private GridPane board;

	public TicTacToeBoard() {
	    board = new GridPane();
		gameStats = Statistics.deserialize();
		currentPlayer = 1;
		playerPrompter = new PlayerPrompter(this);
		
		//fills the board with SubSquares
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				squares[i][j] = new SubSquare(this);
				
				// sets the position for the SubSquare
				squares[i][j].setXPosition(i);
				squares[i][j].setYposition(j);
				
				//adds the SubSquare to the main GridPane
				board.add(squares[i][j], i, j);
			}
		}
		this.setTop(playerPrompter);
		this.setCenter(board);
	}

	/**
	 * Clears the board and resets the board piece's values
	 */
	void clearBoard() {
		// parses through all elements in the board
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				squares[i][j].setText("");
				squares[i][j].setDisable(false);
			}
		}
	}
	
	/**
	 * returns the playerPrompter object
	 * @return the playerPrompter object
	 */
	public PlayerPrompter getPlayerPrompter() {
		return playerPrompter;
	}
	
	public Statistics getGameStats() {
	    return gameStats;
	}
	
	public int getCurrentPlayer() {
	    return currentPlayer;
	}
	
	public void incRoundsPlayed() {
	    roundsPlayed++;
	}
	
	public SubSquare[][] getSquares() {
	    return squares;
	}
	
	public int getRoundsPlayed() {
	    return roundsPlayed;
	}
	
	public int getRows() {
	    return ROWS;
	}
	
	public int getColumns() {
	    return COLUMNS;
	}
	
	public void resetRoundsPlayed() {
	    roundsPlayed = 0;
	}
	
	public void switchCurrentPlayer() {
	    currentPlayer*= -1;
	}
	
	public void disablePlay() {
	    for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                squares[i][j].setDisable(true);
            }
        }
	}
}
