/**
 * Created by matt on 02/01/2016.
 */
public class TextualOutput implements Output {
	Board board;

	public TextualOutput(Board board) {
		this.board = board;
	}

	public void update() {
		clearOutput();
		drawBoard();
	}

	public void print(String message) {
		System.out.print(message);
	}

	public void println(String message) {
		System.out.println(message);
	}

	private void clearOutput() {
		try {
			Runtime.getRuntime().exec("clear");
		} catch (Exception e) {

		}
	}

	private void drawBoard() {
		for (PreviousGuess row : board.getPreviousGuesses()) {
			Combination guess = row.getGuess();
			Combination feedback = row.getFeedback();
			if (guess != null) {
				print(guess.toString());
			}
			if (feedback != null) {
				print("     --     ");
				print(feedback.toString());
			}
			println("");
		}
	}
}
