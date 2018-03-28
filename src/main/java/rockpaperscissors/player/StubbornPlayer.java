package rockpaperscissors.player;

import rockpaperscissors.logic.Player;
import rockpaperscissors.logic.Shape;

import java.util.Random;

/**
 * A rock-paper-scissors player that plays whatever shape would have beaten the opponent in the previous round.
 * On the first round, a random shape is played.
 */
public class StubbornPlayer implements Player {

    private Shape lastOpponentShape;

    @Override
    public Shape play() {
        if (lastOpponentShape == null) {
            var shapes = Shape.values();
            return shapes[new Random().nextInt(shapes.length)];
        }
        return lastOpponentShape.getDefeatedBy();
    }

    @Override
    public void take(Shape shape) {
        lastOpponentShape = shape;
    }
}
