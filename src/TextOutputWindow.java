import javax.swing.*;

/**
 * Created by matt on 30/12/2015.
 */
public class TextOutputWindow extends JFrame {
	private boolean newLine;
	private JList<String> list;
	private DefaultListModel listModel;

	public TextOutputWindow(int width, int height) {
		super("Mastermind - Output");
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		listModel = new DefaultListModel();
		list = new JList(listModel);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(list);
		getContentPane().add(scrollPane);
		setSize(width, height);
		newLine = true;
	}

	public void print(String message) {
		if (newLine) {
			listModel.addElement(message);
			newLine = false;
		} else {
			listModel.setElementAt(listModel.getElementAt(listModel.size() - 1) + message, listModel.size() - 1);
		}
		setVisible(true);
	}

	public void println(String message) {
		print(message);
		newLine = true;
	}
}