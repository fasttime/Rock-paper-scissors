package rockpaperscissors.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static rockpaperscissors.logic.Shape.ROCK;

class RockPlayerTest {

    @Test
    void play() {
        var player = new RockPlayer();

        for (var i = 0; i <= 10; i++) {
            assertEquals(ROCK, player.play());
            assertAll(() -> player.take(ROCK));
        }
    }
}
