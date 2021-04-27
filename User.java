package a1_bullsAndCows;

import java.io.IOException;

public class User extends Player {

    private final GameIO gameIO = PlayGame.gameIO;

    /*
       Constructor - If the player chooses to enter guesses via reading a file,
       create a new guessing file
    */
    public User() throws IOException {
        if (!Player.isManual) {
            createGuessFile();
        }
        fileMessage = "\nYour code: ";
        winningMessage = "**********\n YOU WIN! :)\n**********";
    }

    // --------------- PLAYER SECRET CODE ----------------
    public String getUserCode() {
        secretCode = Keyboard.readInput();
        return secretCode;
    }

    // --------------- PLAYER GUESS ----------------
    public String getUserGuess() throws IOException {
        String guess;

        // If the player is entering their guesses manually, get the guesses normally.
        if (Player.isManual) {

            System.out.println("---");
            System.out.print("You guess: ");

            while (true) {
                guess = Keyboard.readInput();
                // If user enters an invalid guess, prompt them again.
                if (!guess.matches("^[0-9]*$") || guess.length() != CODE_LENGTH) {

                    System.out.println("Please enter a valid 4-digit guess: ");
                    continue;
                }
                // If the guess is valid, break out and continue the game.
                break;
            }

        /*
          If the user has chosen to enter their guesses via file-reading, set the guesses to be read line-by-line by
          the BufferedReader set up in GameIO class.
        */
        } else {
            guess = gameIO.getGuess();
        }

        return guess;
    }


    // --------------- VALID CODE CHECK ----------------
    @Override
    public void checkValidCode() {

        validCode:
        while (true) {

            // Check whether the user has entered a valid 4-digit code containing ONLY digits
            if (!secretCode.matches("^[0-9]*$") || secretCode.length() != CODE_LENGTH) {
                System.out.println("---");
                System.out.println("Please enter a valid 4-digit code: ");

                // If the code is invalid, prompt the user to re-enter a valid code
                getUserCode();
            } else {
                break validCode;
            }
        }
    }

    public void createGuessFile() throws IOException {
        // Clear file for every game
        gameIO.refreshGuessFile();
        for (int i = 0; i < Player.GUESS_LIMIT; i++) {

            System.out.println("Please enter guess " + String.valueOf(i + 1) + ": ");
            while (true) {
                guess = Keyboard.readInput();
                // If user enters an invalid guess, prompt them again
                if (!guess.matches("^[0-9]*$") || guess.length() != CODE_LENGTH) {

                    System.out.println("Please enter a valid 4-digit guess: ");
                    continue;
                }
                break;
            }
            // Write each of the user's guesses to a file, all on a new line
            gameIO.writeGuessToFile(guess);
        }
        // Close the writer and start the reader
        gameIO.setupGuessFileReader();
    }

    @Override
    public void printResult(int turnNum, String computerSecretCode) throws IOException {

        getBullsAndCows(guess, computerSecretCode);

        if (Player.isManual) {
            // Print the total bulls and cows after each guess
            System.out.println("Result: " + bulls + " bulls and " + cows + " cows\n");

        } else {
            // send formatted thing to file
            String turnResult = "\nTurn " + turnNum + ":\nYou guessed " + this.guess +
                    ", scoring " + bulls + " bulls and " + cows + " cows";

            gameIO.writeToOutput(turnResult);
        }
    }

}







