/**
 * Created by matt on 24/12/2015.
 */
public class PreviousGuess {
	private Combination guess;
	private Combination feedback;

	public PreviousGuess(Combination guess) {
		this.guess = guess;
	}

	public void setFeedback(Combination feedback) {
		this.feedback = feedback;
	}

	public Combination getGuess() {
		return(guess);
	}

	public Combination getFeedback() {
		return(feedback);
	}

	public int blackPegCount() {
		if (feedback != null) {
			return (feedback.countPegs(Peg.getPeg("Black")));
		}
		return(0);
	}

	public int whitePegCount() {
		if (feedback != null) {
			return (feedback.countPegs(Peg.getPeg("White")));
		}
		return(0);
	}
}
