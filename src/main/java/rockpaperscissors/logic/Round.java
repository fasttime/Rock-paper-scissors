package rockpaperscissors.logic;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import static rockpaperscissors.logic.RoundResult.*;

/**
 * A round of rock-paper-scissors, as identified by the shapes played by each player.
 */
public final class Round {

    private static final Map<Shape, Map<Shape, Round>> roundMap = new EnumMap<>(Shape.class);
    private final Shape shape1;
    private final Shape shape2;
    private RoundResult result;

    private Round(Shape shape1, Shape shape2) {
        this.shape1 = shape1;
        this.shape2 = shape2;
    }

    /**
     * Obtains an instance of the round specified by a pair of shapes.
     *
     * @param shape1 The shape played by the first player.
     * @param shape2 The shape played by the second player.
     * @return An instance of a round for the specified shapes.
     * @throws NullPointerException if either shape is {@code null}.
     */
    public static Round of(Shape shape1, Shape shape2) {
        Objects.requireNonNull(shape1);
        Objects.requireNonNull(shape2);
        synchronized (roundMap) {
            return
                    roundMap
                            .computeIfAbsent(shape1, shape -> new EnumMap<>(Shape.class))
                            .computeIfAbsent(shape2, shape -> new Round(shape1, shape2));
        }
    }

    /**
     * Retrieves the shape played by the first player.
     *
     * @return The shape played by the first player.
     */
    public Shape getShape1() {
        return shape1;
    }

    /**
     * Retrieves the shape played by the second player.
     *
     * @return The shape played by the second player.
     */
    public Shape getShape2() {
        return shape2;
    }

    /**
     * Retrieves the result of the current round.
     *
     * @return A constant indicating the result of the current round.
     */
    public RoundResult getResult() {
        if (result == null) {
            if (shape1 == shape2)
                result = DRAW;
            else
                result = shape1 == shape2.getDefeatedBy() ? PLAYER1_WINS : PLAYER2_WINS;
        }
        return result;
    }
}
