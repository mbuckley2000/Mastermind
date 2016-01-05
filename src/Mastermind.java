import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <h1>Mastermind Coursework - created by mb2070</h1>
 * <p>This is a graphical and textual implementation of the classic board game 'Mastermind'</p>
 * <p>It can be played by any combination of two human or AI players</p>
 * <p>By default, it launches in GUI mode. To launch in textual mode, use the command line argument 'textual' (i.e. 'java Mastermind textual'</p>
 *
 * @author mb2070
 * @since 11/12/2015
 */

public class Mastermind {
	private static boolean guiMode = true;
	private static Game game;
	private static Output output;


	/**
	 * Interprets any command lines arguments given by the user:
	 * <ul>
	 * <li>textual - launches the game in textual mode rather than graphical mode, which is the default</li>
	 * <li>help / h - prints available commands to System.out</li>
	 * </ul>
	 *
	 * @param args Command line arguments
	 */
	private static void interpretArguments(String[] args) {
		for (String s : args) {
			if (s.toLowerCase().equals("textual")) {
				//We are in textual mode
				guiMode = false;
			}
			if (s.toLowerCase().equals("help") || s.toLowerCase().equals("h")) {
				printCommands();
			}
		}
	}

	/**
	 * Prints all available command line arguments
	 */
	private static void printCommands() {
		System.out.println("textual - launches the game in textual mode");
		System.out.println("help / h - this information");
	}

	/**
	 * Main method - Sets up the game and output and then updates the Game and Output objects until the game is exited
	 *
	 * @param args Arguments passed from command line
	 */
	public static void main(String[] args) {
		interpretArguments(args);

		if (guiMode) {
			try {
				MenuWindow menuWindow = new MenuWindow();
				while (menuWindow.getGame() == null) {
					try {
						Thread.currentThread().sleep(500);
					} catch (Exception e) {
						Thread.currentThread().interrupt();
					}
				}
				game = menuWindow.getGame();
				output = new GraphicalOutput(game.getBoard());
				menuWindow.dispose();
			} catch (HeadlessException e) {
				System.err.println("Please use a terminal with X11 capability. Otherwise, use 'java Mastermind textual' to launch in non-graphical mode");
				System.exit(0);
			}
		} else {
			game = TextualInput.mainMenu();
			output = new TextualOutput(game.getBoard());
		}

		game.setOutput(output);
		game.setGuiMode(guiMode);

		while (true) {
			if (game.getState() < 4)
				output.update();
			game.update();
		}
	}

	/**
	 * Saves the current Game object to the specified filename using the Save class
	 *
	 * @param filename Filename to save to
	 */
	public static boolean save(String filename) {
		Save save = new Save(game);
		try {
			FileOutputStream fos = new FileOutputStream(filename + ".mastermind");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(save);
			oos.close();
			return (true);
		} catch (Exception e) {
			output.println("Couldn't save. Do you have write permissions?");
			return (false);
		}
	}

	/**
	 * Loads and returns a game object from the specified save file
	 *
	 * @param filename Filename to load from
	 * @return The loaded Game object
	 */
	public static Game load(String filename) {
		Save save = null;
		try {
			FileInputStream fis = new FileInputStream(filename + ".mastermind");
			ObjectInputStream ois = new ObjectInputStream(fis);
			save = (Save) ois.readObject();
			ois.close();
		} catch (Exception e) {
			System.err.println("Couldn't load. Does the save file exist?");
		}
		if (save == null) {
			System.err.println("Default game was created instead");
			try {
				Thread.currentThread().sleep(3000);
			} catch (Exception e) {
				Thread.currentThread().interrupt();
			}
			return (Game.getDefaultGame(guiMode));
		} else {
			return(save.getGame(guiMode));
		}
	}
}