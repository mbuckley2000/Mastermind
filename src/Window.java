/**
 * Created by matt on 11/12/2015.
 */
import javax.swing.*;

public class Window extends JFrame{
	public Window(int width, int height, String title) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setTitle(title);
		setVisible(true);
	}
}
