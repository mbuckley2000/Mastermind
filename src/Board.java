import java.util.Arrays;
import java.util.Stack;

/**
 * The board class is used to hold the virtual board for Mastermind.
 * It is made up of a stack of Guess objects
 *
 * @author Matt Buckley
 * @since 13/12/15
 */

public class Board {
	private Stack<Guess> stack = new Stack<>();
	private int maxLength;
	private int numberOfPegs;
	private int numberOfColours;
	private Combination code;

	/**
	 * @param maxLength       Maximum length of the board. Maximum number of guesses that can be made at the code
	 * @param numberOfPegs    The width of the board. The number of pegs that can be used to make the code
	 * @param numberOfColours The number of possible colours that can be used to make the code
	 */
	public Board(int maxLength, int numberOfPegs, int numberOfColours) {
		this.maxLength = maxLength;
		this.numberOfPegs = numberOfPegs;
		this.numberOfColours = numberOfColours;
	}

	/**
	 * Adds a guess to the board
	 *
	 * @param guess Guess to be added
	 * @throws StackOverflowError Thrown if the board is full
	 */
	public void addGuess(Guess guess) throws StackOverflowError {
		if (!isFull()) {
			stack.add(guess);
		} else {
			throw (new StackOverflowError("Board is full"));
		}
	}

	public boolean isEmpty() {
		return (stack.isEmpty());
	}

	public boolean isFull() {
		return (stack.size() == maxLength);
	}

	public int getMaxLength() {
		return (maxLength);
	}

	/**
	 * Returns the number of guesses that have been made
	 *
	 * @return Number of guesses that have been made
	 */
	public int getCurrentLength() {
		return (stack.size());
	}

	/**
	 * Returns the most recent guess
	 *
	 * @return Most recent guess
	 */
	public Guess peek() {
		return (stack.peek());
	}

	/**
	 * Sets the feedback of a Guess in the board
	 *
	 * @param index    Index of the guess that you want to set the feedback of
	 * @param feedback Feedback you are setting
	 * @throws IndexOutOfBoundsException Thrown if the index is out of bounds (Guess you want to set the feedback on doesn't exist)
	 */
	public void setFeedback(int index, Combination feedback) throws IndexOutOfBoundsException {
		if (index < getCurrentLength()) {
			stack.get(index).setFeedback(feedback);
		} else {
			throw (new IndexOutOfBoundsException("Index out of bounds"));
		}
	}

	/**
	 * Returns the number of feedbacks previously given
	 *
	 * @return The number of feedbacks already given
	 */
	public int countFeedbacksGiven() {
		int i = 0;
		for (Guess p : stack) {
			if (p.getFeedback() != null) {
				i++;
			}
		}
		return (i);
	}

	/**
	 * Gets the guess at a given index
	 *
	 * @param index The index to get the guess at
	 * @return The guess at the given index
	 * @throws IndexOutOfBoundsException If the index is out of bounds
	 */
	public Guess getGuess(int index) throws IndexOutOfBoundsException {
		if (index < getCurrentLength()) {
			return (stack.get(index));
		} else {
			throw (new IndexOutOfBoundsException("Index out of bounds"));
		}
	}

	public int getNumberOfPegs() {
		return (numberOfPegs);
	}

	public int getNumberOfColours() {
		return (numberOfColours);
	}

	/**
	 * Returns the code set by the codeMaker
	 *
	 * @return The code set by the codeMaker at the start of the game. Returns null if the code hasn't been set
	 */
	public Combination getCode() {
		if (code != null) {
			return (code);
		} else {
			return (null);
		}
	}

	/**
	 * Sets the code. Set by the code maker
	 *
	 * @param code The code set by the code maker
	 */
	public void setCode(Combination code) {
		this.code = code;
	}

	/**
	 * Returns an array of all previous guesses
	 *
	 * @return An array of all previous guesses. Returns null if no guesses have been made yet
	 */
	public Guess[] getPreviousGuesses() {
		if (!isEmpty()) {
			Object[] objArray = stack.toArray();
			return (Arrays.copyOf(objArray, objArray.length, Guess[].class));
		} else {
			return (null);
		}
	}
}
