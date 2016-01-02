import javax.swing.*;

/**
 * Created by matt on 30/12/2015.
 */
public class TextOutputWindow extends JFrame {
	private boolean newLine;
	private JList<String> list;
	private DefaultListModel listModel;

	public TextOutputWindow(int width, int height) {
		super("Output");
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		listModel = new DefaultListModel();
		list = new JList(listModel);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(list);
		getContentPane().add(new JLabel("Console"));
		getContentPane().add(scrollPane);
		setSize(width, height);
		newLine = true;
	}

	public void print(String message) {
		if (newLine) {
			listModel.addElement(message);
			newLine = false;
		} else {
			listModel.setElementAt((String) listModel.getElementAt(listModel.size() - 1) + message, listModel.size() - 1);
		}
		setVisible(true);
	}

	public void println(String message) {
		print(message);
		newLine = true;
	}
}