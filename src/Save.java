import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by matt on 29/12/2015.
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

	public Save(Game game) {
		Board board = game.getBoard();
		Player codeMaker = game.getCodeMaker();
		Player codeBreaker = game.getCodeBreaker();

		gameState = game.getState();
		guessesMade = board.getCurrentLength();
		feedbacksGiven = board.getFeedbacksGiven();
		numberOfColours = board.getNumberOfColours();
		numberOfPegs = board.getNumberOfPegs();
		maxBoardLength = board.getMaxLength();
		code = board.getCode().toIDArray();

		guesses = new byte[guessesMade][numberOfPegs];
		feedbacks = new byte[feedbacksGiven][2];

		for (int i=0; i<guessesMade; i++) {
			guesses[i] = board.getCombination(i).getGuess().toIDArray();
		}

		int i=0;
		for (byte[] ba : feedbacks) {
			Combination feedback = board.getCombination(i).getFeedback();
			if (feedback != null) {
				ba[0] = (byte) feedback.countPegs(Peg.black);
				ba[1] = (byte) feedback.countPegs(Peg.white);
			}
			i++;
		}

		playerType = new String[]{codeMaker.getPlayerType(), codeBreaker.getPlayerType()};
	}

	public Game getGame(boolean graphical) {
		Board board = getBoard();
		Player codeMaker = getPlayer(0, board, graphical);
		Player codeBreaker = getPlayer(1, board, graphical);
		Game game = new Game(board, codeMaker, codeBreaker);
		game.setState(gameState);
		return (game);
	}

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

	private Board getBoard() {
		Board board = new Board(maxBoardLength, numberOfPegs, numberOfColours);
		for (int y = 0; y < guessesMade; y++) {
			Combination guess = new Combination(guesses[y]);
			board.add(new PreviousGuess(guess));
		}
		for (int y = 0; y < feedbacksGiven; y++) {
			Combination feedback = new Combination(numberOfPegs);
			feedback.addPeg(Peg.black, feedbacks[y][0]);
			feedback.addPeg(Peg.white, feedbacks[y][1]);
			board.setFeedback(y, feedback);
		}
		return (board);
	}
}
