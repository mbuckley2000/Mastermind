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
	private Interface playerInterface;

	public Human() {
		input = new Scanner(System.in);
		playerInterface = new TextualInterface();
	}

	public Interface getInterface() {
		return(playerInterface);
	}
}