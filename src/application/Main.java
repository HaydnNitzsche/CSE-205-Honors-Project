package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            TicTacToeBoard board = new TicTacToeBoard();
            root.setCenter(board); // adds a game board to the stage
            Scene scene = new Scene(root, 300, 320);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Tic-Tac-Toe"); // adds a title to the window
            primaryStage.setResizable(false);
            primaryStage.setOnCloseRequest(new SaveStateHandler(board));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class SaveStateHandler implements EventHandler<WindowEvent> {
        TicTacToeBoard board;
        SaveStateHandler(TicTacToeBoard board) {
            super();
            this.board = board;
        }
        
        public void handle(WindowEvent event) {
            board.getGameStats().serialize();
        }
    }
}
