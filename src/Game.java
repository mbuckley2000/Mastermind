/**
 * Created by matt on 13/12/2015.
 */

/*
	Game States:
		0 - Code maker is making the code
		1 - Code breaker is guessing
		2 - Code maker is giving feedback
		4 - Game over
*/


public class Game {
	private Board board;
	private int numberOfColours;
	private int numberOfPegs;
	private Player codeMaker;
	private Player codeBreaker;
	private int gameState;
	private Combination code;

	public Game(int numberOfColours, int numberOfPegs, Player codeMaker, Player codeBreaker) {
		this.codeBreaker = codeBreaker;
		this.codeMaker = codeMaker;
		this.numberOfColours = numberOfColours;
		this.numberOfPegs = numberOfPegs;
		board = new Board(numberOfPegs);
		Peg.setNumberOfColours(numberOfColours);
		gameState = 0;
	}

	public void update() {
		Interface codeMakerInterface = codeMaker.getInterface();
		Interface codeBreakerInterface = codeBreaker.getInterface();
		switch(gameState) {
			case 0: { //Code maker is making the code
				codeMakerInterface.clearDisplay();
				code = codeMakerInterface.getCode(numberOfPegs);
				codeMakerInterface.displayCode(code);
				System.out.println(code.toString());
				gameState = 1;
				break;
			}

			case 1: { //Code breaker is guessing
				Combination guess = codeBreakerInterface.getGuess(numberOfPegs);
				board.add(guess);
				codeBreakerInterface.displayGuess(guess);
				codeMakerInterface.displayGuess(guess);
				if (guess.equals(code)) {
					//Code breaker has won
					gameState = 3;
				} else {
					gameState = 2;
				}
				break;
			}

			case 2: { //Code maker is giving feedback
				Combination feedback = codeMakerInterface.getFeedback(board.peek(), code);
				codeMakerInterface.displayFeedback(feedback);
				codeBreakerInterface.displayFeedback(feedback);
				gameState = 1;
				break;
			}

			case 3: { //End of game
				if (board.peek().equals(code)) {
					codeMakerInterface.displayLose();
					codeBreakerInterface.displayWin();
				} else {
					codeMakerInterface.displayWin();
					codeBreakerInterface.displayLose();
				}
				gameState = 4;
				break;
			}

			case 4: { //Reset game
				board.empty();
				gameState = 0;
				break;
			}
			default: {
				//Invalid gamestate
				System.err.println("Invalid gameState: " + gameState);
				break;
			}
		}
	}

	public Combination getCode() {
		return(code);
	}
}
