/**
 * Created by matt on 23/12/2015.
 */
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class AIInterface implements Interface {
	private String name;
	private Set<PreviousGuess> previousGuesses;
	private PreviousGuess lastGuess;

	public AIInterface(String name) {
		this.name = name;
		previousGuesses = new HashSet<PreviousGuess>();
		lastGuess = new PreviousGuess(null);
	}

	public Combination getGuess(int length) {
		Combination guess = new Combination(length);
		if (previousGuesses.isEmpty()) {
			guess.addPeg(Peg.getRandomPeg(), length / 2);
			guess.addPeg(Peg.getRandomPeg(), length / 2);
		} else {
			Iterator<PreviousGuess> iterator = previousGuesses.iterator();
			int highestScore = 0;
			PreviousGuess bestGuess = iterator.next();
			while (iterator.hasNext()) {
				PreviousGuess g = iterator.next();
				if (scoreGuess(g) > highestScore) {
					highestScore = scoreGuess(g);
					bestGuess = g;
				}
			}
				int blackPegs = bestGuess.blackPegCount();
				int whitePegs = bestGuess.whitePegCount();
				guess = adjustGuess(bestGuess.getGuess(), blackPegs, whitePegs);
		}
		lastGuess = new PreviousGuess(guess);
		previousGuesses.add(lastGuess);
		say(guess.toString());
		return(guess);
	}

	public Combination getCode(int length) {
		//Generate Random Code
		return(randomCombination(length));
	}

	public Combination getFeedback(Combination guess, Combination code) {
		if (guess.getLength() == code.getLength()) {
			int blackCounter = 0;
			int whiteCounter = 0;
			Boolean[] codeIgnore = new Boolean[code.getLength()];
			Boolean[] guessIgnore = new Boolean[guess.getLength()];

			for (int i = 0; i < guess.getLength(); i++) {
				if (guess.getPeg(i) == code.getPeg(i)) {
					codeIgnore[i] = true;
					guessIgnore[i] = true;
					blackCounter++;
				}
			}

			for (int i = 0; i < guess.getLength(); i++) {
				for (int j = 0; j < code.getLength(); j++) {
					if (guess.getPeg(i) == code.getPeg(j) && codeIgnore[j] == null && guessIgnore[i] == null) {
						whiteCounter++;
						codeIgnore[j] = true;
						guessIgnore[i] = true;
						break;
					}
				}
			}

			Combination feedback = new Combination(guess.getLength());
			for (int i=0; i<blackCounter; i++) {
				feedback.addPeg(Peg.getPeg("Black"));
			}
			for (int i=0; i<whiteCounter; i++) {
				feedback.addPeg(Peg.getPeg("White"));
			}
			say(feedback.toString());
			return(feedback);

		} else {
			return(null);
			//Different lengths
		}
	}

	public void displayGuess(Combination guess) {
	}

	public void displayCode(Combination code) {
	}

	public void displayFeedback(Combination feedback) {
		lastGuess.setFeedback(feedback);
	}

	public void displayWin() {
		say("I'm growing tired of playing fools...");
	}

	public void displayLose() {
		say("You have defeated me this time...");
	}

	public void displayBoard(Board board) {
	}

	public void clearDisplay() {}

	private void say(String message) {
		System.out.println(name + ": " + message);
	}

	private Combination randomCombination(int length) {
		Peg[] availiablePegs = Peg.getAvailablePegs();
		Random rand = new Random();
		Combination combination = new Combination(length);
		for (int i=0; i<length; i++) {
			combination.setPeg(i, availiablePegs[rand.nextInt(availiablePegs.length)]);
		}
		return(combination);
	}

	private Combination adjustGuess(Combination previousGuess, int blackPegs, int whitePegs) {
		Combination newGuess = new Combination(previousGuess.getLength());
		Random rand = new Random();
		int indexA = 0;
		int indexB = 0;
		int loopLimit=0;
		Boolean[] ignoreList = new Boolean[previousGuess.getLength()];
		for (int i=0; i<blackPegs; i++) {
			do {
				loopLimit++;
				if (loopLimit>50) {
					loopLimit = 0;
					break;
				}
				indexA = rand.nextInt(previousGuess.getLength());
			} while (ignoreList[indexA] != null);
			ignoreList[indexA] = true;
			newGuess.setPeg(indexA, previousGuess.getPeg(indexA));
		}
		for (int i=0; i<whitePegs; i++) {
			do {
				loopLimit++;
				if (loopLimit>50) {
					loopLimit = 0;
					break;
				}
				indexA = rand.nextInt(previousGuess.getLength());
			} while (ignoreList[indexA] != null);
			ignoreList[indexA] = true;
			do {
				loopLimit++;
				if (loopLimit>50) {
					loopLimit = 0;
					break;
				}
				indexB = rand.nextInt(previousGuess.getLength());
			} while (ignoreList[indexB] != null);
			ignoreList[indexB] = true;
			newGuess.setPeg(indexB, previousGuess.getPeg(indexA));
			newGuess.setPeg(indexA, previousGuess.getPeg(indexB));
		}
		for (int i=0; i<previousGuess.getLength()-blackPegs-whitePegs; i++) {
			do {
				loopLimit++;
				if (loopLimit>50) {
					loopLimit = 0;
					break;
				}
				indexA = rand.nextInt(previousGuess.getLength());
			} while (ignoreList[indexA] != null);
			ignoreList[indexA] = true;
			newGuess.setPeg(indexA, Peg.getRandomPeg());
		}
		return(newGuess);
	}

	private int scoreGuess(PreviousGuess guess) {
		int score = 2 * guess.blackPegCount() + guess.whitePegCount();
		return(score);
	}
}
