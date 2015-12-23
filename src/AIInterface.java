/**
 * Created by matt on 23/12/2015.
 */
import java.util.Random;

public class AIInterface implements Interface {
	private String name;

	public AIInterface(String name) {
		this.name = name;
	}

	public Combination getGuess(int length) {
		return(randomCombination(length));
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
	}

	public void displayWin() {
		say("I've played simpletons more tactical than you!");
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
}
