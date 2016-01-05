import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A JFrame which gets a combination of pegs from the user
 *
 * @author mb2070
 * @since 28/12/2015
 */
public class GetCombinationWindow extends JFrame {
	private Combination combination;
	private JButton button;
	private JComboBox<Peg>[] comboBoxes;

	/**
	 * Constructs and displays the window
	 *
	 * @param title     Title of and message displayed in the window
	 * @param options   An array of acceptable pegs
	 * @param length    Desired length of the combination
	 * @param locationX X location of the window on the screen
	 * @param locationY Y location of the window on the screen
	 */
	public GetCombinationWindow(String title, Peg[] options, int length, int locationX, int locationY) {
		super(title);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(100 * length + 100, 200);
		setLayout(new FlowLayout());
		setLocation(locationX, locationY);

		JLabel message = new JLabel(title);
		add(message);

		comboBoxes = new JComboBox[length];

		//Components
		for (int i = 0; i < length; i++) {
			comboBoxes[i] = new JComboBox<>(options);
			add(comboBoxes[i]);
		}

		button = new JButton("OK");
		add(button);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				combination = new Combination(comboBoxes.length);
				for (JComboBox<Peg> comboBox : comboBoxes) {
					combination.addPeg((Peg) comboBox.getSelectedItem());
				}
			}});

		pack();
		setVisible(true);
	}

	/**
	 * Gets a combination as input from the combination window.
	 * Waits until the button is pressed, and then returns the given combination/
	 *
	 * @return Returns the inputted combination
	 */
	public Combination getCombination() {
		return (combination);
	}
}
