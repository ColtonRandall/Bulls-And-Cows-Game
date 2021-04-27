package a1_bullsAndCows;

import java.io.*;


public class GameIO {

    // --------------- INSTANCE VARIABLES ----------------
    // Create two files to store both guesses and the resulting output file
    private File guessFile = new File("UserGuesses.txt");
    private File resultFile = new File("BullsAndCowsGame.txt");
    // Create the readers and writers
    private BufferedReader guessReader;
    private BufferedWriter resultWriter;
    private BufferedWriter guessWriter;


    // --------------- CLEAR GUESS FILE ----------------
    public void refreshGuessFile() throws IOException {
        if (guessFile.exists()) {
            // If we can't delete the result file, throw an exception
            if (!guessFile.delete()) {
                throw new IOException("Unable to delete guess file");
            }
        }
        /* Create a new FileWrite wrapped within a BufferedWriter
            each time the file is refreshed.
        */
        guessWriter = new BufferedWriter(new FileWriter(guessFile, true));

    }

    // --------------- CLEAR OUTPUT FILE ----------------
    public void refreshOutputFile() throws IOException {
        if (resultFile.exists()) {
            // If we can't clear the result file, throw an exception
            if (!resultFile.delete()) {
                throw new IOException("Unable to delete result file");
            }
        }
    }


    // --------------- WRITE GUESS FILE ----------------
    public void writeGuessToFile(String guess) throws IOException {
        // Write each guess to the file on a new line
        guessWriter.write(guess + '\n');
    }


    // --------------- CREATE FILE READER ----------------
    public void setupGuessFileReader() throws IOException {
        // Close the writer
        guessWriter.close();
        // Create a new reader
        guessReader = new BufferedReader(new FileReader(guessFile));
    }


    // --------------- WRITE OUTPUT FILE ----------------
    public void writeToOutput(String outputMessage) throws IOException {
        // Create a new FileWriter that takes in the result file
        resultWriter = new BufferedWriter(new FileWriter(resultFile, true));
        // Write the parameterised output message to the result file
        resultWriter.write(outputMessage);
        // Close the writer
        resultWriter.close();
    }


    // --------------- READ GUESSES ----------------
    public String getGuess() throws IOException {
        /*
            The newly constructed file should then be read, line
            by line for each guess.
        */
        String guess = guessReader.readLine();
        if (guess == null) {
            throw new IOException("Not enough guesses in file!");
        }
        return guess;
    }
}