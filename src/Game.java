import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by matt on 13/12/2015.
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
    private Observer observer;

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
                observer.getGameWindow().displayBoard(board);
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
                observer.println("Game completed in " + guessCount + " guesses");
	            //System.exit(0);
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

	public void setBoard(Board board) {
	}

	public void setState(int state) {
		gameState = state;
	}

    public void save(String filename) {
        Save save = new Save(gameState, board, numberOfPegs, numberOfColours, codeMaker, codeBreaker);
	    try {
		    FileOutputStream fos = new FileOutputStream(filename + ".mastermind");
		    ObjectOutputStream oos = new ObjectOutputStream(fos);
		    oos.writeObject(save);
		    oos.close();
	    } catch (Exception e) {
		    e.printStackTrace();
	    }
    }

    public void setGraphical(boolean graphical) {
        if (graphical) {
            if (!codeMaker.getPlayerType().equals("Graphical") || !codeBreaker.getPlayerType().equals("Graphical"))
            this.observer = new Observer(numberOfPegs, board.getMaxLength());
        } else {
            this.observer = new Observer();
        }
        codeMaker.setObserver(observer);
        codeBreaker.setObserver(observer);
    }
}
