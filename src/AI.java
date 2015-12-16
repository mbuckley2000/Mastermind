/**
 * Created by matt on 13/12/2015.
 */
public class AI implements Player {
	public Combination getGuess(int length) {
		return(new Combination(Peg.red, Peg.green, Peg.blue, Peg.yellow));
	}
}
