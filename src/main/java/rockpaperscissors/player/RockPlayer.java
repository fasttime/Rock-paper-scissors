package rockpaperscissors.player;

import rockpaperscissors.logic.Player;
import rockpaperscissors.logic.Shape;

import static rockpaperscissors.logic.Shape.ROCK;

/**
 * A rock-paper-scissors player that always plays rock.
 */
public class RockPlayer implements Player {

    @Override
    public Shape play() {
        return ROCK;
    }
}
