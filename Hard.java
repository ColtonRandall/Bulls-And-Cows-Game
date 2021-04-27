package a1_bullsAndCows;

import java.util.ArrayList;

public class Hard extends Medium {

/*
     The first guess will be randomised, similar to Easy and Medium.
     The result from the first guess will be used as a template for next guesses.
     Whatever number of Bulls and Cows were produced from the previous guess,
     the program should loop through all other possible 4-digit combinations
     that produce that same bulls and cows result, group them together in a list
     and randomly choose the next guess from the new 'refined' list of guess options.
*/


    /*
     Constructor - for every Hard object we create,
     create a list containing all possible number options.
     There should be no duplicate numbers in the code.
    */
    public Hard() {
        // Initialise list similar to that created in medium
        computerGuessList = new ArrayList<>();

        /*
         For every Hard object created, loop through all possible
         4-digit combinations up to 9999, remove combinations that
         contain duplicate numbers and add to ArrayList
        */
        possibleValue:
        for (int i = 0; i < 10000; i++) {
            /*
             Left-pad numbers below 1000 with zeros. '%' = Initiates formatting. '0' = The number to be padded on.
             '4' = How many zeroes to pad. 'd' = Integer.
            */
            String possible = String.format("%04d", Integer.parseInt(Integer.toString(i)));

            /*
             Iterate through each digit in every 4-digit code, compare against other digits in the code to
             determine if there are duplicates present. Use a nested loop - one to iterate through all codes,
             another to iterate through each digit.
            */
            for (int j = 0; j < 4; j++) {
                for (int k = j + 1; k < 4; k++) {
                    //
                    boolean containsDuplicate = Character.compare(possible.charAt(j), possible.charAt(k)) == 0;

                    if (containsDuplicate) {
                        continue possibleValue;
                    }
                }
            }
            computerGuessList.add(possible);
        }
    }

    // -------------------- GET GUESS -----------------
    @Override
    public String getGuess() {

        // Choose a random number in computerGuessList
        int index = (int) (Math.random() * computerGuessList.size());

        guess = computerGuessList.get(index);

        return guess;
    }

    // ------------------ REDUCE GUESS LIST SIZE -----------------
    @Override
    public void refineGuessOptions(int numBulls, int numCows) {

        // Create a new ArrayList - Can be appended onto dynamically.
        ArrayList<String> possibleAnswers = new ArrayList<>();

        // Refer to each guess based on the previous guesses
        String lastGuess = guess;

        // for-each loop to iterate through every possible combination of numbers in the ArrayList
        for (String potentialCode : computerGuessList) {

            // Retrieve the bulls and cows based on the possible code options and previous guess made.
            getBullsAndCows(potentialCode, lastGuess);

            /*
             If the number if bulls and cows are equal in the possible codes and the last guess, add all the codes
             to a new 'refined' list called possibleAnswers.
            */
            if ((numBulls == bulls) && (numCows == cows)) {
                possibleAnswers.add(potentialCode);
            }
        }

        // Remove the guess to prevent the computer from guessing the same code twice.
        possibleAnswers.remove(guess);

        /*
         Set the computerGuessList to equal the new list, containing only codes that yielded the same number of
         bulls and cows as our previous guesses. The new computerGuessList will now be accessible from getGuess().
        */
        computerGuessList = possibleAnswers;
    }


}
