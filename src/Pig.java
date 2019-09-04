import java.util.Random;
import java.util.Scanner;

import static java.lang.System.*;

/*
 * The Pig game
 * See http://en.wikipedia.org/wiki/Pig_%28dice_game%29
 *
 */
public class Pig {

    public static void main(String[] args) {
        new Pig().program();
    }

    // The only allowed instance variables (i.e. declared outside any method)
    // Accessible from any method
    final Scanner sc = new Scanner(in);
    final Random rand = new Random();

    void program() {
        //test();                 // <-------------- Uncomment to run tests!

        final int winPts = 20;    // Points to win (decrease if testing)
        Player[] players;         // The players (array of Player objects)
        Player currentPlayer;            // Current player for round (must use)
        boolean aborted = false;   // Game aborted by player?

        printWelcomeMsg(winPts);

        players = getPlayers();   // Hard coded players, replace *last* of all with ... (see below)
        currentPlayer = getRandomPlayer(players);

        while (true) {

            printStatusMsg(players);

            String input = getPlayerChoice(currentPlayer);

            if (input.equalsIgnoreCase("r")) {
                // Roll the dice! - Swap player if 1 is rolled.

                int roll = rollDice();
                currentPlayer.roundPts += roll;

                if (roll == 1) {

                    currentPlayer.roundPts = 0;
                    printRoundMsg(roll, currentPlayer);
                    currentPlayer = getNextPlayer(currentPlayer, players);

                } else {

                    printRoundMsg(roll, currentPlayer);
                }

                if (currentPlayer.totalPts + currentPlayer.roundPts >= winPts) { break; }

            } else if (input.equalsIgnoreCase("n")) {
                // Next Player - Save round points and swap player

                currentPlayer.totalPts += currentPlayer.roundPts;
                currentPlayer.roundPts = 0;
                currentPlayer = getNextPlayer(currentPlayer, players);

            } else if (input.equalsIgnoreCase("q")) {
                // Game Aborted!

                aborted = true;
                break;
            }

        }

        // TODO Game logic, using small step, functional decomposition


        printGameOverMsg(currentPlayer, aborted);
    }

    // ---- Game logic methods --------------

    // TODO

    // ---- IO methods ------------------

    void printWelcomeMsg(int winPoints) {
        out.println("Welcome to PIG!");
        out.println("First player to get " + winPoints + " points will win!");
        out.println("Commands are: r = roll , n = next, q = quit");
        out.println();
    }

    void printStatusMsg(Player[] players) {
        out.print("Points: ");
        for (int i = 0; i < players.length; i++) {
            out.print(players[i].name + " = " + players[i].totalPts + " ");
        }
        out.println();
    }

    void printRoundMsg(int result, Player current) {
        if (result > 1) {
            out.println("Got " + result + " running total are " + current.roundPts);
        } else {
            out.println("Got 1 lost it all!");
        }
    }

    void printGameOverMsg(Player player, boolean aborted) {
        if (aborted) {
            out.println("Aborted");
        } else {
            out.println("Game over! Winner is player " + player.name + " with "
                    + (player.totalPts + player.roundPts) + " points");
        }
    }

    String getPlayerChoice(Player player) {
        out.print("Player is " + player.name + " > ");
        return sc.next();
    }

    Player getRandomPlayer(Player[] players) {

        int randNr = rand.nextInt(players.length);

        return players[randNr];
    }

    Player getNextPlayer(Player previousPlayer, Player[] players) {

        int newIndex = previousPlayer.playerIndex + 1;

        if (newIndex >= players.length) {

            newIndex = 0;
        }

        return players[newIndex];
    }

    int rollDice() {

        return rand.nextInt(6) + 1;
    }

    Player[] getPlayers() {

        out.print("How many players? > ");
        int playersCount = sc.nextInt();

        Player[] players = new Player[playersCount];

        for (int i  = 1; i <= playersCount; i++) {

            out.print("Name for player " + i + " > ");
            String name = sc.next();

            Player player = new Player(name, i - 1);
            players[i - 1] = player;
        }

        return players;
    }

    // ---------- Class -------------
    // Class representing the concept of a player
    // Use the class to create (instantiate) Player objects
    class Player {
        String name;     // Default null
        int totalPts;    // Total points for all rounds, default 0
        int roundPts;    // Points for a single round, default 0

        int playerIndex;

        public Player(String name, int index) {

            this.name = name;
            this.playerIndex = index;
            this.totalPts = 0;
            this.roundPts = 0;

        }

    }

    // ----- Testing -----------------
    // Here you run your tests i.e. call your game logic methods
    // to see that they really work (IO methods not tested here)
    void test() {
        // This is hard coded test data
        // An array of (no name) Players (probably don't need any name to test)
        // Player[] players = {new Player(), new Player(), new Player()};

        // TODO Use for testing of logcial methods (i.e. non-IO methods)

        exit(0);   // End program
    }
}



