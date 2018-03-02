package rockpaperscissors.player;

import rockpaperscissors.logic.Player;
import rockpaperscissors.logic.Shape;

import static rockpaperscissors.logic.Shape.SCISSORS;

/**
 * A rock-paper-scissors player that alternatingly plays rock, paper, and scissors.
 */
public class AlternatingPlayer implements Player {

    private Shape shape = SCISSORS;

    @Override
    public Shape play() {
        shape = shape.getDefeatedBy();
        return shape;
    }
}
