/**
 * Mastermind Coursework - mb2070
 * This is a graphical and textual implementation of the classic board game 'Mastermind'
 * It can be played by any combination of two human or AI players
 * By default, it launches in GUI mode. To launch in textual mode, use the command line argument: textual (e.g. 'java Mastermind textual'
 *
 * @author mb2070
 * @version 1.0
 * @since 11/12/2015
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Mastermind {
	private static boolean guiMode = true;
	private static Game game;
	private static Output output;

	private static void interpretArguments(String[] args) {
		for (String s : args) {
			if (s.equals("textual")) {
				//We are in textual mode
				guiMode = false;
			}
		}
	}

	public static void main(String[] args) {
		interpretArguments(args);

		if (guiMode) {
			MenuWindow menuWindow = new MenuWindow();
			game = menuWindow.getGame();
			output = new GraphicalOutput(game.getBoard());
		} else {
			game = textualMenu();
			output = new TextualOutput(game.getBoard());
		}

		game.setOutput(output);

		while (true) {
			output.update();
			game.update();
		}
	}

	public static void save(String filename) {
		Save save = new Save(game);
		try {
			FileOutputStream fos = new FileOutputStream(filename + ".mastermind");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(save);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Game load(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename + ".mastermind");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Save save = (Save) ois.readObject();
			ois.close();
			return (save.getGame(guiMode));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (null);
	}

	public static Game newGame() {
		int numberOfColours;
		int numberOfPegs;
		int boardLength = 12;
		Scanner scanner = new Scanner(System.in);

		System.out.println("Please enter the number of colours you would like (3-8)");
		do {
			numberOfColours = scanner.nextInt();
			if (numberOfColours < 3 || numberOfColours > 8) {
				System.err.println("Invalid number of colours. Please choose a value between 3 and 8");
			}
		} while (numberOfColours < 3 || numberOfColours > 8);

		System.out.println("Please enter the number of pegs you would like hidden (3-8)");
		do {
			numberOfPegs = scanner.nextInt();
			if (numberOfPegs < 3 || numberOfPegs > 8) {
				System.err.println("Invalid number of pegs. Please choose a value between 3 and 8");
			}
		} while (numberOfPegs < 3 || numberOfPegs > 8);

		Board board = new Board(boardLength, numberOfPegs, numberOfColours);

		Player codeBreaker = new AI(board);
		Player codeMaker = new AI(board);

		Game game = new Game(board, codeMaker, codeBreaker);

		return (game);
	}

	public static Game textualMenu() {
		Scanner input = new Scanner(System.in);

		String lastString = "";
		System.out.println("Load or new game?");
		Game game = null;
		do {
			lastString = input.nextLine().toLowerCase();
			if (!lastString.contains("load") && !lastString.contains("new")) {
				System.err.println("Unrecognized command: " + lastString);
			}

			if (lastString.contains("load")) {
				System.out.println("Please enter the filename of the save that you wish to load:");
				game = load(input.nextLine());
			}

			if (lastString.contains("new")) {
				game = Mastermind.newGame();
			}
		} while (!lastString.contains("load") && !lastString.contains("new") || game == null);
		return (game);
	}
}