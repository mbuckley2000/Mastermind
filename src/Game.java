import java.io.Serializable;

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


public class Game implements Serializable {
	private Board board;
	private int numberOfColours;
	private int numberOfPegs;
	private Interface codeMaker;
	private Interface codeBreaker;
	private int gameState;
	private Combination code;
	private int guessCount;

	public Game(int numberOfColours, int numberOfPegs, Interface codeMaker, Interface codeBreaker) {
		this.codeBreaker = codeBreaker;
		this.codeMaker = codeMaker;
		this.numberOfColours = numberOfColours;
		this.numberOfPegs = numberOfPegs;
		board = new Board(500);
		Peg.setNumberOfColours(numberOfColours);
		gameState = 0;
		guessCount = 0;
	}

	public void update() {
		switch(gameState) {
			case 0: { //Code maker is making the code
				codeMaker.clearDisplay();
				code = codeMaker.getCode(numberOfPegs);
				codeMaker.displayCode(code);
				System.out.println(code.toString());
				gameState = 1;
				break;
			}

			case 1: { //Code breaker is guessing
				Combination guess = codeBreaker.getGuess(numberOfPegs);
				board.add(guess);
				codeMaker.displayGuess(guess);
				if (guess.equals(code)) {
					//Code breaker has won
					gameState = 3;
				} else {
					gameState = 2;
				}
				guessCount++;
				break;
			}

			case 2: { //Code maker is giving feedback
				Combination feedback = codeMaker.getFeedback(board.peek(), code);
				codeBreaker.displayFeedback(feedback);
				gameState = 1;
				break;
			}

			case 3: { //End of game
				if (board.peek().equals(code)) {
					codeMaker.displayLose();
					codeBreaker.displayWin();
				} else {
					codeMaker.displayWin();
					codeBreaker.displayLose();
				}
				System.out.println("Guesses: " + guessCount);
				gameState = 4;
				break;
			}

			case 4: { //Reset game
				board.empty();
				//gameState = 0;
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
