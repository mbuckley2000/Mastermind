import javax.swing.*;
import java.awt.*;

/**
 * A graphical implementation of the output interface for the Mastermind game
 * Contains a BoardWindow, a TextOutputWindow, and a GameControlWindow
 *
 * @author Matt Buckley
 * @since 02/01/2016
 */
public class GraphicalOutput implements Output {
	private BoardWindow boardWindow;
	private TextOutputWindow textOutputWindow;
	private GameControlsWindow gameControlsWindow;

	/**
	 * Constructs the output given the board the game is to be played on
	 *
	 * @param board The board the game will be played on
	 */
	public GraphicalOutput(Board board) {
		try {
			boardWindow = new BoardWindow(board, 50, 25);
			textOutputWindow = new TextOutputWindow(boardWindow.getWidth(), 200);
			gameControlsWindow = new GameControlsWindow();
		} catch (HeadlessException e) {
			System.err.println("Please use a terminal with X11 capability. Otherwise, use 'java Mastermind textual' to launch in non-graphical mode");
			System.exit(0);
		}
		stickFrame(textOutputWindow, boardWindow);
		stickFrame(gameControlsWindow, textOutputWindow);
	}

	/**
	 * Updates the board window, drawing any pegs that have yet to be drawn
	 */
	public void update() {
		boardWindow.update();
	}

	/**
	 * Prints a message to the TextOutputWindow
	 *
	 * @param message The message to print
	 */
	public void print(String message) {
		textOutputWindow.print(message);
		stickFrame(textOutputWindow, boardWindow);
		stickFrame(gameControlsWindow, textOutputWindow);
	}

	/**
	 * Prints a message to the TextOutputWindow followed by a new line character
	 *
	 * @param message The message to print
	 */
	public void println(String message) {
		textOutputWindow.println(message);
		stickFrame(textOutputWindow, boardWindow);
		stickFrame(gameControlsWindow, textOutputWindow);
	}

	/**
	 * Moves one frame to the right of another
	 */
	private void stickFrame(JFrame frameA, JFrame frameB) {
		frameA.setLocation(frameB.getX() + frameB.getWidth(), frameB.getY());
	}
}