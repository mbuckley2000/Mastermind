/**
 * Created by matt on 13/12/2015.
 */

/*
	0 - Code maker is making the code
	1 - Code breaker is guessing
	2 - Code maker is giving feedback
	4 - Game over
*/

import java.util.Scanner;

public class Human implements Player {
	private Scanner input;

	public Human() {
		input = new Scanner(System.in);
	}

	public Combination getGuess(int length) {
		Combination guess = new Combination(length);
		String colour;
		for (int i=0; i<length; i++) {
			//Get color as input
			System.out.println("Please enter a colour:");
			colour = input.next();
			switch(colour.toLowerCase()) {
				case "red": guess.setPeg(i, Peg.red);
					break;
				case "blue": guess.setPeg(i, Peg.blue);
					break;
				case "green": guess.setPeg(i, Peg.green);
					break;
				case "yellow": guess.setPeg(i, Peg.yellow);
					break;
				default:  //Invalid colour
					i--;
					System.err.println("Invalid peg colour: " + colour);
					break;
			}
		}
		return(guess);
	}
}