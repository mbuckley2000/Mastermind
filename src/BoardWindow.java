import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

/**
 * A JFrame window that displays the board of a Mastermind game
 *
 * @author mb2070
 * @since 02/01/2016
 */
public class BoardWindow extends JFrame {
	private Set<Combination> drawnGuesses;
	private Set<Combination> drawnFeedbacks;
	private Board board;
	private int gridSpacing;
	private int pegSize;

	/**
	 * Constructs and displays the board window with no pegs
	 *
	 * @param board       The board to display
	 * @param gridSpacing The distance between each peg hole on the board in pixels
	 * @param pegSize     The size of each peg on the board in pixels
	 */
	public BoardWindow(Board board, int gridSpacing, int pegSize) {
		super("Mastermind - Board");
		this.board = board;
		this.gridSpacing = gridSpacing;
		this.pegSize = pegSize;
		setSize(board.getNumberOfPegs() * 100 + 150, board.getMaxLength() * 50 + 100);
		setResizable(false);
		getLayeredPane().add(new Sprite("images/wood.jpg", this), new Integer(0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawEmptyHoles();
		setVisible(true);
		drawnGuesses = new HashSet();
		drawnFeedbacks = new HashSet();
	}

	/**
	 * Draws any previously undrawn pegs onto the board
	 */
	public void update() {
		Combination guess;
		Combination feedback;
		for (int c = 0; c < board.getCurrentLength(); c++) {
			guess = board.getGuess(c).getGuess();
			feedback = board.getGuess(c).getFeedback();
			if (guess != null && !drawnGuesses.contains(guess)) {
				//Draw combination
				for (int p = 0; p < guess.getPegs().length; p++) {
					Peg peg = guess.getPegs()[p];
					if (peg != null) {
						drawPeg(new Sprite(peg.getSpriteFilePath(), this), (p + 1) * gridSpacing, (c + 1) * gridSpacing, 2);
					}
				}
				drawnGuesses.add(guess);
			}
			if (feedback != null && !drawnFeedbacks.contains(feedback)) {
				for (int p = 0; p < feedback.getPegs().length; p++) {
					Peg peg = feedback.getPegs()[p];
					if (peg != null) {
						drawPeg(new Sprite(peg.getSpriteFilePath(), this), (p + board.getNumberOfPegs() + 2) * gridSpacing, (c + 1) * gridSpacing, 2);
					}
				}
				drawnFeedbacks.add(feedback);
			}
		}
	}

	/**
	 * Draws a peg sprite onto the JLayeredPane of the window at the specified position and z-depth
	 *
	 * @param sprite The sprite to draw
	 * @param xPos   The X component of the position in pixels
	 * @param yPos   The Y component of the position in pixels
	 * @param zDepth The Z depth of the peg in the JLayeredPane
	 */
	private void drawPeg(Sprite sprite, int xPos, int yPos, int zDepth) {
		sprite.setPosition(xPos + (gridSpacing - pegSize) / 2, yPos + (gridSpacing - pegSize) / 2);
		sprite.setSize(pegSize, pegSize);
		getLayeredPane().add(sprite, new Integer(zDepth));
		getLayeredPane().validate();
		getLayeredPane().repaint();
	}

	/**
	 * Draws all empty peg holes onto the board
	 */
	private void drawEmptyHoles() {
		for (int x = 0; x < board.getNumberOfPegs(); x++) {
			for (int y = 0; y < board.getMaxLength(); y++) {
				drawPeg(new Sprite("images/empty.png", this), (x + 1) * gridSpacing, (y + 1) * gridSpacing, new Integer(1));
				drawPeg(new Sprite("images/empty.png", this), (x + board.getNumberOfPegs() + 2) * gridSpacing, (y + 1) * gridSpacing, new Integer(1));
			}
		}
	}
}