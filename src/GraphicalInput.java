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
		Combination userFeedback = getCombination("Please enter your feedback", board.getNumberOfPegs(), Peg.getPeg("Empty"), Peg.getPeg("Black"), Peg.getPeg("White"));
		byte[] correctFeedback = AIInput.getFeedback(board.peek().getGuess().toIDArray(), board.getCode().toIDArray());
		while (userFeedback.countPegs(Peg.black) != correctFeedback[0] || userFeedback.countPegs(Peg.white) != correctFeedback[1]) {
			userFeedback = getCombination("Cheat! Please re-enter the CORRECT feedback", board.getNumberOfPegs(), Peg.getPeg("Empty"), Peg.getPeg("Black"), Peg.getPeg("White"));
		}
		return (userFeedback);
	}

	private Combination getCombination(String title, int length, Peg... options) {
		GetCombinationWindow dialog = new GetCombinationWindow(title, options, length, 0, 0);
		return (dialog.getCombination());
	}
}