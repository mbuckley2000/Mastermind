/**
 * A textual input for the mastermind game
 * Uses System.in scanner
 *
 * @author mb2070
 * @since 22/12/2015
 */

import java.util.Arrays;
import java.util.Scanner;

public class TextualInput implements Input {
	private Scanner scanner;
	private Board board;

	/**
	 * @param board The board used by the game instance that the input is for
	 */
	public TextualInput(Board board) {
		scanner = new Scanner(System.in);
		this.board = board;
	}

	/**
	 * Generates and returns a new Game object based on textual user input
	 *
	 * @return The created Game object
	 */
	public static Game newGameMenu() {
		int boardLength = 12;

		int numberOfColours = getInt("Please enter the number of colours you would like (3-8)", 3, 8);

		int numberOfPegs = getInt("Please enter the number of pegs you would like hidden (3-8)", 3, 8);

		String codeMakerType = getString("Please enter the type of player you would like the Code Maker to be", "ai", "human");
		String codeBreakerType = getString("Please enter the type of player you would like the Code Breaker to be", "ai", "human");

		Board board = new Board(boardLength, numberOfPegs, numberOfColours);

		Player codeBreaker;
		if (codeBreakerType.equals("ai")) {
			codeBreaker = new AI(board);
		} else {
			codeBreaker = new Human(false, board);
		}

		Player codeMaker;
		if (codeMakerType.equals("ai")) {
			codeMaker = new AI(board);
		} else {
			codeMaker = new Human(false, board);
		}

		return (new Game(board, codeMaker, codeBreaker));
	}

	/**
	 * Displays a textual menu to the user and allows them to either load or create a new game. Loops until one of these courses of action is selected
	 *
	 * @return The new Game object, generated from newGame()
	 */
	public static Game mainMenu() {
		Scanner scanner = new Scanner(System.in);
		String input;
		System.out.println("Load or new game?");
		Game game = null;
		do {
			input = scanner.nextLine().toLowerCase();
			if (!input.contains("load") && !input.contains("new")) {
				System.err.println("Unrecognized command: " + input);
			}

			if (input.contains("load")) {
				game = Mastermind.load("save");
			}

			if (input.contains("new")) {
				game = newGameMenu();
			}
		} while (!input.contains("load") && !input.contains("new") || game == null);
		return (game);
	}

	private static int getInt(String prompt, int lowerBound, int upperBound) {
		Scanner scanner = new Scanner(System.in);
		int value;
		System.out.println(prompt);
		do {
			value = scanner.nextInt();
			if (value < 3 || value > 8) {
				System.err.println("Invalid value. Please choose a value between " + lowerBound + " and " + upperBound);
			}
		} while (value < lowerBound || value > upperBound);
		return (value);
	}

	private static String getString(String prompt, String... options) {
		Scanner scanner = new Scanner(System.in);
		String string;
		System.out.println(prompt);
		while (true) {
			string = scanner.next();
			for (String s : options) {
				if (s.equals(string.toLowerCase())) {
					return (string);
				}
			}
			System.out.println("Invalid option. Valid options: ");
			for (String s : options) {
				System.out.println(s);
			}
		}
	}

	/**
	 * Gets the player's guess at the code
	 *
	 * @return The guess
	 */
	public Combination getGuess() {
		System.out.println("Please enter your guess:");
		return (getCombination(board.getNumberOfPegs()));
	}

	/**
	 * Gets the player's secret code
	 *
	 * @return The secret code
	 */
	public Combination getCode() {
		System.out.println("Please enter your code: (Write this down and keep it secret!)");
		return (getCombination(board.getNumberOfPegs()));
	}

	/**
	 * Gets the player's feedback on the latest guess
	 *
	 * @return The feedback
	 */
	public Combination getFeedback() {
		System.out.println("Please enter your feedback:");
		return (getCombination(board.getNumberOfPegs()));
	}

	/**
	 * Asks the user to enter a combination of pegs. Also checks for commands:
	 * <ul>
	 * <li>save - Saves and exits the game</li>
	 * </ul>
	 *
	 * @param length Length of the combination
	 * @return Combination given by the user
	 */
	private Combination getCombination(int length) {
		Combination combination = new Combination(length);
		Peg peg;
		for (int i = 0; i < length; i++) {
			//Get color as input
			String input = scanner.next().toLowerCase();
			if (input.equals("save")) {
				if (Mastermind.save("save")) {
					System.out.println("Game saved! Quitting...");
					System.exit(0);
				}
			}
			peg = Peg.getPeg(input);
			if (peg != null && Arrays.asList(Peg.getAvailablePegs()).contains(peg)) {
				combination.setPeg(i, peg);
			} else {
				i--;
				System.err.println("Invalid peg colour: " + input);
			}
		}
		return (combination);
	}
}