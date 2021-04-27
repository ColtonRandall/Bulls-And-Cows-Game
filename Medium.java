package a1_bullsAndCows;

import java.util.ArrayList;

public class Medium extends Computer {


    // Create new arrayList to store computer's guesses
    public ArrayList<String> computerGuessList = new ArrayList<>();


    // -------------------- GET GUESS -----------------
    @Override
    public String getGuess() {
        // Store a random 4-digit number as 'guess'
        String guess = randomNumberGenerator();

        /* If the list contains the above 4-digit number, generate a
            new number. Prevents repeated guesses.
         */
        while (computerGuessList.contains(guess)) {
            guess = randomNumberGenerator();
        }

        /* Otherwise, we want to add our guess to the list
            for the computer to remember for future guesses
         */
        computerGuessList.add(guess);

        return guess;
    }

}
