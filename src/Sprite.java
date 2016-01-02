import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite extends JComponent {
	private BufferedImage image;
	private Vector2i position;
	private Vector2i size;

	public Sprite(String filePath, JFrame target) {
		super();
		try {
			image = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			System.err.println("Couldn't load sprite image: " + filePath);
		}
		position = new Vector2i(0, 0);
		size = new Vector2i(image.getWidth(), image.getHeight());
		setBounds(0, 0, target.getWidth(), target.getHeight());
	}

	public void setPosition(int x, int y) {
		position.x = x;
		position.y = y;
	}

	public void setSize(int x, int y) {
		size.x = x;
		size.y = y;
	}

	public Vector2i getPosition() {
		return (position);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, position.x, position.y, size.x, size.y, this);
	}
}
