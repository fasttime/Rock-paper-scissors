package rockpaperscissors.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static rockpaperscissors.logic.Shape.ROCK;

class RockPlayerTest {

    @Test
    void play() {
        RockPlayer player = new RockPlayer();

        for (int i = 0; i <= 10; i++) {
            assertEquals(ROCK, player.play());
            assertAll(() -> player.take(ROCK));
        }
    }
}