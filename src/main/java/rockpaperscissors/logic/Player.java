package rockpaperscissors.logic;

/**
 * A rock-paper-scissors player.
 */
public interface Player {

    /**
     * Called by the game engine when the player must play a shape.
     *
     * @return The shape played by this player.
     */
    Shape play();

    /**
     * Called by the game engine after a call to {@link #play} to notify the player about the opponent's shape.
     *
     * @param shape The shape played by the opponent.
     */
    default void take(Shape shape) {
        // do nothing by default
    }
}
