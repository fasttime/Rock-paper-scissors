package rockpaperscissors.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static rockpaperscissors.logic.RoundResult.*;
import static rockpaperscissors.logic.Shape.*;

class RoundTest {

    @Test
    void getShape1() {
        assertEquals(ROCK, Round.of(ROCK, PAPER).getShape1());
        assertEquals(PAPER, Round.of(PAPER, SCISSORS).getShape1());
        assertEquals(SCISSORS, Round.of(SCISSORS, ROCK).getShape1());
    }

    @Test
    void getShape2() {
        assertEquals(ROCK, Round.of(PAPER, ROCK).getShape2());
        assertEquals(PAPER, Round.of(SCISSORS, PAPER).getShape2());
        assertEquals(SCISSORS, Round.of(ROCK, SCISSORS).getShape2());
    }

    @Test
    void player1Wins() {
        assertEquals(PLAYER1_WINS, Round.of(PAPER, ROCK).getResult());
        assertEquals(PLAYER1_WINS, Round.of(SCISSORS, PAPER).getResult());
        assertEquals(PLAYER1_WINS, Round.of(ROCK, SCISSORS).getResult());
    }

    @Test
    void player2Wins() {
        assertEquals(PLAYER2_WINS, Round.of(ROCK, PAPER).getResult());
        assertEquals(PLAYER2_WINS, Round.of(PAPER, SCISSORS).getResult());
        assertEquals(PLAYER2_WINS, Round.of(SCISSORS, ROCK).getResult());
    }

    @Test
    void draw() {
        assertEquals(DRAW, Round.of(ROCK, ROCK).getResult());
        assertEquals(DRAW, Round.of(PAPER, PAPER).getResult());
        assertEquals(DRAW, Round.of(SCISSORS, SCISSORS).getResult());
    }

    @Test
    void roundOfCachesRounds() {
        assertSame(Round.of(ROCK, PAPER), Round.of(ROCK, PAPER));
    }

    @Test
    void roundOfThrowsNPE() {
        assertThrows(NullPointerException.class, () -> Round.of(null, ROCK));
        assertThrows(NullPointerException.class, () -> Round.of(ROCK, null));
    }
}