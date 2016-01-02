/**
 * Created by matt on 02/01/2016.
 */
public class AI implements Player {
	private AIInput input;

	public AI(Board board) {
		input = new AIInput(board);
	}

	public Input getInput() {
		return (input);
	}
}
