import java.util.Random;

/**
 * Created by matt on 11/12/2015.
 */

public class Peg {
	private static byte idCounter;
	private static Peg[] availiablePegs;
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
			new Peg("White", "images/white.png"),
			new Peg("Empty", "images/empty.png")
	};
	public static Peg black = getPeg("Black");
	public static Peg white = getPeg("White");
	private byte id;
	private String colour;
	private String spriteFilePath;


	public Peg(String colour, String spriteFilePath) {
		this.id = idCounter++;
		this.colour = colour;
		this.spriteFilePath = spriteFilePath;
	}

	public static Peg getPeg(String name) {
		for (Peg p : allPegs) {
			if (name.toLowerCase().equals(p.toString().toLowerCase())) {
				return (p);
			}
		}
		return (null);
	}

	public static Peg getPeg(byte id) {
		for (Peg p : allPegs) {
			if (id == p.getID()) {
				return (p);
			}
		}
		return (null);
	}

	public static void setNumberOfColours(int numberOfColours) {
		availiablePegs = new Peg[numberOfColours];
		for (int i = 0; i < numberOfColours; i++) {
			availiablePegs[i] = allPegs[i];
		}

	}

	public static Peg[] getAvailablePegs() {
		return (availiablePegs);
	}

	public static Peg getRandomPeg() {
		Random rand = new Random();
		return (availiablePegs[rand.nextInt(availiablePegs.length)]);
	}

	public String toString() {
		return (colour);
	}

	public String getSpriteFilePath() {
		return (spriteFilePath);
	}

	public byte getID() {
		return (id);
	}
}
