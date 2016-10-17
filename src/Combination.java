/**
 * A Combination of Peg objects for the Mastermind game. The board consists of a collection of these
 *
 * @author Matt Buckley
 * @since 11/12/2015
 */

public class Combination {
	private Peg[] pegs;
	private int length;

	/**
	 * Constructs an empty combination given the maximum number of pegs it can hold
	 *
	 * @param length Maximum number of pegs it can hold
	 */
	public Combination(int length) {
		if (length >= 3 && length <= 8) {
			this.length = length;
			pegs = new Peg[length];
		} else {
			System.err.println("Invalid combination length specified. Must be between 3 and 8");
		}
	}

	/**
	 * Constructs a combination from given Pegs
	 *
	 * @param pegArray Variable length parameter. The pegs which will make up the combination.
	 */
	public Combination(Peg... pegArray) {
		length = pegArray.length;
		pegs = pegArray;
	}

	/**
	 * Constructs a combination from a byte array of Peg IDs.
	 * These are used to speed up AI processing and save memory.
	 *
	 * @param idArray The array of Peg IDs to generate the combination from
	 */
	public Combination(byte[] idArray) {
		length = idArray.length;
		pegs = new Peg[length];
		for (int i = 0; i < length; i++) {
			pegs[i] = Peg.getPeg(idArray[i]);
		}
	}


	/**
	 * Sets the peg at a certain index
	 *
	 * @param index Index to set the peg at
	 * @param peg   Peg to set
	 * @return Returns false if an invalid index was specified, true if successful
	 */
	public Boolean setPeg(int index, Peg peg) {
		if (index < length && index >= 0) {
			pegs[index] = peg;
			return (true);
		} else {
			System.err.println("Invalid peg index specified");
			return (false);
		}
	}

	/**
	 * Gets the peg at the specified index
	 *
	 * @param index The index
	 * @return The Peg
	 */
	public Peg getPeg(int index) {
		return (pegs[index]);
	}

	/**
	 * Adds a peg to the next free slot in the combination. Used to construct combinations easily.
	 *
	 * @param peg The peg to add
	 */
	public void addPeg(Peg peg) {
		for (int i = 0; i < length; i++) {
			if (pegs[i] == null) {
				pegs[i] = peg;
				break;
			}
		}
	}

	/**
	 * Adds a specified number of the same peg to the next free slots in the combinations
	 *
	 * @param peg    Peg to add
	 * @param number Number of times to add it
	 */
	public void addPeg(Peg peg, int number) {
		for (int n = 0; n < number; n++) {
			addPeg(peg);
		}
	}

	/**
	 * Checks if the contents of this combination is equal to that of another
	 *
	 * @param combination The combination to check against
	 * @return Whether or not they ar equal
	 */
	public Boolean equals(Combination combination) {
		if (combination.length == length) {
			Boolean equal = true;
			for (int i = 0; i < length; i++) {
				if (combination.getPeg(i) != pegs[i]) {
					equal = false;
				}
			}
			return (equal);
		} else {
			return (false);
		}
	}

	/**
	 * Converts the combination into a string of peg colours
	 *
	 * @return String of peg colours
	 */
	public String toString() {
		String output = "";
		for (int i = 0; i < length; i++) {
			if (pegs[i] != null) {
				output = output + "\t" + pegs[i].toString();
			}
		}
		return (output);
	}

	/**
	 * Count the number of times the specified peg appears in the combination
	 *
	 * @param p The Peg
	 * @return The number of times it appears
	 */
	public int countPegs(Peg p) {
		int count = 0;
		for (Peg n : pegs) {
			if (n == p) {
				count++;
			}
		}
		return (count);
	}

	/**
	 * Returns a peg array of all pegs in the combinations
	 *
	 * @return Peg array of all pegs in the combinations
	 */
	public Peg[] getPegs() {
		return (pegs);
	}

	/**
	 * Converts the combination into a byte array of Peg IDs
	 * Used to speed up AI processing and save memory
	 *
	 * @return Byte array of peg IDs
	 */
	public byte[] toIDArray() {
		byte[] array = new byte[length];
		for (int i = 0; i < length; i++) {
			array[i] = pegs[i].getID();
		}
		return (array);
	}
}