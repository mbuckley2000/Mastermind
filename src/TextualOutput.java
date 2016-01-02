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

	}

	private void drawBoard() {

	}
}
