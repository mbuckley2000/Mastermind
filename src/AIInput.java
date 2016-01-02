/**
 * Created by matt on 23/12/2015.
 */

import java.util.*;

public class AIInput implements Input {
	private PreviousGuess lastGuess;
	private Set<byte[]> possibleCombinations;
	private byte[] lastGuessID;
	private Board board;
	private Combination code;

	public AIInput(Board board) {
		this.board = board;
		lastGuess = new PreviousGuess(null);
		possibleCombinations = new HashSet();
		fillPossibleCombinations(new byte[board.getNumberOfPegs()], board.getNumberOfColours(), board.getNumberOfPegs());
	}

	private static byte[] getFeedback(byte[] guess, byte[] code) {
		if (guess.length == code.length) {
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
					if (guess[i] == code[j] && ignore[j] == null) {
						ignore[j] = true;
						whiteCounter++;
						break;
					}
				}
			}

			return (new byte[]{blackCounter, whiteCounter});
		} else {
			System.err.println("Guess and code are different lengths. Cannot generate feedback");
			return (null);
		}
	}

	public Combination getFeedback() {
		byte[] feedback = getFeedback(board.peek().getGuess().toIDArray(), code.toIDArray());
		Combination feedbackCombination = new Combination(board.getNumberOfPegs());
		feedbackCombination.addPeg(Peg.black, feedback[0]);
		feedbackCombination.addPeg(Peg.white, feedback[1]);
		return (feedbackCombination);
	}

	public Combination getGuess() {
		removeImpossibleCombinations();
		try {
			lastGuessID = possibleCombinations.iterator().next();
			lastGuess = new PreviousGuess(new Combination(lastGuessID));
			return (lastGuess.getGuess());
		} catch (NoSuchElementException e) {
			//Run out of possible combinations!
		}
		return (null);
	}

	public Combination getCode() {
		this.code = randomCombination(board.getNumberOfPegs());
		return (code);
	}

	private void removeImpossibleCombinations() {
		Combination feedback = board.peek().getFeedback();
		Stack<byte[]> impossibleCombinations = new Stack();
		impossibleCombinations.add(lastGuessID);
		lastGuess.setFeedback(feedback);
		byte blackPegs = (byte) feedback.countPegs(Peg.black);
		byte whitePegs = (byte) feedback.countPegs(Peg.white);
		if (blackPegs + whitePegs != 0) {
			for (byte[] ba : possibleCombinations) {
				if (getFeedback(lastGuessID, ba)[0] != blackPegs || getFeedback(lastGuessID, ba)[1] != whitePegs) {
					impossibleCombinations.add(ba);
				}
			}
		}
		while (!impossibleCombinations.isEmpty()) {
			possibleCombinations.remove(impossibleCombinations.pop());
		}
	}

	private Combination randomCombination(int length) {
		Peg[] availablePegs = Peg.getAvailablePegs();
		Random rand = new Random();
		Combination combination = new Combination(length);
		for (int i = 0; i < length; i++) {
			combination.setPeg(i, availablePegs[rand.nextInt(availablePegs.length)]);
		}
		return (combination);
	}

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
}
