package rockpaperscissors.player;

import org.junit.jupiter.api.Test;
import rockpaperscissors.logic.Shape;

import static org.junit.jupiter.api.Assertions.*;

class StubbornPlayerTest {

    @Test
    void play() {
        var player = new StubbornPlayer();

        assertNotNull(player.play());
        for (var opponentShape : Shape.values()) {
            assertAll(() -> player.take(opponentShape));

            var actual = player.play();
            var expected = opponentShape.getDefeatedBy();

            assertEquals(expected, actual);
        }
    }
}
