package rockpaperscissors.ui;

import rockpaperscissors.logic.*;
import rockpaperscissors.player.AlternatingPlayer;
import rockpaperscissors.player.RandomPlayer;
import rockpaperscissors.player.RockPlayer;
import rockpaperscissors.player.StubbornPlayer;

import java.io.InputStream;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.Scanner;

import static rockpaperscissors.logic.Game.MAX_ROUNDS;
import static rockpaperscissors.logic.Game.MIN_ROUNDS;
import static rockpaperscissors.logic.RoundResult.*;

/**
 * Exposes the main method and provides a text-based interface for the application.
 *
 * @implSpec This class is part of the user interface and not intended to be accessed by external code.
 */
class RockPaperScissors {

    static final String PLAYER1_KEY = "Player1";
    static final String PLAYER2_KEY = "Player2";
    private static final ResourceBundle textBundle = ResourceBundle.getBundle("text");
    private static final Strategy[] strategies =
            new Strategy[]{
                    new Strategy(RandomPlayer::new, "Random"),
                    new Strategy(RockPlayer::new, "Rock"),
                    new Strategy(AlternatingPlayer::new, "Alternating"),
                    new Strategy(StubbornPlayer::new, "Stubborn"),
            };

    private final InputStream in;
    private final PrintStream out;

    RockPaperScissors(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    @SuppressWarnings("squid:S106")
    public static void main(String[] args) {
        new RockPaperScissors(System.in, System.out).runGame();
    }

    private static String formatShapeText(String playerKey, Shape shape) {
        String playerName = textBundle.getString(playerKey);
        String shapeName = textBundle.getString("Shape/" + shape.name());
        return MessageFormat.format(textBundle.getString("PlayerPlaysShape"), playerName, shapeName);
    }

    private static String formatResultText(RoundResult roundResult) {
        if (roundResult == DRAW)
            return textBundle.getString("ItsADraw");
        String winnerName = textBundle.getString(roundResult == PLAYER1_WINS ? PLAYER1_KEY : PLAYER2_KEY);
        return MessageFormat.format(textBundle.getString("PlayerWins"), winnerName);
    }

    private static String formatVictoryText(String playerKey, Integer resultCount) {
        String pattern = textBundle.getString("VictoriesForPlayer");
        String playerName = textBundle.getString(playerKey);
        return MessageFormat.format(pattern, resultCount, playerName);
    }

    private static String formatDrawText(int resultCount) {
        String pattern = textBundle.getString("Draws");
        return MessageFormat.format(pattern, resultCount);
    }

    void runGame() {
        Player player1 = inputPlayer(PLAYER1_KEY);
        Player player2 = inputPlayer(PLAYER2_KEY);
        int roundsToPlay = inputRoundsToPlay();
        Game game = new Game(player1, player2, roundsToPlay);
        Round[] rounds = game.getRounds();
        for (int roundNr = 1; roundNr <= roundsToPlay; roundNr++)
            printRoundReport(roundNr, rounds[roundNr - 1]);
        printGameReport(game);
    }

    Player inputPlayer(String playerKey) {
        Scanner scanner = new Scanner(in);
        int count = strategies.length;
        String pattern = textBundle.getString("ChooseStrategyForPlayer");
        String playerName = textBundle.getString(playerKey);
        String format = MessageFormat.format(pattern, playerName) + " (1 - %d): ";
        for (; ; ) {
            for (int index = 1; index <= count; index++) {
                String description = textBundle.getString("StrategyDescription/" + strategies[index - 1].getDescriptionKey());
                out.printf("%d - %s%n", index, description);
            }
            out.printf(format, count);
            if (scanner.hasNextInt()) {
                int index = scanner.nextInt();
                if (index >= 1 && index <= count)
                    return strategies[index - 1].createPlayer();
            } else
                scanner.next();
        }
    }

    int inputRoundsToPlay() {
        Scanner scanner = new Scanner(in);
        String pattern = textBundle.getString("NumberOfRounds");
        String format = pattern + " (%d - %d): ";
        for (; ; ) {
            out.printf(format, MIN_ROUNDS, MAX_ROUNDS);
            if (scanner.hasNextInt()) {
                int roundsToPlay = scanner.nextInt();
                if (roundsToPlay >= MIN_ROUNDS && roundsToPlay <= MAX_ROUNDS)
                    return roundsToPlay;
            } else
                scanner.next();
        }
    }

    void printRoundReport(int roundNr, Round round) {
        String title = MessageFormat.format(textBundle.getString("Round"), roundNr);
        out.printf("%n%s%n", title);
        out.println(formatShapeText(PLAYER1_KEY, round.getShape1()));
        out.println(formatShapeText(PLAYER2_KEY, round.getShape2()));
        out.println(formatResultText(round.getResult()));
    }

    void printGameReport(Game game) {
        String title = textBundle.getString("GameResults");
        out.printf("%n%s%n", title);
        out.println(formatVictoryText(PLAYER1_KEY, game.countResults(PLAYER1_WINS)));
        out.println(formatVictoryText(PLAYER2_KEY, game.countResults(PLAYER2_WINS)));
        out.println(formatDrawText(game.countResults(DRAW)));
    }
}
