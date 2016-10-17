/**
 * A Peg class for the Mastermind game. Combinations are made of these, and the Board is made of combinations.
 * Contains many static predefined pegs, which have corresponding images for GUI mode.
 * All pegs are given an ID when created. These IDs are used instead of Peg objects to speed up AI processing and to use less memory
 *
 * @author Matt Buckley
 * @since 11/12/2015
 */

public class Peg {
	private static byte idCounter;
	private static Peg[] availablePegs;
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

	/**
	 * Constructs a new Peg given the peg's colour and its image filepath
	 *
	 * @param colour         The peg's colour. Used to identify it in Textual interfaces
	 * @param spriteFilePath The filepath of the Peg's corresponding image
	 */
	public Peg(String colour, String spriteFilePath) {
		this.id = idCounter++;
		this.colour = colour;
		this.spriteFilePath = spriteFilePath;
	}

	/**
	 * Finds and returns a predefined peg using it's name
	 *
	 * @param name The peg's name
	 * @return The found Peg object
	 */
	public static Peg getPeg(String name) {
		for (Peg p : allPegs) {
			if (name.toLowerCase().equals(p.toString().toLowerCase())) {
				return (p);
			}
		}
		return (null);
	}

	/**
	 * Finds and returns a predefined peg using it's ID. Each peg colour is given an ID, and these are used to speed up AI calculations
	 *
	 * @param id The peg's ID
	 * @return The found Peg object
	 */
	public static Peg getPeg(byte id) {
		for (Peg p : allPegs) {
			if (id == p.getID()) {
				return (p);
			}
		}
		return (null);
	}

	/**
	 * Sets the pegs that are available to the user based on the number of colours that they specified
	 *
	 * @param numberOfColours The number of colours specified by the user at the start of the game
	 */
	public static void setNumberOfColours(int numberOfColours) {
		availablePegs = new Peg[numberOfColours];
		for (int i = 0; i < numberOfColours; i++) {
			availablePegs[i] = allPegs[i];
		}

	}

	/**
	 * Returns all available pegs as an array
	 *
	 * @return Available peg array
	 */
	public static Peg[] getAvailablePegs() {
		return (availablePegs);
	}

	/**
	 * Returns the name of the peg
	 *
	 * @return Name of the peg
	 */
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
