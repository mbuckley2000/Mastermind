import java.io.Serializable;

/**
 * Created by matt on 29/12/2015.
 */
public class Save implements Serializable {
	private byte[][] guesses;
	private byte[][] feedbacks;
	private int boardLength;
	private int numberOfPegs;
	private int numberOfColours;
	private int gameState;
	private String[] playerType;

	public Save(Game game) {
		Board board = game.getBoard();
		Player codeMaker = game.getCodeMaker();
		Player codeBreaker = game.getCodeBreaker();

		gameState = game.getState();
		boardLength = board.getMaxLength();
		numberOfColours = board.getNumberOfColours();
		numberOfPegs = board.getNumberOfPegs();

		guesses = new byte[board.getCurrentLength()][numberOfPegs];
		feedbacks = new byte[board.getCurrentLength()][2];

		int i = 0;
		for (byte[] ba : guesses) {
			ba = board.getCombination(i++).getGuess().toIDArray();
		}

		i = 0;
		for (byte[] ba : feedbacks) {
			ba[0] = (byte) board.getCombination(i++).getFeedback().countPegs(Peg.black);
			ba[1] = (byte) board.getCombination(i++).getFeedback().countPegs(Peg.white);
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
		Board board = new Board(boardLength, numberOfPegs, numberOfColours);
		for (int y = 0; y < boardLength; y++) {
			Combination guess = new Combination(guesses[y]);
			Combination feedback = new Combination(numberOfPegs);
			feedback.addPeg(Peg.black, feedbacks[y][0]);
			feedback.addPeg(Peg.white, feedbacks[y][1]);
			board.add(new PreviousGuess(guess));
			board.peek().setFeedback(feedback);
		}
		return (board);
	}
}
