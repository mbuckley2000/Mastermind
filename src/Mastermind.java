/**
 * Created by matt on 11/12/2015.
 */

public class Mastermind {
	public static void main(String[] args) {
		//Window window = new Window(800, 600, "Mastermind");

		Human codeBreaker = new Human();

		AI codeMaker = new AI("Thor");

		Game game = new Game(4, 4, codeMaker, codeBreaker);

		while(true) {
			game.update();
		}
	}
}