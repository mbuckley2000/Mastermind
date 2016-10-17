/**
 * A human implementation of the Player interface
 *
 * @author Matt Buckley
 * @since 02/01/2016
 */
public class Human implements Player {
	private Input input;

	/**
	 * Constructs the Human given the board the game will be played on and whether or not the game is in graphical mode
	 *
	 * @param board     The board the game is played on
	 * @param graphical Whether or not the game is in graphical mode
	 */
	public Human(boolean graphical, Board board) {
		if (graphical) {
			input = new GraphicalInput(board);
		} else {
			input = new TextualInput(board);
		}
	}

	/**
	 * Gets the human's input object
	 *
	 * @return The input object
	 */
	public Input getInput() {
		return (input);
	}

	/**
	 * Gets the player type ("Human")
	 *
	 * @return "Human"
	 */
	public String getPlayerType() {
		return ("Human");
	}
}