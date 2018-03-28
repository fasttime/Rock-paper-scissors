package rockpaperscissors.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static rockpaperscissors.logic.Shape.ROCK;

class RandomPlayerTest {

    @Test
    void play() {
        var player = new RandomPlayer();

        for (var i = 0; i <= 10; i++) {
            assertNotNull(player.play());
            assertAll(() -> player.take(ROCK));
        }
    }
}
