package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TicTacToeBoard extends BorderPane {
    private final int ROWS = 3, COLUMNS = 3;
    private int roundsPlayed, currentPlayer;
    private SubSquare[][] squares = new SubSquare[ROWS][COLUMNS];
    private Statistics gameStats;
    private GridPane board;
    private Label currentPlayerLabel;
    private Button clearStats;
    private HBox topLayout;

    public TicTacToeBoard(Statistics data) {
        board = new GridPane();
        gameStats = data;
        currentPlayer = 1;

        // fills the board with SubSquares
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                SubSquare newSquare = new SubSquare();

                newSquare.setOnAction(new PlayHandler());

                // sets the position for the SubSquare
                newSquare.setXPosition(i);
                newSquare.setYposition(j);

                // adds the SubSquare to the board
                squares[i][j] = newSquare;
                board.add(newSquare, i, j);
            }
        }
        clearStats = new Button("Clear Stats");
        clearStats.setOnAction(new ClearStatsHandler());
        currentPlayerLabel = new Label();
        updateCurrentPlayer(currentPlayer);
        topLayout = new HBox(20);
        topLayout.getChildren().addAll(clearStats, currentPlayerLabel);
        this.setTop(topLayout);
        this.setCenter(board);
    }

    /**
     * checks if a winning move has been placed on the board
     * 
     * @param x
     * @param y
     * @return true if a winning move has been made, false otherwise
     */
    private boolean checkForWin(int x, int y) {
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
     * checks for a drawn state
     * 
     * @return true if drawn, false otherwise
     */
    private boolean checkForDraw() {
        if (roundsPlayed == (ROWS * COLUMNS))
            return true;
        else
            return false;
    }

    /**
     * Clears the board and resets the board piece's values
     */
    private void clearBoard() {
        // parses through all elements in the board
        for (SubSquare[] i : squares) {
            for (SubSquare j : i) {
                j.setText("");
                j.setDisable(false);
            }
        }
        roundsPlayed = 0;
    }

    /**
     * disables the game board
     */
    private void disablePlay() {
        for (SubSquare[] i : squares) {
            for (SubSquare j : i) {
                j.setDisable(true);
            }
        }
    }

    /**
     * Creates a new window that displays post game statistics
     * 
     * @param winString
     */
    private void createWinDialogue(String winString) {
        disablePlay();

        // creates the label with information
        Label winLabel = new Label(winString + " time(s) in " + gameStats.getGamesPlayed() + " game(s) played!");

        // creates a button to close the window and reset the board
        Button closeButton = new Button("CLOSE");
        closeButton.setOnAction(new CloseWindowHandler());
        // centers the button in a pane
        BorderPane buttonLayout = new BorderPane();
        buttonLayout.setCenter(closeButton);

        // formats the button and label
        BorderPane winNotifierLayout = new BorderPane();
        winNotifierLayout.setCenter(winLabel);
        winNotifierLayout.setBottom(buttonLayout);

        // creates a new scene
        Scene dialogScene = new Scene(winNotifierLayout, 450, 50);

        // adds the scene to a stage
        Stage winNotifier = new Stage();
        winNotifier.setScene(dialogScene);
        winNotifier.setTitle("Game Complete");
        winNotifier.setOnCloseRequest(new WindowClosedHandler());
        winNotifier.show();
    }

    /**
     * 
     * @param currentPlayer
     */
    public void updateCurrentPlayer(int currentPlayer) {
        String currentPlayerName = (currentPlayer == 1) ? "X" : "O";
        currentPlayerLabel.setText("It is " + currentPlayerName + "'s turn to play");
        currentPlayer *= -1;
    }

    /**
     * clears the stats
     * 
     * @author Haydn
     *
     */
    private class ClearStatsHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            gameStats = new Statistics();
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
     * Processes a turn in the game
     * 
     * @author Haydn
     */
    private class PlayHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            roundsPlayed++;
            SubSquare source = (SubSquare) event.getSource();

            // adds the play to the board
            source.setContents(currentPlayer);
            source.setDisable(true);

            // checks for a win or draw
            if (checkForWin(source.getXPosition(), source.getYPosition()) || checkForDraw()) {
                gameStats.incGamesPlayed();
                if (checkForDraw()) {
                    gameStats.incDraws();
                    createWinDialogue("It's a draw! The game has been drawn " + gameStats.getDraws());
                } else if (currentPlayer == 1) {
                    gameStats.incXWins();
                    createWinDialogue("X Won! X has won " + gameStats.getXWins());
                } else {
                    gameStats.incOWins();
                    createWinDialogue("O Won! O has won " + gameStats.getOWins());
                }
            }
            currentPlayer *= -1;
            updateCurrentPlayer(currentPlayer);
        }
    }

}
