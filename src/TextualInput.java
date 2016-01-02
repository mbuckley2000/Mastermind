/**
 * Created by matt on 22/12/2015.
 */

import java.util.Scanner;

public class TextualInput implements Input {
	private Scanner scanner;
	private Board board;

	public TextualInput(Board board) {
		scanner = new Scanner(System.in);
		this.board = board;
	}

	public Combination getGuess() {
		System.out.println("Please enter your guess:");
		return (getCombination(board.getNumberOfPegs()));
	}

	public Combination getCode() {
		System.out.println("Please enter your code:");
		return (getCombination(board.getNumberOfPegs()));
	}

	public Combination getFeedback() {
		System.out.println("Please enter your feedback:");
		Combination feedback = new Combination(board.getNumberOfPegs());
		return (getCombination(board.getNumberOfPegs()));
	}

	private Combination getCombination(int length) {
		Combination combination = new Combination(length);
		String colour;
		Peg peg;
		for (int i = 0; i < length; i++) {
			//Get color as input
			colour = scanner.next();
			if (colour == "save") {
				System.out.println("Enter filename to save: ");
				//game.save(input.next());
				System.out.println("Game saved! Quitting...");
				System.exit(0);
			}
			peg = Peg.getPeg(colour);
			if (peg != null) {
				combination.setPeg(i, peg);
			} else {
				i--;
				System.err.println("Invalid peg colour: " + colour);
			}
		}
		return (combination);
	}
}