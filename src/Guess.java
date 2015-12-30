/**
 * Created by matt on 24/12/2015.
 */
public class Guess {
	private Combination guess;
	private Combination feedback;

	public Guess(Combination guess, Combination feedback) {
		this.guess = guess;
		this.feedback = feedback;
	}

	public Combination getGuess() {
		return(guess);
	}

	public Combination getFeedback() {
		return(feedback);
	}
}
