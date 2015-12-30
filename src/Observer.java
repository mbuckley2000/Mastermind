import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Created by matt on 30/12/2015.
 */
public class Observer {
	private boolean useSystemOut;
	private TextOutputWindow textWindow;
	private GraphicalInterface gameWindow;

	public Observer() {
		useSystemOut = true;
	}

	public Observer(int numberOfPegs, int boardLength) {
		useSystemOut = false;
		gameWindow = new GraphicalInterface(numberOfPegs, boardLength, "Mastermind");
		textWindow = new TextOutputWindow(gameWindow.getWidth(), 200);
		textWindow.setLocation(gameWindow.getX(), gameWindow.getY() + gameWindow.getHeight());
		gameWindow.addComponentListener(new ComponentAdapter() {
			public void componentMoved(ComponentEvent e) {
				textWindow.setLocation(gameWindow.getX(), gameWindow.getY() + gameWindow.getHeight());
			}
		});
	}

	public void print(String message) {
		if (useSystemOut) {
			System.out.print(message);
		} else {
			textWindow.print(message);
		}
	}

	public void println(String message) {
		if (useSystemOut) {
			System.out.println(message);
		} else {
			textWindow.println(message);
		}
	}

	public GraphicalInterface getGameWindow() {
		return(gameWindow);
	}
}
