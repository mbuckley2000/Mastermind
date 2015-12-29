import java.io.Serializable;

/**
 * Created by matt on 29/12/2015.
 */
public class Save implements Serializable {
	private String[][] guesses;
	private String[][] feedbacks;
	private int boardLength;
	private int numberOfPegs;
	private int numberOfColours;
	private int gameState;
	private String[] playerType;
	private String[] playerName;

	public int getBoardLength() {
		return(boardLength);
	}

	public int getNumberOfPegs() {
		return(numberOfPegs);
	}

	public int getNumberOfColours() {
		return(numberOfColours);
	}

	public int getGameState() {
		return(gameState);
	}

	public String getPlayerType(int player) {
		return(playerType[player]);
	}

	public String getPlayerName(int player) {
		return(playerName[player]);
	}

	public Board getBoard() {
		Board board = new Board(boardLength);
		for (int y=0; y<boardLength; y++) {
			Combination guess = new Combination(numberOfPegs);
			Combination feedback = new Combination(numberOfPegs);
			for (int x=0; x<numberOfPegs; x++) {
				guess.setPeg(x, Peg.getPeg(guesses[x][y]));
				feedback.setPeg(x, Peg.getPeg(feedbacks[x][y]));
			}
			board.add(new PreviousGuess(guess));
			board.peek().setFeedback(feedback);
		}
		return(board);
	}

	public void set(int gameState, Board board, int numberOfPegs, int numberOfColours, Interface codeMaker, Interface codeBreaker) {
		boardLength = board.getMaxLength();
		this.numberOfPegs = numberOfPegs;
		this.numberOfColours = numberOfColours;
		this.gameState = gameState;

		guesses = new String[numberOfPegs][board.getCurrentLength()];
		feedbacks = new String[numberOfPegs][board.getCurrentLength()];

		for (int x=0; x<numberOfPegs; x++) {
			for (int y=0; y<board.getCurrentLength(); y++) {
				guesses[x][y] = board.getCombination(y).getGuess().getPeg(x).toString();
				feedbacks[x][y] = board.getCombination(y).getFeedback().getPeg(x).toString();
			}
		}

		playerName[0] = codeMaker.getName();
		playerName[1] = codeBreaker.getName();
		playerType[0] = codeMaker.getPlayerType();
		playerType[1] = codeBreaker.getPlayerType();
	}



}
