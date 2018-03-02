package rockpaperscissors.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static rockpaperscissors.logic.Shape.ROCK;

class RandomPlayerTest {

    @Test
    void play() {
        RandomPlayer player = new RandomPlayer();

        for (int i = 0; i <= 10; i++) {
            assertNotNull(player.play());
            assertAll(() -> player.take(ROCK));
        }
    }
}