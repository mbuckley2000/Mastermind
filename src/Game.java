/**
 * The game class for the Mastermind game.
 * All of the game logic is controlled in this class.
 * Communicates with two Player interfaces for input.
 * Communicates with an Output interface for output.
 *
 * @author Matt Buckley
 * @since 13/12/2015
 */

public class Game {
	private Board board;
	private Player codeMaker;
	private Player codeBreaker;
	private int gameState;
	private Output output;
	private boolean guiMode;

	/**
	 * Constructs and initialises the game
	 *
	 * @param board       The board that the game will be played on
	 * @param codeMaker   The code maker player object
	 * @param codeBreaker The code breaker player object
	 */
	public Game(Board board, Player codeMaker, Player codeBreaker) {
		this.codeBreaker = codeBreaker;
		this.codeMaker = codeMaker;
		this.board = board;
		Peg.setNumberOfColours(board.getNumberOfColours());
		gameState = 0;
	}

	public static Game getDefaultGame(boolean graphical) {
		Board board = new Board(12, 4, 6);
		Player codeMaker = new AI(board);
		Player codeBreaker = new Human(graphical, board);
		return (new Game(board, codeMaker, codeBreaker));
	}

	/**
	 * Updates the game logic. What happens depends on the value of gameState.
	 * There are five game states:
	 * <ul>
	 * <li>State 0 - The code maker is asked to make a code using their Input object. gameState is set to State 2 when a valid code has been entered</li>
	 * <li>State 1 - The code breaker is asked to guess at the code using their Input object. gameState is set to 3 if either the board is full or the guess was correct (somebody won), or State 2 if not</li>
	 * <li>State 2 - The code maker is asked for feedback on the last guess at the code by the code breaker. gameState is then set back to State 1</li>
	 * <li>State 3 - The game checks who won. If the last guess was correct, then the code breaker wins. If not, the code maker wins</li>
	 * <li>State 4 - Game over. Game loops here doing nothing if in graphical mode, or exits if in textual mode</li>
	 * <li>If gameState is not set to one of these values, an error message is printed and the game exits</li>
	 * </ul>
	 */
	public void update() {

		switch (gameState) {
			case 0: { //Code maker is making the code
				board.setCode(codeMaker.getInput().getCode());
				output.println("Code is set! Starting game");
				gameState = 1;
				break;
			}

			case 1: { //Code breaker is guessing
				board.addGuess(new Guess(codeBreaker.getInput().getGuess()));

				if (whoHasWon() != null) {
					//Somebody won
					gameState = 3;
				} else {
					gameState = 2;
				}
				break;
			}

			case 2: { //Code maker is giving feedback
				board.peek().setFeedback(codeMaker.getInput().getFeedback());
				gameState = 1;
				break;
			}

			case 3: { //End of game
				if (whoHasWon() == codeBreaker) {
					//codeBreaker won
					output.println("Code breaker wins");
				} else {
					//codeMaker won
					output.println("Code maker wins");
				}
				gameState = 4;
				break;
			}

			case 4: {   //Game over
				if (!guiMode) {
					System.exit(0);
				}
				break;
			}

			default: {
				//Invalid gameState
				System.err.println("Invalid gameState: " + gameState);
				System.err.println("This is probably due to a corrupt or incorrectly loaded save file");
				System.exit(1);
				break;
			}
		}
	}

	/**
	 * Gets the current gameState
	 *
	 * @return The current gameState
	 */
	public int getState() {
		return (gameState);
	}

	/**
	 * Sets the gameState
	 *
	 * @param state The new gameState
	 */
	public void setState(int state) {
		gameState = state;
	}

	/**
	 * Returns the board being used by the game
	 *
	 * @return The board
	 */
	public Board getBoard() {
		return (board);
	}

	/**
	 * Sets the board being used by the game
	 *
	 * @param board The board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * Returns the code maker player object
	 *
	 * @return The code maker player object
	 */
	public Player getCodeMaker() {
		return (codeMaker);
	}

	/**
	 * Returns the code breaker player object
	 *
	 * @return The code breaker player object
	 */
	public Player getCodeBreaker() {
		return (codeBreaker);
	}

	/**
	 * Sets the output object being used by the game.
	 * This can be any object which has Output as an interface
	 *
	 * @param output Output object
	 */
	public void setOutput(Output output) {
		this.output = output;
	}

	/**
	 * If a player has won, it returns that player. If not, it returns null
	 */
	private Player whoHasWon() {
		if (board.peek().getGuess().equals(board.getCode())) {
			return (codeMaker);
		} else if (board.isFull()) {
			return (codeBreaker);
		}
		return (null);
	}

	/**
	 * Set whether the game is in graphical mode (true) or textual mode (false). This determines the end-game procedure. It does not change the output mode of the game
	 *
	 * @param guiMode Whether or not the game is in graphical mode
	 */
	public void setGuiMode(boolean guiMode) {
		this.guiMode = guiMode;
	}
}
