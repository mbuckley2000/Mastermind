/**
 * Created by matt on 24/12/2015.
 */

import javax.swing.*;

public class GraphicalInterface extends JFrame implements Interface {
    private static final String type = "Graphical";
    private Sprite woodSprite;
    private Sprite hole;
    private String name;

    public GraphicalInterface(int boardSizeX, int boardSizeY, String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = boardSizeX * 120;
        int height = boardSizeY * 60;
        setSize(width, height);
        setResizable(false);
        drawSprite(new Sprite("images/wood.jpg"), 0, 0, width, height, 0);
        for (int x = 0; x < boardSizeX; x++) {
            for (int y = 0; y < boardSizeY; y++) {
                drawSprite(new Sprite("images/empty.png"), x * 50 + 25, y * 50 + 25, 25, 25, 1);
            }
        }
    }

    public String getPlayerType() {
        return (type);
    }

    public Combination getGuess(int length) {
        return (getCombination("Please enter your guess", length, Peg.getAvailablePegs()));
    }

    public Combination getCode(int length) {
        return (getCombination("Please enter your code", length, Peg.getAvailablePegs()));
    }

    public Combination getFeedback(Combination guess, Combination code) {
        return (getCombination("Please enter your feedback", guess.getLength(), Peg.getPeg("Empty"), Peg.getPeg("Black"), Peg.getPeg("White")));
    }

    public void displayGuess(Combination guess) {
    }

    public void displayCode(Combination code) {
    }

    public void displayFeedback(Combination feedback) {
    }

    public void displayWin() {
    }

    public void displayLose() {
    }

    public String getName() {
        return (name);
    }

    public void displayBoard(Board board) {
        Combination guess;
        Combination feedback;
        for (int c = 0; c < board.getCurrentLength(); c++) {
            guess = board.getCombination(c).getGuess();
            feedback = board.getCombination(c).getFeedback();
            if (guess != null) {
                //Draw combination
                for (int p = 0; p < guess.getPegs().length; p++) {
                    Peg peg = guess.getPegs()[p];
                    if (peg != null) {
                        drawSprite(new Sprite(peg.getSpriteFilePath()), p * 50 + 25, c * 50 + 25, 25, 25, 2);
                    }
                }
            }
            if (feedback != null) {
                for (int p = 0; p < feedback.getPegs().length; p++) {
                    Peg peg = feedback.getPegs()[p];
                    if (peg != null) {
                        drawSprite(new Sprite(peg.getSpriteFilePath()), p * 50 + guess.getLength() * 54, c * 50 + 25, 25, 25, 2);
                    }
                }
            }
        }
        setVisible(true);
    }

    public void clearDisplay() {

    }

    private Combination getCombination(String title, int length, Peg... options) {
        GetCombinationWindow dialog = new GetCombinationWindow(title, options, length, getLocation().x, getLocation().y + getHeight());
        return (dialog.getCombination());
    }

    public Game menu() {
        return (null);
    }

    private void drawSprite(Sprite sprite, int xPos, int yPos, int xSize, int ySize, int zDepth) {
        sprite.setPosition(xPos, yPos);
        sprite.setSize(xSize, ySize);
        getLayeredPane().add(sprite, new Integer(zDepth));
        getLayeredPane().validate();
        getLayeredPane().repaint();
    }
}