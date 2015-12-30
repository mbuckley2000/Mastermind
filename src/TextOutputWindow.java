import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * Created by matt on 30/12/2015.
 */
public class TextOutputWindow extends JFrame {
	private Stack<String> messageStack;
	private boolean newLine;
	private JList<String> list;
	private DefaultListModel listModel;

	public TextOutputWindow() {
		super("Output");
		messageStack = new Stack();
		listModel = new DefaultListModel();
		newLine = true;
		setLayout(new FlowLayout());
		list = new JList(listModel);
		getContentPane().add(list);
		setSize(800, 800);
		setVisible(true);
	}

	public void print(String message) {
		if (newLine) {
			messageStack.add(message);
			newLine = false;
		} else {
			messageStack.add(messageStack.pop() + message);
		}
		refresh();
	}

	public void println(String message) {
		if (newLine) {
			messageStack.add(message);
		} else {
			messageStack.add(messageStack.pop() + message);
		}
		newLine = true;
	}

	private void refresh() {
		while (listModel.size() < messageStack.size()) {
			listModel.addElement("");
		}
		for (int i=0; i<messageStack.size(); i++) {
			if (!messageStack.get(i).equals(listModel.get(i))) {
				listModel.set(i, messageStack.get(i));
			}
		}
		list.validate();
		list.repaint();
		setVisible(true);
	}
}
