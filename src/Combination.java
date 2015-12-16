/**
 * Created by matt on 11/12/2015.
 */

import javax.swing.*;
import java.awt.*;

public class Combination extends JComponent {
	private static int combinationCount = 0;
	private int id;
	private int length;
	protected Peg[] pegs;

	public Combination(int length) {
		if (length >= 3 && length <= 8) {
			this.length = length;
			pegs = new Peg[length];
			id = combinationCount++;
		} else {
			System.err.println("Invalid combination length specified. Must be between 3 and 8");
		}
	}

	public Combination(Peg... pegArray) {
		length = pegArray.length;
		pegs = new Peg[length];
		id = combinationCount++;
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

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < pegs.length; i++) {
			g2.drawImage(pegs[i].getImage(), 10 + 55 * i, 10 + 55 * id, 50, 50, this);
		}
		g2.finalize();
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
}