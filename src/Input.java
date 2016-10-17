/**
 * User input interface for the Mastermind game
 *
 * @author Matt Buckley
 * @since 22/12/2015
 */

public interface Input {
	/**
	 * Get the guess from the player
	 *
	 * @return The guess
	 */
	Combination getGuess();

	/**
	 * Get the feedback from the player
	 *
	 * @return The feedback
	 */
	Combination getFeedback();

	/**
	 * Get the code from the player
	 *
	 * @return The code
	 */
	Combination getCode();
}