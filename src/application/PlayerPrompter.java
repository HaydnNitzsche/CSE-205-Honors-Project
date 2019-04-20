package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class PlayerPrompter extends BorderPane{
	private Label currentPlayerLabel;
	private Button clearStats;
	private TicTacToeBoard board;
	
	PlayerPrompter(TicTacToeBoard board) {
        this.board = board;
        clearStats = new Button("Clear Stats");
        clearStats.setOnAction(new ClearStatsHandler());
		currentPlayerLabel = new Label();
        updateCurrentPlayer(board.getCurrentPlayer());
		this.setCenter(currentPlayerLabel);
		this.setLeft(clearStats);
	}
	
	public void updateCurrentPlayer(int currentPlayer) {
	    //currentPlayer*=-1;
		String currentPlayerName = (currentPlayer == 1) ? "X" : "O";
		currentPlayerLabel.setText("It is " + currentPlayerName + "'s turn to go");
	}
	
	private class ClearStatsHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            board.getGameStats().resetStatistics();
        }
	}
	
}
