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

    public static byte[] getFeedback(byte[] guess, byte[] code) {
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
                    if (guess[i] == code[j] && ignore[j] == null) {
                        ignore[j] = true;
                        whiteCounter++;
                        break;
                    }
                }
            }

            return (new byte[]{blackCounter, whiteCounter});
        } else {
            System.err.println("Guess and code are different lengths. Cannot generate feedback");
            return (null);
        }
    }

    public Combination getGuess(int length) {
        lastGuessID = possibleCombinations.iterator().next();
        lastGuess = new PreviousGuess(new Combination(lastGuessID));
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
        byte[] feedback = getFeedback(guess.toIDArray(), code.toIDArray());
        Combination feedbackCombination = new Combination(guess.getLength());
        feedbackCombination.addPeg(Peg.black, feedback[0]);
        feedbackCombination.addPeg(Peg.white, feedback[1]);
        return (feedbackCombination);
    }

    public void displayGuess(Combination guess) {
    }

    public void displayCode(Combination code) {
    }

    public void displayFeedback(Combination feedback) {
        say("Hmm... Let me think");
        Stack<byte[]> impossibleCombinations = new Stack();
        impossibleCombinations.add(lastGuessID);
        lastGuess.setFeedback(feedback);
        byte blackPegs = (byte) feedback.countPegs(Peg.black);
        byte whitePegs = (byte) feedback.countPegs(Peg.white);
        if (blackPegs + whitePegs != 0) {
            for (byte[] ba : possibleCombinations) {
                if (getFeedback(lastGuessID, ba)[0] != blackPegs || getFeedback(lastGuessID, ba)[1] != whitePegs) {
                    impossibleCombinations.add(ba);
                }
            }
        }
        while (!impossibleCombinations.isEmpty()) {
            possibleCombinations.remove(impossibleCombinations.pop());
        }
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

    private void say(String message) {
        System.out.println(name + ": " + message);
    }

    private Combination randomCombination(int length) {
        Peg[] availablePegs = Peg.getAvailablePegs();
        Random rand = new Random();
        Combination combination = new Combination(length);
        for (int i = 0; i < length; i++) {
            combination.setPeg(i, availablePegs[rand.nextInt(availablePegs.length)]);
        }
        return (combination);
    }

    public String getPlayerType() {
        return (type);
    }

    private void fillPossibleCombinations(byte[] current, int numberOfColours, int numberOfPegs) {
        if (numberOfPegs == 0) {
            possibleCombinations.add(Arrays.copyOf(current, current.length));
        } else {
            for (byte i = 0; i < numberOfColours; i++) {
                current[numberOfPegs - 1] = i;
                fillPossibleCombinations(current, numberOfColours, numberOfPegs - 1);
            }
        }
    }

    public Game menu() {
        return (null);
    }
}
