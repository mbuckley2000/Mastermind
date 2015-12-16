import java.util.Stack;

/**
 * Created by matt on 13/12/2015.
 */
public class Board {
	private Stack<Combination> stack = new Stack<Combination>();
	private int length;
	private Sprite woodTexture;

	public Board(int length) {
		if (length > 0) {
			this.length = length;
		} else {
			this.length = 10;
			System.err.println("Invalid board length specified. Using default (10)");
		}
		woodTexture = new Sprite("images/wood.jpg");
	}

	public void draw(Window window) {
		//window.getContentPane().add(woodTexture);
		//window.setVisible(true);
		for (int i = 0; i < stack.size(); i++) {
			window.getContentPane().add(stack.get(i));
			window.setVisible(true);
		}
	}

	public void add(Combination c) {
		if (stack.size() < length) {
			stack.add(c);
		} else {
			System.err.println("Board is full");
		}
	}

	public Combination peek() {
		return(stack.peek());
	}
}
