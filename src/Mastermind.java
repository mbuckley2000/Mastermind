/**
 * Created by matt on 11/12/2015.
 */

public class Mastermind {
	public static void main(String[] args) {
		//Window window = new Window(800, 600, "Mastermind");

		AI codeBreaker = new AI("Bob");

		AI codeMaker = new AI("Thor");

		Game game = new Game(6, 8, codeMaker, codeBreaker);

		while(true) {
			game.update();
		}
	}
}