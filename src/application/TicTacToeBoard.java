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

public class TicTacToeBoard extends GridPane {
	private final int ROWS = 3;
	private final int COLUMNS = 3;
	private final double SQUARE_SIZE = 100;
	private int roundsPlayed, currentPlayer;
	private SubSquare[][] squares = new SubSquare[ROWS][COLUMNS];
	private Statistics gameStats;

	public TicTacToeBoard() {
		gameStats = Statistics.deserialize();
		currentPlayer = 1;
		
		//fills the board with SubSquares
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				squares[i][j] = new SubSquare();
				
				// sets the position for the SubSquare
				squares[i][j].setXPosition(i);
				squares[i][j].setYposition(j);
				
				// sets the aesthetic characteristics of the square
				squares[i][j].setMinSize(SQUARE_SIZE, SQUARE_SIZE);
				squares[i][j].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
						CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				squares[i][j].setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 36));
				
				//adds the event handler
				squares[i][j].setOnAction(new PlayHandler());
				
				//adds the SubSquare to the main GridPane
				this.add(squares[i][j], i, j);
			}
		}
	}

	/**
	 * checks if a winning move has been placed on the board
	 * 
	 * @param x
	 * @param y
	 * @return true if a winning move has been made, false otherwise
	 */
	public boolean checkForEnd(int x, int y) {
		// checks for draw
		if (roundsPlayed == (ROWS * COLUMNS))
			return true;
		// checks for a horizontal win
		if (squares[0][y].getText().compareTo(squares[1][y].getText()) == 0
				&& squares[1][y].getText().compareTo(squares[2][y].getText()) == 0
				&& squares[0][y].getText().compareTo("") != 0)
			return true;
		// checks for a vertical win
		else if (squares[x][0].getText().compareTo(squares[x][1].getText()) == 0
				&& squares[x][1].getText().compareTo(squares[x][2].getText()) == 0
				&& squares[x][0].getText().compareTo("") != 0)
			return true;
		// checks for a diagonal win from the top left to bottom right
		else if (squares[0][0].getText().compareTo(squares[1][1].getText()) == 0
				&& squares[1][1].getText().compareTo(squares[2][2].getText()) == 0
				&& squares[0][0].getText().compareTo("") != 0)
			return true;
		// checks for a diagonal win from the top right to the bottom left
		else if (squares[0][2].getText().compareTo(squares[1][1].getText()) == 0
				&& squares[1][1].getText().compareTo(squares[2][0].getText()) == 0
				&& squares[0][2].getText().compareTo("") != 0)
			return true;
		return false;
	}

	/**
	 * Clears the board and resets the board piece's values
	 */
	private void clearBoard() {
		// parses through all elements in the board
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				squares[i][j].setText("");
				squares[i][j].setDisable(false);
			}
		}

	}

	/**
	 * Creates a new window that displays post game statistics
	 * 
	 * @param winString
	 */
	private void createWinDialogue(String winString) {
		// creates the label with information
		Label winLabel = new Label(winString + " time(s) in " + gameStats.getGamesPlayed() + " game(s) played!");

		// creates a button to close the window and reset the board
		Button closeButton = new Button("CLOSE");
		closeButton.setOnAction(new CloseWindowHandler());
		//centers the button in a pane
		BorderPane buttonLayout = new BorderPane();
		buttonLayout.setCenter(closeButton);

		//formats the button and label
		BorderPane winNotifierLayout = new BorderPane();
		winNotifierLayout.setCenter(winLabel);
		winNotifierLayout.setBottom(buttonLayout);
		
		//creates a new scene
		Scene dialogScene = new Scene(winNotifierLayout, 450, 50);
		
		//adds the scene to a stage
		Stage winNotifier = new Stage();
		winNotifier.setScene(dialogScene);
		winNotifier.setTitle("Game Complete");
		winNotifier.setOnCloseRequest(new WindowClosedHandler());
		winNotifier.show();
	}

	/**
	 * Processes a turn in the game
	 * 
	 * @author Haydn
	 */
	private class PlayHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			roundsPlayed++;
			SubSquare source = (SubSquare) event.getSource();

			// adds the play to the board
			if (currentPlayer == 1) {
				source.setStyle("-fx-text-fill: #ff0000");
				source.setText("X");
			} else {
				source.setStyle("-fx-text-fill: #0000ff");
				source.setText("O");
			}
			source.setDisable(true);

			// checks for a win or draw
			if (checkForEnd(source.getXPosition(), source.getYPosition())) {
				gameStats.incGamesPlayed();
				if (roundsPlayed == (ROWS * COLUMNS)) {
					gameStats.incDraws();
					createWinDialogue("It's a draw! The game has been drawn " + gameStats.getDraws());
				} else if (currentPlayer == 1) {
					gameStats.incXWins();
					createWinDialogue("X Won! X has won " + gameStats.getXWins());
				} else {
					gameStats.incOWins();
					createWinDialogue("O Won! O has won " + gameStats.getOWins());
				}
				roundsPlayed = 0;
				gameStats.serialize();
			}
			// swaps which player goes first
			currentPlayer *= -1;
		}
	}

	/**
	 * Clears the board and exits the statistics window
	 * 
	 * @author Haydn Nitzsche
	 */
	private class CloseWindowHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Button source = (Button) event.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
			clearBoard();
		}
	}

	/**
	 * Clears the board in case a user does not properly exit the statistics window
	 * 
	 * @author Haydn Nitzsche
	 */
	private class WindowClosedHandler implements EventHandler<WindowEvent> {
		public void handle(WindowEvent event) {
			clearBoard();
		}
	}
	
	/**
	 * returns the current player
	 * @return 1 if X, -1 if O
	 */
	public int getCurrentPlayer() {
		return currentPlayer;
	}
}
