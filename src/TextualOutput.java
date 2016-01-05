/**
 * A textual output for the mastermind game
 * Outputs to the Standard Output (System.out)
 *
 * @author mb2070
 * @since 02/01/2016
 */

public class TextualOutput implements Output {
	private Board board;

	public TextualOutput(Board board) {
		this.board = board;
	}

	/**
	 * Updates the text view of the board
	 */
	public void update() {
		clearOutput();
		drawLogo();
		printAvailablePegs();
		drawBoard();
	}

	public void print(String message) {
		System.out.print(message);
	}

	public void println(String message) {
		System.out.println(message);
	}

	/**
	 * Clears the console view using ASCII modifiers
	 */
	private void clearOutput() {
		print("\033[H\033[2J");
	}

	/**
	 * Prints the mastermind logo
	 */
	private void drawLogo() {
		println("MASTERMIND");
		println("");
	}

	/**
	 * Prints a list of available peg colours
	 */
	private void printAvailablePegs() {
		System.out.println("Available Peg Colours: " + new Combination(Peg.getAvailablePegs()).toString());
		println("");
	}

	/**
	 * Prints the board in its current state
	 */
	private void drawBoard() {
		if (!board.isEmpty()) {
			println("Board:");
			for (Guess row : board.getPreviousGuesses()) {
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
			printLines("", 3);
		}
	}

	/**
	 * Prints multiple lines of the same string
	 *
	 * @param message String to print
	 * @param n       Number of times to print the string
	 */
	private void printLines(String message, int n) {
		for (int i = 0; i < n; i++) {
			println(message);
		}
	}
}