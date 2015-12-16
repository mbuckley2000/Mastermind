import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;

/**
 * Created by matt on 11/12/2015.
 */
public class Peg {
	private BufferedImage image;
	public static Peg red = new Peg("images/red.png");
	public static Peg blue = new Peg("images/blue.png");
	public static Peg green = new Peg("images/green.png");
	public static Peg yellow = new Peg("images/yellow.png");
	public static Peg black = new Peg("images/yellow.png");
	public static Peg white = new Peg("images/yellow.png");

	public Peg(String pegImageFilePath) {
		try {
			image = ImageIO.read(new File(pegImageFilePath));
		} catch(java.io.IOException e) {
			System.err.println("Unable to locate file: " + pegImageFilePath);
			e.printStackTrace();
		}
	}

	public Image getImage() {
		return(image);
	}


}
