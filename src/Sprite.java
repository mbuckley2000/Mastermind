import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite extends JComponent{
		private BufferedImage image;

		public Sprite(String filePath) {
			try {
				image = ImageIO.read(new File(filePath));
			}catch (IOException e) {
				System.err.println("Couldn't load image");
			}
		}

		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(image, 0, 0, this);
			g2.finalize();
		}
	}
