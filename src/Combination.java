/**
 * Created by matt on 11/12/2015.
 */

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Combination extends JComponent implements Serializable {
	private int length;
	protected Peg[] pegs;

	public Combination(int length) {
		if (length >= 3 && length <= 8) {
			this.length = length;
			pegs = new Peg[length];
		} else {
			System.err.println("Invalid combination length specified. Must be between 3 and 8");
		}
	}

	public Combination(Peg... pegArray) {
		length = pegArray.length;
		pegs = new Peg[length];
		for (int i=0; i<pegArray.length; i++) {
			setPeg(i, pegArray[i]);
		}
	}

	public Boolean setPeg(int index, Peg peg) {
		if (index < length && index >= 0) {
			pegs[index] = peg;
			return(true);
		} else {
			System.err.println("Invalid peg index specified");
			return(false);
		}
	}

	public Peg getPeg(int index) {
		return(pegs[index]);
	}

	public void addPeg(Peg p) {
		for (int i=0; i<length; i++) {
			if (pegs[i] == null) {
				pegs[i] = p;
				break;
			}
		}
	}

	public void addPeg(Peg p, int number) {
		for (int n=0; n<number; n++) {
			for (int i = 0; i < length; i++) {
				if (pegs[i] == null) {
					pegs[i] = p;
					break;
				}
			}
		}
	}

	public Boolean equals(Combination c) {
		if(c.length == length) {
			Boolean equal = true;
			for (int i=0; i<length; i++) {
				if (c.getPeg(i) != pegs[i]) {
					equal = false;
				}
			}
			return(equal);
		} else {
			return(false);
		}
	}

	public String toString() {
		String output = "";
		for (int i=0; i<length; i++) {
			try {
				output = output + " " + pegs[i].toString();
			} catch (java.lang.NullPointerException e) {
				//No Peg
				output = output + " Empty";
			}
		}
		return(output);
	}

	public int getLength() {
		return(length);
	}

	public int countPegs(Peg p) {
		int count = 0;
		for (Peg n : pegs) {
			if (n == p) {
				count++;
			}
		}
		return(count);
	}

	public Peg[] getPegs() {
		return(pegs);
	}
}