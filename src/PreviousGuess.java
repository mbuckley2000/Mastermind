/**
 * Created by matt on 24/12/2015.
 */
public class PreviousGuess {
	private Combination guess;
	private Combination feedback;

	public PreviousGuess(Combination guess) {
		this.guess = guess;
	}

	public Combination getGuess() {
		return (guess);
	}

	public Combination getFeedback() {
		return (feedback);
	}

	public void setFeedback(Combination feedback) {
		this.feedback = feedback;
	}
}