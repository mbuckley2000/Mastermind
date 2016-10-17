import java.io.Serializable;

/**
 * A serializable class that stores all data and methods necessary to reconstruct an in-progress mastermind game
 *
 * @author Matt Buckley
 * @since 29/12/2015
 */
public class Save implements Serializable {
	private byte[][] guesses;
	private byte[][] feedbacks;
	private byte[] code;
	private int guessesMade;
	private int feedbacksGiven;
	private int maxBoardLength;
	private int numberOfPegs;
	private int numberOfColours;
	private int gameState;
	private String[] playerType;

	/**
	 * Takes a given game object and copies all required data from it into a Save in primitive form, ready to be serialized and saved to a file
	 *
	 * @param game The game object to be saved
	 */
	public Save(Game game) {
		Board board = game.getBoard();
		Player codeMaker = game.getCodeMaker();
		Player codeBreaker = game.getCodeBreaker();

		gameState = game.getState();
		guessesMade = board.getCurrentLength();
		feedbacksGiven = board.countFeedbacksGiven();
		numberOfColours = board.getNumberOfColours();
		numberOfPegs = board.getNumberOfPegs();
		maxBoardLength = board.getMaxLength();
		code = board.getCode().toIDArray();

		guesses = new byte[guessesMade][numberOfPegs];
		feedbacks = new byte[feedbacksGiven][2];

		for (int i = 0; i < guessesMade; i++) {
			guesses[i] = board.getGuess(i).getGuess().toIDArray();
		}

		int i = 0;
		for (byte[] ba : feedbacks) {
			Combination feedback = board.getGuess(i).getFeedback();
			if (feedback != null) {
				ba[0] = (byte) feedback.countPegs(Peg.black);
				ba[1] = (byte) feedback.countPegs(Peg.white);
			}
			i++;
		}

		playerType = new String[]{codeMaker.getPlayerType(), codeBreaker.getPlayerType()};
	}

	/**
	 * Reconstructs a Mastermind game from the primitive data set by the constructor
	 *
	 * @param graphical Whether or not the Mastermind game is in guiMode or Textual mode
	 * @return The reconstructed game
	 */
	public Game getGame(boolean graphical) {
		Board board = getBoard();
		Player codeMaker = getPlayer(0, board, graphical);
		Player codeBreaker = getPlayer(1, board, graphical);
		Game game = new Game(board, codeMaker, codeBreaker);
		game.setState(gameState);
		return (game);
	}

	/**
	 * Reconstructs the codeMaker or the codeBreaker as a Player object from the primitive data set by the constructor
	 *
	 * @param playerNumber 0 (codeMaker) or 1 (codeBreaker)
	 * @param board        The board that will be used by the players
	 * @param graphical    Whether or not the Mastermind game is in guiMode or Textual mode
	 * @return The reconstructed player
	 */
	private Player getPlayer(int playerNumber, Board board, boolean graphical) {
		if (playerNumber == 0 || playerNumber == 1) {
			Player player = null;
			if (playerType[playerNumber].equals("Human")) {
				player = new Human(graphical, board);
			} else if (playerType[playerNumber].equals("AI")) {
				player = new AI(board);
			}
			return (player);
		} else {
			return (null);
		}
	}

	/**
	 * Reconstructs the board from the primitive data set by the constructor
	 *
	 * @return The reconstructed Board object
	 */
	private Board getBoard() {
		Board board = new Board(maxBoardLength, numberOfPegs, numberOfColours);
		for (int y = 0; y < guessesMade; y++) {
			Combination guess = new Combination(guesses[y]);
			try {
				board.addGuess(new Guess(guess));
			} catch (Exception e) {
				System.err.println("Board is full. Save is probably corrupt");
				e.printStackTrace();
			}
		}
		for (int y = 0; y < feedbacksGiven; y++) {
			Combination feedback = new Combination(numberOfPegs);
			feedback.addPeg(Peg.black, feedbacks[y][0]);
			feedback.addPeg(Peg.white, feedbacks[y][1]);
			board.setFeedback(y, feedback);
		}
		board.setCode(new Combination(code));
		return (board);
	}
}
