import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by matt on 30/12/2015.
 */
public class MenuWindow extends JFrame {
	private JButton okButton;
	private Integer[] numbers;
	private String[] playerTypes;
	private JLabel coloursLabel;
	private JLabel pegsLabel;
	private JLabel makerLabel;
	private JLabel breakerLabel;
	private JComboBox<Integer> coloursCombo;
	private JComboBox<Integer> pegsCombo;
	private JComboBox<String> makerCombo;
	private JComboBox<String> breakerCombo;
	private Game game;

	public MenuWindow() {
		super("Mastermind");
		setSize(1000, 1000);
		setLayout(new FlowLayout());
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);

		numbers = new Integer[]{new Integer(3), new Integer(4), new Integer(5), new Integer(6), new Integer(7), new Integer(8)};
		playerTypes = new String[]{"Human", "AI"};
		coloursLabel = new JLabel("Number of colours: ");
		pegsLabel = new JLabel("Number of pegs: ");
		makerLabel = new JLabel("Code maker type: ");
		breakerLabel = new JLabel("Code breaker type: ");
		coloursCombo = new JComboBox(numbers);
		pegsCombo = new JComboBox(numbers);
		makerCombo = new JComboBox(playerTypes);
		breakerCombo = new JComboBox(playerTypes);

		add(coloursLabel);
		add(coloursCombo);
		add(pegsLabel);
		add(pegsCombo);
		add(makerLabel);
		add(makerCombo);
		add(breakerLabel);
		add(breakerCombo);

		okButton = new JButton("OK");
		add(okButton);

		pack();
		setVisible(true);
	}

	public Game getGame() {
		game = null;
		while (game == null) {
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (game == null) {
						Player codeMaker;
						Player codeBreaker;
						int numberOfColours = (Integer) coloursCombo.getSelectedItem();
						int numberOfPegs = (Integer) pegsCombo.getSelectedItem();
						int boardLength = 12;
						Board board = new Board(boardLength, numberOfPegs, numberOfColours);

						if (makerCombo.getSelectedItem().equals("AI")) {
							codeMaker = new AI(board);
						} else {
							codeMaker = new Human(true, board);
						}

						if (breakerCombo.getSelectedItem().equals("AI")) {
							codeBreaker = new AI(board);
						} else {
							codeBreaker = new Human(true, board);
						}

						game = new Game(board, codeMaker, codeBreaker);
					}
				}
			});
		}
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		return (game);
	}
}
