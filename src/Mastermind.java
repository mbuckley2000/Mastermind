/**
 * Created by matt on 11/12/2015.
 */

import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.util.Scanner;


public class Mastermind {
    public static void main(String[] args) {
        boolean guiMode = true;
        for (String s : args) {
            if (s.equals("textual")) {
                //We are in textual mode
                guiMode = false;
            }
        }

	    Game game;

        if (guiMode) {
	        MenuWindow menuWindow = new MenuWindow();
	        game = menuWindow.getGame();
            game.setGraphical(true);
        } else {
	        game = menu();
            game.setGraphical(false);
        }

        while (true) {
            game.update();
        }
    }

    private static Game load(String filename) {
	    try {
            FileInputStream fis = new FileInputStream(filename + ".mastermind");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Save save = (Save)ois.readObject();
            ois.close();
		    return(save.getGame());

	    } catch (Exception e) {
	        e.printStackTrace();
        }
	    return (null);
    }

    public static Game newGame() {
        int numberOfColours = 3;
        int numberOfPegs = 3;
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

        Interface codeBreaker = new AIInterface("Breaker", numberOfColours, numberOfPegs);
        Interface codeMaker = new AIInterface("Maker", numberOfColours, numberOfPegs);
        //Interface codeMaker = new GraphicalInterface(numberOfPegs, boardLength, "Mastermind");

        Game game = new Game(numberOfColours, numberOfPegs, boardLength, codeMaker, codeBreaker);

        return (game);
    }

	public static Game menu() {
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