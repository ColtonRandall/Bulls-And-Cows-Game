package a1_bullsAndCows;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Computer extends Player {

    /*
     Constructor - for every computer object initialised, create two messages that can be displayed in the console
     or on the output file.
    */
    public Computer() {
        fileMessage = "\nComputer's code: ";
        winningMessage = "***************\n COMPUTER WINS!\n***************";
    }

    /*
        Generate random 4-digit number which can be used as the computer's
        secret code and guesses
    */
    public String randomNumberGenerator() {

        // Create a list of numbers stored as String datatype
        List<String> numbers = new ArrayList<>();
        // Add numbers 0 - 9 to list as String values
        for (int i = 0; i < 10; i++) {
            numbers.add(String.valueOf(i));
        }

        // Randomly shuffle the numbers array
        Collections.shuffle(numbers);
        // Instantiate a number string variable where the 4-digit number will be stored
        String randomNums = "";

        /*  Retrieve first 4 numbers of shuffled list and set
            as the computer's secret code
         */
        for (int i = 0; i < 4; i++) {
            randomNums += numbers.get(i);
        }
        // Return the 4-digit number to be used as a secret code or guess
        return randomNums;
    }


    /*
     Implement the printResult method for Computer to output a specific message depending on which game mode the
     user is playing in. This message is to be displayed after each guess / turn.
    */
    @Override
    public void printResult(int turnNum, String userSecretCode) throws IOException {
        getBullsAndCows(guess, userSecretCode);

        // Entering guesses manually
        if (Player.isManual) {
            System.out.println("Computer guessed: " + getLastGuess());
            // Print the total bulls and cows after each guess
            System.out.println("Result: " + bulls + " bulls and " + cows + " cows");

            // Entering guesses via file input
        } else {
            // Format the message to be computer-specific and write it to the output file.
            String turnResult =
                    "\nComputer guessed " + guess + ", scoring " + bulls + " bulls and " + cows +
                            " cows\n---";
            PlayGame.gameIO.writeToOutput(turnResult);
        }
    }
}



