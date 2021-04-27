package a1_bullsAndCows;

import java.io.IOException;


public class PlayGame {


    // --------------- INSTANCE VARIABLES ----------------
    public int computerDifficulty;
    boolean gameWasWon = false;


    // --------------- OBJECTS ----------------
    // instantiate new User object and set as player 1
    public User player1;
    // instantiate new Computer object to be player 2 - set to null initially. Implement when user selects difficulty.
    public Computer player2 = null;
    // Create new gameIO object
    public static GameIO gameIO = new GameIO();


    // --------------- START ----------------
    public void start() throws IOException {

        // ---------- Welcome the player ----------
        welcome();

        // ---------- User guesses to be read from file or guess manually ----------
        System.out.println("Enter your guesses manually or read from a file?\n1.Manual\n2.File");
        // Store user's response in a variable
        int userResponse = Integer.parseInt(Keyboard.readInput());
        // If userResponse is anything but 2, the user will enter their guesses manually
        Player.isManual = (userResponse != 2);

        // create new User object called player 1.
        player1 = new User();

        // ---------- GET DIFFICULTY LEVEL ----------
        computerDifficulty = getDifficultyLevel();

        // --- EASY ---
        if (computerDifficulty == 1) {

            System.out.println("\nEasy mode selected!\n*** GOOD LUCK! ***\n");
            // Computer is the default Easy mode
            player2 = new Computer();
            game();

            // --- MEDIUM ---
        } else if (computerDifficulty == 2) {
            player2 = new Medium();
            System.out.println("\nMedium mode selected!\n*** GOOD LUCK! ***\n");
            game();

            // --- HARD ---
        } else if (computerDifficulty == 3) {
            player2 = new Hard();
            System.out.println("\nHard mode selected!\n*** GOOD LUCK! ***\n");
            game();

            // If anything else in entered, initiate the default easy mode
        } else {
            System.out.println("Invalid entry, initiating easy mode... ");
            player2 = new Computer();
            game();
        }
    }

    //    ----------------------- METHODS ----------------------------

    //    ----------------------- WELCOME  --------------------------
    public void welcome() {
        System.out.println("\n=========================\nWelcome to Bulls and Cows\n=========================\n");
        System.out.println("What is your name?: ");
        String userName = Keyboard.readInput();
        System.out.println("Hello " + userName + ". You are Player 1.\n");
    }

    //    -------------------- DIFFICULTY LEVEL --------------------------
    public int getDifficultyLevel() {
        System.out.println("***** DIFFICULTY LEVEL *****");
        System.out.println("\t1. Easy\n\t2. Medium\n\t3. Hard");
        System.out.print("Please enter your preferred difficulty: ");
        computerDifficulty = Integer.parseInt(Keyboard.readInput());
        return computerDifficulty;
    }


    //    ----------------------- GAME -------------------------
    public void game() throws IOException {

        // -------------- SET PLAYER CODE--------------
        // Prompt user for their secret code
        System.out.println("---");
        System.out.print("Player 1, please enter your secret code: ");

        // Set user's code and make sure it is valid
        player1.setSecretCode(player1.getUserCode());
        player1.checkValidCode();


        // -------------- SET COMPUTER CODE --------------
        // Get computer to randomly generate a 4-digit code
        player2.setSecretCode(player2.randomNumberGenerator());

        System.out.println("Player 2 has entered their secret code.\n");


        // -------------- GAME BEGINS --------------

        /* Determine how the game is played based on whether the user is entering their
            guesses manually or through file input
        */
        if (Player.isManual) {
            System.out.println("\n*** You will guess first! ***");
        } else {

            // ----- Construct the output file -----
            // Clear file
            gameIO.refreshOutputFile();
            // Write the top line
            gameIO.writeToOutput("Bulls & Cows game result.");
            // Write the two headers containing each of the secret codes
            player1.writeFileHeader();
            player2.writeFileHeader();
            // separate each turn
            gameIO.writeToOutput("\n---");
        }


        // Play the game. Each player gets 7 guesses. Quit the game once guesses run out.
        for (int i = 1; i <= Player.GUESS_LIMIT; i++) {

            // ---------- User's turn ----------
            player1.setGuess(player1.getUserGuess());
            player1.printResult(i, player2.getSecretCode());

            // Check if user has won
            if (player1.hasWon()) {
                gameWasWon = true;
                break;
            }

            // ---------- Computer's turn ----------
            player2.setGuess(player2.randomNumberGenerator());
            player2.printResult(i, player1.getSecretCode());


            if (computerDifficulty == 3) {
                // Hard mode functionality
                player2.refineGuessOptions(player2.bulls, player2.cows);
            }

            // Check if computer has won
            if (player2.hasWon()) {
                gameWasWon = true;
                break;
            }
        }

        /*
         If neither player has won the game, indicate that the game is over and it is a draw. Do this according to
         which game style the user is playing in.
        */
        if (!gameWasWon) {
            if (Player.isManual) {
                // Guesses have run out - quit the game and announce a draw
                System.out.println("---");
                System.out.println("********** No more guesses remaining. It's a DRAW! **********");
            } else {
                gameIO.writeToOutput("---\n********** No more guesses remaining. It's a DRAW! **********");
            }
        }
    }


    // -------------------- MAIN -----------------
    public static void main(String[] args) throws IOException {
        PlayGame playGame = new PlayGame();
        playGame.start();
    }

}
