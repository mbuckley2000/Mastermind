import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by matt on 13/12/2015.
 */
public class Game {
	private Board board;
	private Player codeMaker;
	private Player codeBreaker;
	private int gameState;
	private Combination code;
	private int guessCount;

	public Game(Board board, Player codeMaker, Player codeBreaker) {
		this.codeBreaker = codeBreaker;
		this.codeMaker = codeMaker;
		this.board = board;
		Peg.setNumberOfColours(board.getNumberOfColours());
		gameState = 0;
		guessCount = 0;
	}

	public void update() {
		switch (gameState) {
			case 0: { //Code maker is making the code
				code = codeMaker.getInput().getCode();
				gameState = 1;
				break;
			}

			case 1: { //Code breaker is guessing
				Combination guess = codeBreaker.getInput().getGuess();
				board.add(new PreviousGuess(guess));
				if (guess.equals(code) || board.isFull()) {
					//Somebody won
					gameState = 3;
				} else {
					gameState = 2;
				}
				guessCount++;
				break;
			}

			case 2: { //Code maker is giving feedback
				Combination feedback = codeMaker.getInput().getFeedback();
				board.peek().setFeedback(feedback);
				gameState = 1;
				break;
			}

			case 3: { //End of game
				//Guesscount print
				if (board.peek().getGuess().equals(code)) {
					//Codebreaker won
				} else {
					//Codemaker won
				}
				gameState = 4;
				break;
			}

			case 4:
				//Finished
				break;

			default: {
				//Invalid gamestate
				System.err.println("Invalid gameState: " + gameState);
				break;
			}
		}
	}

	public void save(String filename) {
		Save save = new Save(this);
		try {
			FileOutputStream fos = new FileOutputStream(filename + ".mastermind");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(save);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getState() {
		return (gameState);
	}

	public void setState(int state) {
		gameState = state;
	}

	public Board getBoard() {
		return (board);
	}

	public void setBoard(Board board) {
	}

	public Player getCodeMaker() {
		return (codeMaker);
	}

	public Player getCodeBreaker() {
		return (codeBreaker);
	}
}
