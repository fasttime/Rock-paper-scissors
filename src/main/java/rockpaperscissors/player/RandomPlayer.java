package rockpaperscissors.player;

import rockpaperscissors.logic.Player;
import rockpaperscissors.logic.Shape;

import java.util.Random;

/**
 * A rock-paper-scissors player that chooses shapes pseudorandomly.
 */
public class RandomPlayer implements Player {

    private static final Shape[] shapes = Shape.values();

    private final Random random = new Random();

    @Override
    public Shape play() {
        return shapes[random.nextInt(shapes.length)];
    }
}
