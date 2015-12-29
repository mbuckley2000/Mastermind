/**
 * Created by matt on 23/12/2015.
 */

import java.util.*;

public class AIInterface implements Interface {
    private static final String type = "AI";
    private String name;
    private PreviousGuess lastGuess;
    private Set<byte[]> possibleCombinations;
    private byte[] lastGuessID;

    public AIInterface(String name, int numberOfColours, int numberOfPegs) {
        this.name = name;
        lastGuess = new PreviousGuess(null);
        possibleCombinations = new HashSet();
        fillPossibleCombinations(new byte[numberOfPegs], numberOfColours, numberOfPegs);
    }

    public Combination getGuess(int length) {
        lastGuessID = possibleCombinations.iterator().next();
        lastGuess = new PreviousGuess(new Combination(lastGuessID));
        System.err.println("Remaining Possible Combinations: " + possibleCombinations.size());
        return (lastGuess.getGuess());
    }

    public Combination getCode(int length) {
        //Generate Random Code
        return (randomCombination(length));
    }

    public String getName() {
        return (name);
    }

    public Combination getFeedback(Combination guess, Combination code) {
        if (guess.getLength() == code.getLength()) {
            int blackCounter = 0;
            int whiteCounter = 0;
            Boolean[] ignore = new Boolean[code.getLength()];

            for (int i = 0; i < guess.getLength(); i++) {
                if (guess.getPeg(i) == code.getPeg(i)) {
                    ignore[i] = true;
                    blackCounter++;
                }
            }

            for (int i = 0; i < guess.getLength(); i++) {
                for (int j = 0; j < code.getLength(); j++) {
                    if (guess.getPeg(i) == code.getPeg(j) && ignore[j] == null) {
                        ignore[j] = true;
                        whiteCounter++;
                        break;
                    }
                }
            }

            Combination feedback = new Combination(guess.getLength());
            feedback.addPeg(Peg.black, blackCounter);
            feedback.addPeg(Peg.white, whiteCounter);

            return (feedback);
        } else {
            System.err.println("Guess and code are different lengths. Cannot generate feedback");
            return (null);
        }
    }

    public byte[] getFeedback(byte[] guess, byte[] code) {
        if (guess.length == code.length) {
            byte blackCounter = 0;
            byte whiteCounter = 0;
            Boolean[] ignore = new Boolean[code.length];

            for (int i = 0; i < guess.length; i++) {
                if (guess[i] == code[i]) {
                    ignore[i] = true;
                    blackCounter++;
                }
            }

            for (int i = 0; i < guess.length; i++) {
                for (int j = 0; j < code.length; j++) {
                    if (guess[i] == code[i] && ignore[j] == null) {
                        ignore[j] = true;
                        whiteCounter++;
                        break;
                    }
                }
            }

            return (new byte[] {blackCounter, whiteCounter});
        } else {
            System.err.println("Guess and code are different lengths. Cannot generate feedback");
            return (null);
        }
    }

    public void displayGuess(Combination guess) {
    }

    public void displayCode(Combination code) {
    }

    public void displayFeedback(Combination feedback) {
        lastGuess.setFeedback(feedback);
        if (feedback.countPegs(Peg.getPeg("Black")) != 0 || feedback.countPegs(Peg.getPeg("white")) != 0 ) {
            int i = 0;
            while (possibleCombinations.iterator().hasNext()) {
                byte[] ba = possibleCombinations.iterator().next();
                if (getFeedback(ba, lastGuessID)[0] != lastGuess.getFeedback().countPegs(Peg.getPeg("Black")) || getFeedback(ba, lastGuessID)[1] != lastGuess.getFeedback().countPegs(Peg.getPeg("White"))) {
                    possibleCombinations.remove(ba);
                }
                if (i++ > possibleCombinations.size()) {
                    break;
                }
            }
        }
        possibleCombinations.remove(lastGuessID);
    }

    public void displayWin() {
        say("I'm growing tired of playing fools...");
    }

    public void displayLose() {
        say("You have defeated me this time...");
    }

    public void displayBoard(Board board) {
    }

    public void clearDisplay() {
    }

    private void say (String message) {
        System.out.println(name + ": " + message);
    }

    private Combination randomCombination(int length) {
        Peg[] availiablePegs = Peg.getAvailablePegs();
        Random rand = new Random();
        Combination combination = new Combination(length);
        for (int i = 0; i < length; i++) {
            combination.setPeg(i, availiablePegs[rand.nextInt(availiablePegs.length)]);
        }
        return (combination);
    }

    public String getPlayerType() {
        return (type);
    }

    private void fillPossibleCombinations(byte[] current, int numberOfColours, int numberOfPegs){
        if (numberOfPegs == 0) {
            possibleCombinations.add(Arrays.copyOf(current, current.length));
        } else {
            for (byte i=0; i<numberOfColours; i++) {
                current[numberOfPegs - 1] = i;
                fillPossibleCombinations(current, numberOfColours, numberOfPegs - 1);
            }
        }
    }

    public Game menu() {
        return (null);
    }
}
