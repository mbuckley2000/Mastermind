import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by matt on 28/12/2015.
 */
public class GetCombinationWindow extends JFrame {
	private Combination combination;
	private JButton button;
	private JComboBox<Peg>[] comboBoxes;

	public GetCombinationWindow(String title, Peg[] options, int length, int locationX, int locationY) {
		super(title);
		setSize(100 * length + 100, 200);
		setLayout(new FlowLayout());
		setLocation(locationX, locationY);

		JLabel message = new JLabel(title);
		add(message);

		comboBoxes = new JComboBox[length];

		//Components
		for (int i = 0; i < length; i++) {
			comboBoxes[i] = new JComboBox<Peg>(options);
			add(comboBoxes[i]);
		}

		button = new JButton("OK");
		add(button);
		pack();
		setVisible(true);
	}

	public Combination getCombination() {
		combination = null;
		while (combination == null) {
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (combination == null) {
						combination = new Combination(comboBoxes.length);
						for (JComboBox<Peg> comboBox : comboBoxes) {
							combination.addPeg((Peg) comboBox.getSelectedItem());
						}
					}
				}
			});
		}
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		return (combination);
	}
}
