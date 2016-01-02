/**
 * Created by matt on 02/01/2016.
 */
public class Human implements Player {
	private Input input;

	public Human(boolean graphical, Board board) {
		if (graphical) {
			input = new GraphicalInput(board);
		} else {
			input = new TextualInput(board);
		}
	}

	public Input getInput() {
		return (input);
	}
}