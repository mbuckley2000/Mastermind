import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A JFrame that contains game controls.
 * This currently consists of a save button
 *
 * @author Matt Buckley
 * @since 03/01/2016
 */
public class GameControlsWindow extends JFrame {

	/**
	 * Constructs and displays the window, which currently holds a save button
	 * When the button is pressed, a new thread is created which saves the game and exits
	 */
	public GameControlsWindow() {
		super("Mastermind - Game Controls");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());

		JButton saveButton = new JButton("Save & Exit");
		getContentPane().add(saveButton);

		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Mastermind.save("save")) {
					System.exit(0);
				}
			}
		});

		pack();
		setVisible(true);
	}
}