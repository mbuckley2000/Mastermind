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
		drawLogo();
		drawBoard();
	}

	public void print(String message) {
		System.out.print(message);
	}

	public void println(String message) {
		System.out.println(message);
	}

	private void clearOutput() {
		print("\033[H\033[2J");
	}

	private void drawLogo() {
		println("MASTERMIND");
		println("");
	}

	private void drawBoard() {
		if (!board.isEmpty()) {
			println("Board:");
			for (PreviousGuess row : board.getPreviousGuesses()) {
				Combination guess = row.getGuess();
				Combination feedback = row.getFeedback();
				if (guess != null) {
					print(guess.toString());
				}
				if (feedback != null) {
					print("\t\t\t");
					print(feedback.toString());
				}
				printLines("", 2);
			}
			printLines("", 10);
		}
	}

	private void printLines(String message, int n) {
		for (int i=0; i<n; i++) {
			println(message);
		}
	}
}
