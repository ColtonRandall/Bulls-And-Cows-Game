package a1_bullsAndCows;

import java.io.IOException;

public abstract class Player {

    // --------------- INSTANCE VARIABLES ----------------
    protected String secretCode = "";
    public String fileMessage;
    protected String guess = "";
    protected int bulls;
    protected int cows;
    public static boolean isManual;
    public String winningMessage;


    // --------------- CONSTANTS ----------------
    public final static int GUESS_LIMIT = 7;
    public final static int CODE_LENGTH = 4;


    // --------------- Methods ----------------

    /*
     Abstract method to be implemented by both child classes (User and Computer). Outputs
     a specified message for each player.
    */
    public abstract void printResult(int turnNum, String secretCode) throws IOException;

    // To be implemented by User class only.
    public void checkValidCode() {
    }


    // --------------- GET CODE (accessor) ----------------
    public String getSecretCode() {
        return secretCode;
    }


    // --------------- SET CODE (mutator) ----------------
    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }


    // --------------- GET GUESS (accessor) ----------------
    public String getGuess() {
        return guess;
    }

    // --------------- SET GUESS (mutator) ----------------
    public void setGuess(String guess) {
        this.guess = guess;
    }

    // --------------- GET PREVIOUS GUESS (accessor) ----------------
    public String getLastGuess() {
        return guess;
    }


    // --------------- GET BULLS & COWS ----------------
    public void getBullsAndCows(String guess, String secretCode) {
        bulls = 0;
        cows = 0;

        // Make sure guesses are 4-digits in length
        if (guess.length() != CODE_LENGTH) {
            throw new IllegalStateException("Guess must be 4 digits long.");
        }

        // Loop through each player's guess
        for (int i = 0; i < guess.length(); i++) {

            /* If the digits in the player's guess and other player's
               secret code are matching AND in their right positions,
               increment 'bulls'.
            */
            if (guess.charAt(i) == secretCode.charAt(i)) {
                bulls++;

                // If digits are matching but in different positions, increment 'cows'.
            } else if (secretCode.contains(String.valueOf(guess.charAt(i)))) {
                cows++;
            }
        }

    }

    // Check whether either player has won the game
    public boolean hasWon() throws IOException {
        if (((bulls == 4))) {
            // Print message to console if entering guesses manually
            if (isManual) {
                System.out.println(winningMessage);
                // Otherwise, print message to the output file
            } else {
                PlayGame.gameIO.writeToOutput('\n' + winningMessage);
            }
            // Game has been won.
            return true;
        } else {
            // If the game is still live (no one has won yet).
            return false;
        }
    }

    // Write the top of the result file
    public void writeFileHeader() throws IOException {
        PlayGame.gameIO.writeToOutput(fileMessage + secretCode);
    }

    // Condense the possible guesses based on the number of bulls and cows obtained from the last guess.
    public void refineGuessOptions(int bulls, int cows) {
    }


}









