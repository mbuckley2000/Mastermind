/**
 * Created by matt on 02/01/2016.
 */
public class GraphicalOutput implements Output {
	private BoardWindow boardWindow;
	private TextOutputWindow textOutputWindow;

	public GraphicalOutput(Board board) {
		boardWindow = new BoardWindow(board, 50, 25);
		textOutputWindow = new TextOutputWindow(boardWindow.getWidth(), 200);
	}

	public void update() {
		boardWindow.update();
	}

	public void print(String message) {
		textOutputWindow.print(message);
		repositionTextWindow();
	}

	public void println(String message) {
		textOutputWindow.println(message);
		repositionTextWindow();
	}

	private void repositionTextWindow() {
		textOutputWindow.setLocation(boardWindow.getX() + boardWindow.getWidth(), boardWindow.getY());
	}
}