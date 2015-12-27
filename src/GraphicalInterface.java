/**
 * Created by matt on 24/12/2015.
 */
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class GraphicalInterface extends JFrame implements Interface, Serializable {
	private Sprite woodSprite;
	private Sprite hole;

	public GraphicalInterface(int width, int height, String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		woodSprite = new Sprite("images/wood.jpg");
		hole = new Sprite("images/empty.png");
		getLayeredPane().setBounds(0, 0, width, height);
		getLayeredPane().setPreferredSize(new Dimension(width, height));
		getLayeredPane().add(woodSprite, new Integer(1));
	}

	public Combination getGuess(int length) {
		System.out.println("Please enter your guess:");
		return (getCombination(length));
	}

	public Combination getCode(int length) {
		System.out.println("Please enter your code:");
		return (getCombination(length));
	}

	public Combination getFeedback(Combination guess, Combination code) {
		System.out.println("Please enter your feedback:");
		Combination feedback = new Combination(guess.getLength());
		return (getCombination(guess.getLength()));
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

	public void displayBoard(Board board) {
		Combination combination;
		for (int c=0; c<board.getCurrentLength(); c++) {
			combination = board.getCombination(c);
			if (combination != null) {
				//Draw combination
				for (int p=0; p<combination.getPegs().length; p++) {
					Peg peg = combination.getPegs()[p];
					if (peg != null) {
						Sprite sprite = new Sprite(peg.getSpriteFilePath());
						sprite.setPosition(p * 50 + 25, c * 50 + 25);
						sprite.setSize(25, 25);
						getLayeredPane().add(sprite, new Integer(2));
						getLayeredPane().validate();
						getLayeredPane().repaint();
					}
				}
			} else {
			}
		}
		setVisible(true);
	}

	public void clearDisplay() {

	}

	private Combination getCombination(int length) {
		Combination combination = new Combination(length);

		return (new Combination(Peg.getAvailablePegs()));
	}

	public Game menu() {
		return (null);
	}
}