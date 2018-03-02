package rockpaperscissors.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static rockpaperscissors.logic.Shape.*;

class ShapeTest {

    @Test
    void rockIsDefeatedByPaper() {
        assertEquals(PAPER, ROCK.getDefeatedBy());
    }

    @Test
    void paperIsDefeatedByScissors() {
        assertEquals(SCISSORS, PAPER.getDefeatedBy());
    }

    @Test
    void scissorsIsDefeatedByRock() {
        assertEquals(ROCK, SCISSORS.getDefeatedBy());
    }
}
