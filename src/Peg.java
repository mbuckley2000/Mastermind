import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import java.io.Serializable;
import java.util.Random;

/**
 * Created by matt on 11/12/2015.
 */
public class Peg implements Serializable{
	private BufferedImage image;
	private String colour;
	private static int numberOfColours;

	private static Peg[] allPegs = {
			new Peg("Red", "images/red.png"),
			new Peg("Blue", "images/blue.png"),
			new Peg("Green", "images/green.png"),
			new Peg("Yellow", "images/yellow.png"),
			new Peg("Purple", "images/purple.png"),
			new Peg("Orange", "images/orange.png"),
			new Peg("Pink", "images/pink.png"),
			new Peg("Gold", "images/gold.png"),
			new Peg("Black", "images/black.png"),
			new Peg("White", "images/white.png")
	};

	private static Peg[] availiablePegs;

	public Peg(String colour, String pegImageFilePath) {
		try {
			image = ImageIO.read(new File(pegImageFilePath));
		} catch(Exception e) {
			System.err.println("Unable to locate file: " + pegImageFilePath);
		}
		this.colour = colour;
	}

	public Image getImage() {
		return(image);
	}

	public String toString() {
		return(colour);
	}

	public static Peg getPeg(String name) {
		for (Peg p : allPegs) {
			if (name.toLowerCase().equals(p.toString().toLowerCase())) {
				return(p);
			}
		}
		return(null);
	}

	public static void setNumberOfColours(int numberOfColours) {
		Peg.numberOfColours = numberOfColours;
		availiablePegs = new Peg[numberOfColours];
		for (int i=0; i<numberOfColours; i++) {
			availiablePegs[i] = allPegs[i];
		}

	}

	public static Peg[] getAvailablePegs() {
		return(availiablePegs);
	}

	public static Peg getRandomPeg() {
		Random rand = new Random();
		return(availiablePegs[rand.nextInt(availiablePegs.length)]);
	}
}
