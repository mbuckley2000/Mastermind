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
	private Set<Combination> feedbackUsedForRemoval;

	public AIInput(Board board) {
		this.board = board;
		lastGuess = new PreviousGuess(null);
		possibleCombinations = new HashSet();
		feedbackUsedForRemoval = new HashSet<>();
		fillPossibleCombinations(new byte[board.getNumberOfPegs()], board.getNumberOfColours(), board.getNumberOfPegs());
	}

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
			System.err.println("AI has run out of possible combinations");
			System.exit(1);
		}
		return (null);
	}

	public Combination getCode() {
		this.code = randomCombination(board.getNumberOfPegs());
		return (code);
	}


	//MAKE IT REMOVE THE LAST GUESS IF IT ISNT THE CODE

	private void removeImpossibleCombinations() {
		if (!board.isEmpty()) {
			for (PreviousGuess p : board.getPreviousGuesses()) {
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
						while (!impossibleCombinations.isEmpty()) {
							possibleCombinations.remove(impossibleCombinations.pop());
						}
					}
					feedbackUsedForRemoval.add(p.getFeedback());
				}
			}
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
