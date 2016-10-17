/**
 * Player interface used by the Mastermind game
 *
 * @author Matt Buckley
 * @since 02/01/2016
 */

public interface Player {
	/**
	 * @return The input interface used by the player
	 */
	Input getInput();

	/**
	 * @return The player type as a string (e.g, 'AI', 'Human')
	 */
	String getPlayerType();
}