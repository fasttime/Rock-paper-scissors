package rockpaperscissors.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static rockpaperscissors.logic.Shape.*;

class AlternatingPlayerTest {

    @Test
    void play() {
        AlternatingPlayer player = new AlternatingPlayer();

        assertEquals(ROCK, player.play());
        assertAll(() -> player.take(PAPER));
        assertEquals(PAPER, player.play());
        assertAll(() -> player.take(SCISSORS));
        assertEquals(SCISSORS, player.play());
        assertAll(() -> player.take(PAPER));
        assertEquals(ROCK, player.play());
        assertAll(() -> player.take(SCISSORS));
        assertEquals(PAPER, player.play());
        assertAll(() -> player.take(ROCK));
        assertEquals(SCISSORS, player.play());
        assertAll(() -> player.take(ROCK));
    }
}