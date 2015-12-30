/**
 * Created by matt on 22/12/2015.
 */

import java.util.Scanner;

public class TextualInterface implements Interface {
    private static final String type = "Textual";
    private Scanner input;
    private String name;

    public TextualInterface() {
        input = new Scanner(System.in);
    }

    public Combination getGuess(int length) {
        System.out.println("Please enter your guess:");
        return (getCombination(length));
    }

    public Combination getCode(int length) {
        System.out.println("Please enter your code:");
        return (getCombination(length));
    }

    public String getPlayerType() {
        return (type);
    }

    public Combination getFeedback(Combination guess, Combination code) {
        System.out.println("Please enter your feedback:");
        Combination feedback = new Combination(guess.getLength());
        return (getCombination(guess.getLength()));
    }


    public String getName() {
        return (name);
    }

    public void displayGuess(Combination guess) {
        System.out.println("Guess:" + guess.toString());
    }

    public void displayCode(Combination code) {
        System.out.println("Code:" + code.toString());
    }

    public void displayFeedback(Combination feedback) {
        System.out.println("Feedback:" + feedback.toString());
    }

    public void displayWin() {
        System.out.println("Victory!");
    }

    public void displayLose() {
        System.out.println("Defeat.");
    }

    public void displayBoard(Board board) {
        for (int i = 0; i < board.getMaxLength(); i++) {
            if (i < board.getCurrentLength()) {
                System.out.println(board.getCombination(i).toString());
            } else {
                System.out.println("********************************************");
            }
        }
    }

    public void clearDisplay() {
        try {
            Runtime.getRuntime().exec("clear");
        } catch (java.io.IOException e) {

        }
    }

    public void setObserver(Observer observer) {
    }

    private Combination getCombination(int length) {
        Combination combination = new Combination(length);
        String colour;
        Peg peg;
        for (int i = 0; i < length; i++) {
            //Get color as input
            colour = input.next();
            if (colour == "save") {
                System.out.println("Enter filename to save: ");
                //game.save(input.next());
	            System.out.println("Game saved! Quitting...");
	            System.exit(0);
            }
            peg = Peg.getPeg(colour);
            if (peg != null) {
                combination.setPeg(i, peg);
            } else {
                i--;
                System.err.println("Invalid peg colour: " + colour);
            }
        }
        return (combination);
    }
}