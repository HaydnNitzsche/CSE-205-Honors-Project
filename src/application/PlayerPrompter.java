package application;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class PlayerPrompter extends BorderPane{
	private Label currentPlayerLabel;
	
	PlayerPrompter() {
		currentPlayerLabel = new Label();
		currentPlayerLabel.setText("It is X's turn to go");
		this.setCenter(currentPlayerLabel);
	}
	
	public void updateCurrentPlayer(int currentPlayer) {
		String currentPlayerName = (currentPlayer == 1) ? "X" : "O";
		currentPlayerLabel.setText("It is " + currentPlayerName + "'s turn to go");
	}
}
