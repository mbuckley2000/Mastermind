/**
 * Created by matt on 22/12/2015.
 */
public interface Interface {
    Combination getGuess(int length);

    Combination getFeedback(Combination guess, Combination code);

    Combination getCode(int length);

    void displayGuess(Combination combination);

    void displayCode(Combination code);

    void displayFeedback(Combination feedback);

    void clearDisplay();

    void displayBoard(Board board);

    void displayWin();

    void displayLose();

    String getName();

    String getPlayerType();

    void setObserver(Observer observer);
}
