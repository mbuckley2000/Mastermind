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
		gameState = 0;
	}

	public void update() {
		switch(gameState) {
			case 0: { //Code maker is making the code
				code = codeMaker.getGuess(numberOfPegs);
				gameState = 1;
				break;
			}

			case 1: { //Code breaker is guessing
				Combination guess = codeBreaker.getGuess(numberOfPegs);
				board.add(guess);
				if (guess.equals(code)) {
					//Code breaker has won
					gameState = 3;
				} else {
					gameState = 2;
				}
				break;
			}

			case 2: { //Code maker is giving feedback
				//Combination feedback = codeMaker.getGuess(Peg.black, Peg.white);
				//Make feedback a subclass of combination

				break;
			}

			case 3: {
				if (board.peek().equals(code)) {
					System.out.println("Code breaker wins!");
				} else {
					System.out.println("Code maker wins!");
				}
				break;
			}

			default: {
				//Invalid gamestate
				System.err.println("Invalid gameState: " + gameState);
				break;
			}
		}
	}

	public void draw(Window window) {
		switch(gameState) {
			case 0: { //Code maker is making the code
				break;
			}

			case 1: { //Code breaker is guessing
				board.draw(window);
				break;
			}

			case 2: { //Code maker is giving feedback
				//Combination feedback = codeMaker.getGuess(Peg.black, Peg.white);
				//Make feedback a subclass of combination

				break;
			}

			case 3: {
				if (board.peek().equals(code)) {
					//System.out.println("Code breaker wins!");
				} else {
					//System.out.println("Code maker wins!");
				}
				break;
			}
		}
	}
}
