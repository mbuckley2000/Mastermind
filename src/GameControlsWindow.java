import oracle.jvm.hotspot.jfr.JFR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by matt on 03/01/2016.
 */
public class GameControlsWindow extends JFrame {
	public GameControlsWindow() {
		super("Mastermind - Game Controls");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());

		JButton saveButton = new JButton("Save");
		getContentPane().add(saveButton);

		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Mastermind.save("save");
			}
		});

		pack();
		setVisible(true);
	}
}