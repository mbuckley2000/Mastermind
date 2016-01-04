/**
 * Created by matt on 13/12/2015.
 */
public class Game {
	private Board board;
	private Player codeMaker;
	private Player codeBreaker;
	private int gameState;
	private int guessCount;
	private Output output;

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
				board.setCode(codeMaker.getInput().getCode());
				gameState = 1;
				break;
			}

			case 1: { //Code breaker is guessing
				Combination guess = codeBreaker.getInput().getGuess();
				board.add(new PreviousGuess(guess));
				if (guess.equals(board.getCode()) || board.isFull()) {
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
				if (board.peek().getGuess().equals(board.getCode())) {
					//Codebreaker won
					output.println("Code breaker wins");
				} else {
					//Codemaker won
					output.println("Code maker wins");
				}
				gameState = 4;
				output.println("Game over in " + guessCount + " guesses");
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

	public void setOutput(Output output) {
		this.output = output;
	}
}
