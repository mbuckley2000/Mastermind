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
	private String[] playerName;

	public Save(int gameState, Board board, int numberOfPegs, int numberOfColours, Interface codeMaker, Interface codeBreaker) {
		boardLength = board.getMaxLength();
		this.numberOfPegs = numberOfPegs;
		this.numberOfColours = numberOfColours;
		this.gameState = gameState;

		guesses = new byte[board.getCurrentLength()][numberOfPegs];
		feedbacks = new byte[board.getCurrentLength()][2];

		int i=0;
		for (byte[] ba : guesses) {
			ba = board.getCombination(i++).getGuess().toIDArray();
		}

		i=0;
		for (byte[] ba : feedbacks) {
			ba[0] = (byte)board.getCombination(i++).getFeedback().countPegs(Peg.black);
			ba[1] = (byte)board.getCombination(i++).getFeedback().countPegs(Peg.white);
		}

		playerType = new String[] {codeMaker.getPlayerType(), codeBreaker.getPlayerType()};
		playerName = new String[] {codeMaker.getName(), codeBreaker.getName()};
	}

	public Game getGame() {
		Interface codeMaker = getPlayer(0);
		Interface codeBreaker = getPlayer(1);
		Game game = new Game(numberOfColours, numberOfPegs, boardLength, codeMaker, codeBreaker);
		game.setState(gameState);
		game.setBoard(getBoard());
		return(game);
	}

	private Interface getPlayer(int playerNumber) {
		if (playerNumber == 0 || playerNumber == 1) {
			Interface player = null;
			if (playerType[playerNumber].toLowerCase().equals("textual")) {
				player = new TextualInterface();
			} else if (playerType[playerNumber].toLowerCase().equals("graphical")) {
				player = new GraphicalInterface(numberOfPegs, boardLength, "Mastermind");
			} else if (playerType[playerNumber].toLowerCase().equals("ai")) {
				player = new AIInterface(playerName[0], numberOfColours, numberOfPegs);
			}
			return (player);
		} else {
			return(null);
		}
	}

	private Board getBoard() {
		Board board = new Board(boardLength);
		for (int y=0; y < boardLength; y++) {
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
