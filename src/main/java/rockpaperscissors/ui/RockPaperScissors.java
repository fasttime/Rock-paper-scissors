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
            new Strategy[] {
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
        var pattern = getString("PlayerPlaysShape");
        var playerName = getString(playerKey);
        var shapeName = getString("Shape/" + shape.name());
        return MessageFormat.format(pattern, playerName, shapeName);
    }

    private static String formatResultText(RoundResult roundResult) {
        if (roundResult == DRAW)
            return getString("ItsADraw");
        var pattern = getString("PlayerWins");
        var winnerName = getString(roundResult == PLAYER1_WINS ? PLAYER1_KEY : PLAYER2_KEY);
        return MessageFormat.format(pattern, winnerName);
    }

    private static String formatVictoryText(String playerKey, Integer resultCount) {
        var pattern = getString("VictoriesForPlayer");
        var playerName = getString(playerKey);
        return MessageFormat.format(pattern, resultCount, playerName);
    }

    private static String formatDrawText(int resultCount) {
        var pattern = getString("Draws");
        return MessageFormat.format(pattern, resultCount);
    }

    private static String getString(String key) {
        return textBundle.getString(key);
    }

    void runGame() {
        var player1 = inputPlayer(PLAYER1_KEY);
        var player2 = inputPlayer(PLAYER2_KEY);
        var roundsToPlay = inputRoundsToPlay();
        var game = new Game(player1, player2, roundsToPlay);
        var rounds = game.getRounds();
        for (var roundNr = 1; roundNr <= roundsToPlay; roundNr++)
            printRoundReport(roundNr, rounds[roundNr - 1]);
        printGameReport(game);
    }

    Player inputPlayer(String playerKey) {
        var scanner = new Scanner(in);
        var count = strategies.length;
        var pattern = getString("ChooseStrategyForPlayer");
        var playerName = getString(playerKey);
        var format = MessageFormat.format(pattern, playerName) + " (1 - %d): ";
        for (; ; ) {
            for (var index = 1; index <= count; index++) {
                var description = getString("StrategyDescription/" + strategies[index - 1].getDescriptionKey());
                out.printf("%d - %s%n", index, description);
            }
            out.printf(format, count);
            if (scanner.hasNextInt()) {
                var index = scanner.nextInt();
                if (index >= 1 && index <= count)
                    return strategies[index - 1].createPlayer();
            } else
                scanner.next();
        }
    }

    int inputRoundsToPlay() {
        var scanner = new Scanner(in);
        var format = getString("NumberOfRounds") + " (%d - %d): ";
        for (; ; ) {
            out.printf(format, MIN_ROUNDS, MAX_ROUNDS);
            if (scanner.hasNextInt()) {
                var roundsToPlay = scanner.nextInt();
                if (roundsToPlay >= MIN_ROUNDS && roundsToPlay <= MAX_ROUNDS)
                    return roundsToPlay;
            } else
                scanner.next();
        }
    }

    void printRoundReport(int roundNr, Round round) {
        var pattern = getString("Round");
        var title = MessageFormat.format(pattern, roundNr);
        out.printf("%n%s%n", title);
        out.println(formatShapeText(PLAYER1_KEY, round.getShape1()));
        out.println(formatShapeText(PLAYER2_KEY, round.getShape2()));
        out.println(formatResultText(round.getResult()));
    }

    void printGameReport(Game game) {
        var title = getString("GameResults");
        out.printf("%n%s%n", title);
        out.println(formatVictoryText(PLAYER1_KEY, game.countResults(PLAYER1_WINS)));
        out.println(formatVictoryText(PLAYER2_KEY, game.countResults(PLAYER2_WINS)));
        out.println(formatDrawText(game.countResults(DRAW)));
    }
}
