/**
 * Created by matt on 24/12/2015.
 */
import java.io.Serializable;
import java.util.Scanner;

public class GraphicalInterface implements Interface, Serializable {
	private Scanner input;
	private Window window;

	public GraphicalInterface() {
		input = new Scanner(System.in);
		window = new Window(800, 600, "Mastermind");
	}

	public Combination getGuess(int length) {
		System.out.println("Please enter your guess:");
		return (getCombination(length));
	}

	public Combination getCode(int length) {
		System.out.println("Please enter your code:");
		return (getCombination(length));
	}

	public Combination getFeedback(Combination guess, Combination code) {
		System.out.println("Please enter your feedback:");
		Combination feedback = new Combination(guess.getLength());
		return (getCombination(guess.getLength()));
	}

	public void displayGuess(Combination guess) {
	}

	public void displayCode(Combination code) {
	}

	public void displayFeedback(Combination feedback) {
	}

	public void displayWin() {
	}

	public void displayLose() {
	}

	public void displayBoard(Board board) {

	}

	public void clearDisplay() {

	}

	private Combination getCombination(int length) {
		Combination combination = new Combination(length);

		return (combination);
	}

	public Game menu() {
		return (null);
	}
}