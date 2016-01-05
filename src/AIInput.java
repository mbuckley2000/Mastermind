/**
 * An AI driven implementation of the Input interface for the Mastermind game.
 * For clarification: this class takes no input from the human user, it implements the Input interface because the Game class sees it the same way it sees a human player
 *
 * @author mb2070
 * @since 23/12/2015
 */

import java.util.*;

public class AIInput implements Input {
	private Set<byte[]> possibleCombinations;
	private Board board;
	private Set<Combination> feedbackUsedForRemoval;
	private double intelligence;

	/**
	 * Constructs the AI given the board that the game is played on
	 * Fills the set of all possible combinations with every possible combination
	 *
	 * @param board        The board the game is played on
	 * @param intelligence AI intelligence, 0 being the least intelligent and 1 being the most intelligent
	 */
	public AIInput(Board board, double intelligence) {
		this.board = board;
		possibleCombinations = new HashSet();
		feedbackUsedForRemoval = new HashSet();
		fillPossibleCombinations(new byte[board.getNumberOfPegs()], board.getNumberOfColours(), board.getNumberOfPegs());
		this.intelligence = constrainDouble(intelligence, 0, 1);
	}

	/**
	 * Generates the feedback that would be given between two combinations (e.g. a guess and the code)
	 * The feedback is 1D array of bytes with 2 cells.
	 * The first cell contains the number of black pegs that would be given
	 * The second cell contains the number of white pegs that would be given
	 * They are returned as a byte array instead of a combination to speed up AI calculations and free memory
	 *
	 * @param guess The guess to compare with the code
	 * @param code  The code to compare with the guess
	 * @return The byte array containing the number of black and white pegs in the feedback that would be given
	 */
	public static byte[] getFeedback(byte[] guess, byte[] code) {
		byte blackCounter = 0;
		byte whiteCounter = 0;
		Boolean[] ignore = new Boolean[code.length];

		for (int i = 0; i < guess.length; i++) {
			if (guess[i] == code[i]) {
				ignore[i] = true;
				blackCounter++;
			}
		}

		for (int i = 0; i < guess.length; i++) {
			for (int j = 0; j < code.length; j++) {
				if (guess[i] == code[j] && ignore[i] == null && ignore[j] == null) {
					ignore[j] = true;
					whiteCounter++;
					break;
				}
			}
		}

		return (new byte[]{blackCounter, whiteCounter});
	}

	/**
	 * Returns the feedback given by the AI to the codeMaker on their last guess
	 * Uses the byte array method getFeedback method for calculation
	 *
	 * @return The feedback as a combination
	 */
	public Combination getFeedback() {
		sleepRandom();
		byte[] feedback = getFeedback(board.peek().getGuess().toIDArray(), board.getCode().toIDArray());
		Combination feedbackCombination = new Combination(board.getNumberOfPegs());
		feedbackCombination.addPeg(Peg.black, feedback[0]);
		feedbackCombination.addPeg(Peg.white, feedback[1]);
		return (feedbackCombination);
	}

	/**
	 * Gets the AIs guess at the code.
	 * It guesses using the following algorithm:
	 * <ul>
	 * <li>Start with a set S of all possible combinations</li>
	 * <li>Make a random guess from S</li>
	 * <li>If the guess is wrong, iterate through all combinations C in S and remove all C that would NOT give the same feedback with the guess</li>
	 * <li>Repeat</li>
	 * </ul>
	 *
	 * @return The guess as a combination
	 */
	public Combination getGuess() {
		sleepRandom();
		removeImpossibleCombinations();
		try {
			return (new Combination(possibleCombinations.iterator().next()));
		} catch (NoSuchElementException e) {
			System.err.println("ERROR: AI has run out of possible combinations");
			System.exit(1);
		}
		return (null);
	}

	/**
	 * Gets the AI's randomly generated code
	 *
	 * @return The code as a combination
	 */
	public Combination getCode() {
		sleepRandom();
		return (randomCombination(board.getNumberOfPegs()));
	}

	/**
	 * Removes all impossible combinations from the set of all possible combinations using the following algorithm:
	 * <ul>
	 * <li>Start with a set S of all possible combinations</li>
	 * <li>Make a random guess from S</li>
	 * <li>If the guess is wrong, iterate through all combinations C in S and remove all C that would NOT give the same feedback with the guess</li>
	 * <li>Intelligence is the proportion of known impossible combinations that are removed from the set of possible combinations</li>
	 * <li>Repeat</li>
	 * </ul>
	 */
	private void removeImpossibleCombinations() {
		if (!board.isEmpty()) {
			for (Guess p : board.getPreviousGuesses()) {
				if (!feedbackUsedForRemoval.contains(p.getFeedback())) {
					byte[] guess = p.getGuess().toIDArray();
					Combination feedback = p.getFeedback();
					if (feedback != null) {
						Stack<byte[]> impossibleCombinations = new Stack();
						byte blackPegs = (byte) feedback.countPegs(Peg.black);
						byte whitePegs = (byte) feedback.countPegs(Peg.white);
						for (byte[] ba : possibleCombinations) {
							if (getFeedback(guess, ba)[0] != blackPegs || getFeedback(guess, ba)[1] != whitePegs || Arrays.equals(guess, ba)) {
								impossibleCombinations.add(ba);
							}
						}
						while (impossibleCombinations.size() > impossibleCombinations.size() * (1 - intelligence)) {
							possibleCombinations.remove(impossibleCombinations.pop());
						}
						impossibleCombinations.empty();
					}
					feedbackUsedForRemoval.add(p.getFeedback());
				}
			}
		}
	}

	/**
	 * Generates a random combination
	 *
	 * @param length The desired length of the combination
	 * @return The generated combination
	 */
	private Combination randomCombination(int length) {
		Peg[] availablePegs = Peg.getAvailablePegs();
		Random rand = new Random();
		Combination combination = new Combination(length);
		for (int i = 0; i < length; i++) {
			combination.setPeg(i, availablePegs[rand.nextInt(availablePegs.length)]);
		}
		return (combination);
	}

	/**
	 * Recursive algorithm that fills the set of all possible combinations with every possible combination
	 * Combinations are represented as byte arrays where each cell contains a Peg ID
	 * Once a combination is generated, it is added to the set
	 *
	 * @param current         The byte array to start with. Used for recursion
	 * @param numberOfColours The number of colours to use
	 * @param numberOfPegs    The number of pegs in each combination
	 */
	private void fillPossibleCombinations(byte[] current, int numberOfColours, int numberOfPegs) {
		if (numberOfPegs == 0) {
			possibleCombinations.add(Arrays.copyOf(current, current.length));
		} else {
			for (byte i = 0; i < numberOfColours; i++) {
				current[numberOfPegs - 1] = i;
				fillPossibleCombinations(current, numberOfColours, numberOfPegs - 1);
			}
		}
	}

	/**
	 * Sleeps for a random amount of time between 1 and 2 seconds. Used to simulate AI 'thinking' time
	 */
	private void sleepRandom() {
		try {
			Thread.currentThread().sleep(new Random().nextInt(1000) + 1000);
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Constrains a double between a minimum and a maximum value
	 *
	 * @param inputValue The double to constrain
	 * @param minValue   The minimum value
	 * @param maxValue   The maximum value
	 * @return The constrained double, between the minimum and maximum value (inclusive)
	 */
	private double constrainDouble(double inputValue, double minValue, double maxValue) {
		if (inputValue < minValue)
			return (minValue);
		if (inputValue > maxValue)
			return (maxValue);
		return (inputValue);
	}
}
