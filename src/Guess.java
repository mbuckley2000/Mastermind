/**
 * Guess object for the Mastermind game. The Board class contains a stack of these
 * Consists of two combinations:
 * <ul>
 * <li>The actual guess. This is added at construction</li>
 * <li>The feedback on the guess. This is added at a later time, not at construction</li>
 * </ul>
 *
 * @author mb2070
 * @since 24/12/2015
 */
public class Guess {
	private Combination guess;
	private Combination feedback;

	/**
	 * Sets the guess variable using the given combination
	 *
	 * @param guess The guess Combination
	 */
	public Guess(Combination guess) {
		this.guess = guess;
	}


	public Combination getGuess() {
		return (guess);
	}

	public Combination getFeedback() {
		return (feedback);
	}

	/**
	 * Sets the feedback on the guess
	 * This is done after construction, because the feedback is entered by the Player after they have seen the guess
	 *
	 * @param feedback The player's feedback
	 */
	public void setFeedback(Combination feedback) {
		this.feedback = feedback;
	}
}