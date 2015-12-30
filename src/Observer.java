/**
 * Created by matt on 30/12/2015.
 */
public class Observer {
	private boolean useSystemOut;
	private TextOutputWindow window;

	public Observer(boolean useSystemOut) {
		this.useSystemOut = useSystemOut;
		if (!useSystemOut) {
			window = new TextOutputWindow();
		}
	}

	public void print(String message) {
		if (useSystemOut) {
			System.out.print(message);
		} else {
			window.print(message);
		}
	}

	public void println(String message) {
		if (useSystemOut) {
			System.out.println(message);
		} else {
			window.println(message);
		}
	}
}
