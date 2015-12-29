/**
 * Created by matt on 11/12/2015.
 */

import java.io.*;
import java.util.Scanner;

public class Mastermind {
	public static void main(String[] args) {
		boolean guiMode = false;
		for (String s : args) {
			if (s.equals("gui")) {
				//We are in GUI mode
				guiMode = true;
			}
		}

		Interface menuInterface = null;
		if (guiMode) {
			menuInterface = new GraphicalInterface(0, 0, "Mastermind");
		} else {
			menuInterface = new TextualInterface();
		}

		Game game = menuInterface.menu();

		while (true) {
			game.update();

			/*
			Runtime.getRuntime().addShutdownHook(new Thread() { //This code runs on shutdown
				public void run() {
					save(game, "test");
				}
			});
			*/

		}
	}

	public static void save(Game game, String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename + ".mastermind");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(game);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Game load(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename + ".mastermind");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Game game = (Game) ois.readObject();
			ois.close();
			return (game);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return (null);
	}

	public static Game newGame() {
		int numberOfColours = 3;
		int numberOfPegs = 3;
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

		Interface codeBreaker = new AIInterface("Bob");

		Interface codeMaker = new GraphicalInterface(numberOfPegs, 10, "Mastermind");

		Game game = new Game(numberOfColours, numberOfPegs, codeMaker, codeBreaker);

		return (game);
	}
}