package rockpaperscissors.player;

import org.junit.jupiter.api.Test;
import rockpaperscissors.logic.Shape;

import static org.junit.jupiter.api.Assertions.*;

class StubbornPlayerTest {

    @Test
    void play() {
        StubbornPlayer player = new StubbornPlayer();

        assertNotNull(player.play());
        for (Shape opponentShape : Shape.values()) {
            assertAll(() -> player.take(opponentShape));

            Shape actual = player.play();
            Shape expected = opponentShape.getDefeatedBy();

            assertEquals(expected, actual);
        }
    }
}