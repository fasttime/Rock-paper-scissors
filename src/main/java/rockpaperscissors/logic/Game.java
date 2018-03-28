package rockpaperscissors.logic;

import java.util.Arrays;
import java.util.Objects;

/**
 * A game of rock-paper-scissors, consisting of one or more rounds played by the same two players.
 */
public class Game {

    /**
     * The minimum number of rounds per game.
     */
    public static final int MIN_ROUNDS = 1;

    /**
     * The maximum number of rounds per game.
     */
    public static final int MAX_ROUNDS = 100;

    private final Round[] rounds;

    /**
     * Creates a new game of rock-paper-scissors by letting two players compete against each other in a specified number
     * of rounds.
     *
     * @param player1      The first player.
     * @param player2      The second player.
     * @param roundsToPlay The number of rounds in the game.
     * @throws NullPointerException     if either player is {@code null}, or if a call to {@link Player#play} unexpectedly returns {@code null}.
     * @throws IllegalArgumentException if {@code roundsToPlay} is not in the range of values between {@link #MIN_ROUNDS} and {@link #MAX_ROUNDS}.
     */
    public Game(Player player1, Player player2, int roundsToPlay) {
        Objects.requireNonNull(player1);
        Objects.requireNonNull(player2);
        if (roundsToPlay < MIN_ROUNDS || roundsToPlay > MAX_ROUNDS)
            throw new IllegalArgumentException();
        rounds = new Round[roundsToPlay];
        for (var roundNr = 1; roundNr <= roundsToPlay; roundNr++) {
            var shape1 = player1.play();
            var shape2 = player2.play();
            var round = Round.of(shape1, shape2);
            player1.take(shape2);
            player2.take(shape1);
            rounds[roundNr - 1] = round;
        }
    }

    /**
     * Retrieves an array of all rounds played.
     *
     * @return An array of all rounds played.
     */
    public Round[] getRounds() {
        return rounds.clone();
    }

    /**
     * Retrieves the number of rounds that ended in a specified result.
     *
     * @param roundResult The result of the rounds to be counted.
     * @return The number of rounds that ended in the specified result.
     * @throws NullPointerException if the specified parameter if {@code null}.
     */
    public int countResults(RoundResult roundResult) {
        Objects.requireNonNull(roundResult);
        return (int) Arrays.stream(rounds).filter(round -> round.getResult() == roundResult).count();
    }
}
