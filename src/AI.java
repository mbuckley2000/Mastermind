/**
 * An AI implementation of the Player interface
 *
 * @author Matt Buckley
 * @since 02/01/2016
 */

public class AI implements Player {
	private AIInput input;

	/**
	 * Constructs the AI given the board the game will be played on
	 *
	 * @param board The board the game is played on
	 */
	public AI(Board board) {
		input = new AIInput(board, 1);
	}

	/**
	 * Gets the AI's input
	 *
	 * @return The AI's input
	 */
	public Input getInput() {
		return (input);
	}

	/**
	 * Gets the player type as a string ("AI)
	 *
	 * @return "AI"
	 */
	public String getPlayerType() {
		return ("AI");
	}
}