/**
 * A graphical input for the Mastermind game.
 * Implements Input as an interface
 *
 * @author Matt Buckley
 * @since 24/12/2015
 */

public class GraphicalInput implements Input {
	private Board board;

	/**
	 * Constructs the graphicalInput object with a given board
	 *
	 * @param board The board that the game is being played on
	 */
	public GraphicalInput(Board board) {
		this.board = board;
	}

	public Combination getGuess() {
		return (getCombination("Please enter your guess", board.getNumberOfPegs(), Peg.getAvailablePegs()));
	}

	public Combination getCode() {
		return (getCombination("Please enter your code (Write this down and keep it secret!)", board.getNumberOfPegs(), Peg.getAvailablePegs()));
	}


	/**
	 * Gets feedback on the last guess from the user. Checks that this feedback is correct and that the user isn't cheating.
	 * Repeats this until the correct feedback is given.
	 *
	 * @return The feedback as a Combination object
	 */
	public Combination getFeedback() {
		Combination userFeedback = getCombination("Please enter your feedback", board.getNumberOfPegs(), Peg.getPeg("Empty"), Peg.getPeg("Black"), Peg.getPeg("White"));
		byte[] correctFeedback = AIInput.getFeedback(board.peek().getGuess().toIDArray(), board.getCode().toIDArray());
		while (userFeedback.countPegs(Peg.black) != correctFeedback[0] || userFeedback.countPegs(Peg.white) != correctFeedback[1]) {
			userFeedback = getCombination("Cheat! Please re-enter the CORRECT feedback", board.getNumberOfPegs(), Peg.getPeg("Empty"), Peg.getPeg("Black"), Peg.getPeg("White"));
		}
		return (userFeedback);
	}


	/**
	 * Gets a combination of pegs from the user using a GetCombinationWindow object
	 *
	 * @param title   The message to be displayed in and the title of the GetCombinationWindow
	 * @param length  The length of the desired combination
	 * @param options A variable number of Pegs that the user has to choose from
	 * @return Combination of Pegs
	 */
	private Combination getCombination(String title, int length, Peg... options) {
		GetCombinationWindow dialog = new GetCombinationWindow(title, options, length, 600, 200);
		while (dialog.getCombination() == null) {
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				Thread.currentThread().interrupt();
			}
		}
		Combination combination = dialog.getCombination();
		dialog.dispose();
		return (combination);
	}
}