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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SubSquare extends Button {
    private final double SQUARE_SIZE = 100;
	private int xPosition;
	private int yPosition;
	TicTacToeBoard owner;
	
	SubSquare (TicTacToeBoard owner) {
	    super();
	    
	    this.owner = owner;
	    
        // sets the aesthetic characteristics of the square
        this.setMinSize(SQUARE_SIZE, SQUARE_SIZE);
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 36));
        
        //adds the event handler
        this.setOnAction(new PlayHandler());
	}
	
	/**
     * Processes a turn in the game
     * 
     * @author Haydn
     */
    private class PlayHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            owner.incRoundsPlayed();
            SubSquare source = (SubSquare) event.getSource();

            // adds the play to the board
            if (owner.getCurrentPlayer() == 1) {
                source.setStyle("-fx-text-fill: #ff0000");
                source.setText("X");
            } else {
                source.setStyle("-fx-text-fill: #0000ff");
                source.setText("O");
            }
            source.setDisable(true);

            // checks for a win or draw
            if (checkForEnd(source.getXPosition(), source.getYPosition())) {
                owner.getGameStats().incGamesPlayed();
                if (owner.getRoundsPlayed() == (owner.getRows() * owner.getColumns())) {
                    owner.getGameStats().incDraws();
                    createWinDialogue("It's a draw! The game has been drawn " + owner.getGameStats().getDraws());
                } else if (owner.getCurrentPlayer() == 1) {
                    owner.getGameStats().incXWins();
                    createWinDialogue("X Won! X has won " + owner.getGameStats().getXWins());
                } else {
                    owner.getGameStats().incOWins();
                    createWinDialogue("O Won! O has won " + owner.getGameStats().getOWins());
                }
                owner.resetRoundsPlayed();
                //gameStats.serialize();
            }
            // swaps which player goes first
            owner.switchCurrentPlayer();
            owner.getPlayerPrompter().updateCurrentPlayer(owner.getCurrentPlayer());
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
        if (owner.getRoundsPlayed() == (owner.getRows() * owner.getColumns()))
            return true;
        // checks for a horizontal win
        if (owner.getSquares()[0][y].getText().compareTo(owner.getSquares()[1][y].getText()) == 0
                && owner.getSquares()[1][y].getText().compareTo(owner.getSquares()[2][y].getText()) == 0
                && owner.getSquares()[0][y].getText().compareTo("") != 0)
            return true;
        // checks for a vertical win
        else if (owner.getSquares()[x][0].getText().compareTo(owner.getSquares()[x][1].getText()) == 0
                && owner.getSquares()[x][1].getText().compareTo(owner.getSquares()[x][2].getText()) == 0
                && owner.getSquares()[x][0].getText().compareTo("") != 0)
            return true;
        // checks for a diagonal win from the top left to bottom right
        else if (owner.getSquares()[0][0].getText().compareTo(owner.getSquares()[1][1].getText()) == 0
                && owner.getSquares()[1][1].getText().compareTo(owner.getSquares()[2][2].getText()) == 0
                && owner.getSquares()[0][0].getText().compareTo("") != 0)
            return true;
        // checks for a diagonal win from the top right to the bottom left
        else if (owner.getSquares()[0][2].getText().compareTo(owner.getSquares()[1][1].getText()) == 0
                && owner.getSquares()[1][1].getText().compareTo(owner.getSquares()[2][0].getText()) == 0
                && owner.getSquares()[0][2].getText().compareTo("") != 0)
            return true;
        return false;
    }
    
    /**
     * Creates a new window that displays post game statistics
     * 
     * @param winString
     */
    private void createWinDialogue(String winString) {
        owner.disablePlay();
        
        // creates the label with information
        Label winLabel = new Label(winString + " time(s) in " + owner.getGameStats().getGamesPlayed() + " game(s) played!");

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
     * Clears the board and exits the statistics window
     * 
     * @author Haydn Nitzsche
     */
    private class CloseWindowHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            Button source = (Button) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            owner.clearBoard();
        }
    }

    /**
     * Clears the board in case a user does not properly exit the statistics window
     * 
     * @author Haydn Nitzsche
     */
    private class WindowClosedHandler implements EventHandler<WindowEvent> {
        public void handle(WindowEvent event) {
            owner.clearBoard();
        }
    }
    
	/**
	 * returns the x position of the SubSquare in the game board
	 * @return x position of the SubSquare in the game board
	 */
	public int getXPosition() {
		return xPosition;
	}
	
	/**
	 * returns the y position of the SubSquare in the game board
	 * @return y position of the SubSquare in the game board
	 */
	public int getYPosition() {
		return yPosition;
	}

	/**
	 * sets the x position of the SubSquare in the board
	 * @param xPosition
	 */
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * sets the y position of the SubSquare in the board
	 * @param yPosition
	 */
	public void setYposition(int yPosition) {
		this.yPosition = yPosition;
	}
}
