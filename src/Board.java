import java.io.Serializable;
import java.util.Stack;

/**
 * Created by matt on 13/12/2015.
 */
public class Board implements Serializable{
	private Stack<Combination> stack = new Stack<Combination>();
	private int length;

	public Board(int length) {
		this.length = length;
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

	public int getMaxLength() {
		return(length);
	}

	public int getCurrentLength() {
		return(stack.size());
	}

	public Combination peek() {
		if (!stack.isEmpty()) {
			return (stack.peek());
		} else {
			System.err.println("Board is empty");
			return(null);
		}
	}

	public Combination getCombination(int index) {
		if (index < stack.size()) {
			return (stack.get(index));
		} else {
			System.err.println("Board out of bounds. Combination doesn't exist");
			return(null);
		}
	}

	public void empty() {
		stack.empty();
	}
}
