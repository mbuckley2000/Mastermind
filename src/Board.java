import java.util.Stack;

/**
 * Created by matt on 13/12/2015.
 */
public class Board {
	private Stack<PreviousGuess> stack = new Stack<PreviousGuess>();
	private int length;

	public Board(int length) {
		this.length = length;
	}

	public void add(PreviousGuess c) {
		if (stack.size() < length) {
			stack.add(c);
		} else {
			System.err.println("Board is full");
		}
	}

	public boolean isFull() {
		return (stack.size() == length);
	}

	public int getMaxLength() {
		return (length);
	}

	public int getCurrentLength() {
		return (stack.size());
	}

	public PreviousGuess peek() {
		if (!stack.isEmpty()) {
			return (stack.peek());
		} else {
			System.err.println("Board is empty");
			return (null);
		}
	}

	public PreviousGuess getCombination(int index) {
		if (index < stack.size()) {
			return (stack.get(index));
		} else {
			System.err.println("Board out of bounds. Combination doesn't exist");
			return (null);
		}
	}

	public void empty() {
		stack.empty();
	}
}
