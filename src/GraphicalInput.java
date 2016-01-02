/**
 * Created by matt on 24/12/2015.
 */
public class GraphicalInput implements Input {
	private Board board;

	public GraphicalInput(Board board) {
		this.board = board;
	}

	public Combination getGuess() {
		return (getCombination("Please enter your guess", board.getNumberOfPegs(), Peg.getAvailablePegs()));
	}

	public Combination getCode() {
		return (getCombination("Please enter your code", board.getNumberOfPegs(), Peg.getAvailablePegs()));
	}

	public Combination getFeedback() {
		return (getCombination("Please enter your feedback", board.getNumberOfPegs(), Peg.getPeg("Empty"), Peg.getPeg("Black"), Peg.getPeg("White")));
	}

	private Combination getCombination(String title, int length, Peg... options) {
		GetCombinationWindow dialog = new GetCombinationWindow(title, options, length, 0, 0);
		return (dialog.getCombination());
	}
}