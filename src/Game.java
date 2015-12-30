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
    private Interface codeMaker;
    private Interface codeBreaker;
    private int gameState;
    private Combination code;
    private int guessCount;

    public Game(int numberOfColours, int numberOfPegs, int boardLength, Interface codeMaker, Interface codeBreaker) {
        this.codeBreaker = codeBreaker;
        this.codeMaker = codeMaker;
        this.numberOfColours = numberOfColours;
        this.numberOfPegs = numberOfPegs;
        board = new Board(boardLength);
        Peg.setNumberOfColours(numberOfColours);
        gameState = 0;
        guessCount = 0;
    }

    public void update() {
        switch (gameState) {
            case 0: { //Code maker is making the code
                codeMaker.clearDisplay();
                code = codeMaker.getCode(numberOfPegs);
                codeMaker.displayCode(code);
                gameState = 1;
                break;
            }

            case 1: { //Code breaker is guessing
                Combination guess = codeBreaker.getGuess(numberOfPegs);
                board.add(new PreviousGuess(guess));
                codeMaker.displayGuess(guess);
                codeMaker.displayBoard(board);
                codeBreaker.displayBoard(board);
                System.out.println("My guess is " + guess.toString());
                if (guess.equals(code)) {
                    //Code breaker has won
                    gameState = 3;
                } else {
                    gameState = 2;
                }
                if (board.isFull()) {
                    //Code maker wins!
                    gameState = 3;
                }
                guessCount++;
                break;
            }

            case 2: { //Code maker is giving feedback
                Combination feedback = codeMaker.getFeedback(board.peek().getGuess(), code);
                board.peek().setFeedback(feedback);
                System.out.println("My feedback is " + feedback.toString());
                codeBreaker.displayFeedback(feedback);
                gameState = 1;
                break;
            }

            case 3: { //End of game
                if (board.peek().getGuess().equals(code)) {
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
}
