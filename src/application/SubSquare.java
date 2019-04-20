package application;

import javafx.scene.control.Button;

public class SubSquare extends Button {
	private int xPosition;
	private int yPosition;
	
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
