/**
 * Created by matt on 11/12/2015.
 */
import javax.swing.*;
import java.io.Serializable;

public class Window extends JFrame implements Serializable{
	public Window(int width, int height, String title) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setTitle(title);
		setVisible(true);
	}
}
