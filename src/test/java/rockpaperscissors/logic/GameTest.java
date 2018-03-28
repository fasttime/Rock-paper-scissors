package rockpaperscissors.logic;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static rockpaperscissors.logic.RoundResult.*;
import static rockpaperscissors.logic.Shape.*;

class GameTest {

    private static Player mockPlayer(Shape shape, Shape... shapes) {
        var player = mock(Player.class);
        when(player.play()).thenReturn(shape, shapes);
        return player;
    }

    @Test
    void constructorCallsPlayAndTakeOnPlayers() {
        var player1 = mockPlayer(ROCK, PAPER, SCISSORS);
        var player2 = mockPlayer(SCISSORS, PAPER, ROCK);
        new Game(player1, player2, 3);

        verify(player1, times(3)).play();
        var shapeCaptor1 = ArgumentCaptor.forClass(Shape.class);
        verify(player1, times(3)).take(shapeCaptor1.capture());
        assertEquals(Arrays.asList(SCISSORS, PAPER, ROCK), shapeCaptor1.getAllValues());

        verify(player2, times(3)).play();
        var shapeCaptor2 = ArgumentCaptor.forClass(Shape.class);
        verify(player2, times(3)).take(shapeCaptor2.capture());
        assertEquals(Arrays.asList(ROCK, PAPER, SCISSORS), shapeCaptor2.getAllValues());
    }

    @Test
    void constructorThrowsNPEIfEitherPlayerIsNull() {
        assertThrows(NullPointerException.class, () -> new Game(null, mockPlayer(ROCK), 1));
        assertThrows(NullPointerException.class, () -> new Game(mockPlayer(ROCK), null, 1));
    }

    @Test
    void constructorThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Game(mockPlayer(ROCK), mockPlayer(ROCK), 0));
        assertThrows(IllegalArgumentException.class, () -> new Game(mockPlayer(ROCK), mockPlayer(ROCK), 101));
    }

    @Test
    void constructorThrowsNPEIfPlayer1PlaysNull() {
        var player1 = mockPlayer(null);
        var player2 = mockPlayer(ROCK);
        assertThrows(NullPointerException.class, () -> new Game(player1, player2, 100));
        verify(player1).play();
        verify(player2).play();
        verifyNoMoreInteractions(player1, player2);
    }

    @Test
    void constructorThrowsNPEIfPlayer2PlaysNull() {
        var player1 = mockPlayer(ROCK);
        var player2 = mockPlayer(null);
        assertThrows(NullPointerException.class, () -> new Game(player1, player2, 100));
        verify(player1).play();
        verify(player2).play();
        verifyNoMoreInteractions(player1, player2);
    }

    @Test
    void getRounds() {
        var roundsToPlay = 42;
        var rounds = new Game(mockPlayer(ROCK), mockPlayer(PAPER), roundsToPlay).getRounds();

        assertEquals(roundsToPlay, rounds.length);
        assertEquals(Round.of(ROCK, PAPER), rounds[0]);
    }

    @Test
    void countResults() {
        var game = new Game(mockPlayer(ROCK), mockPlayer(SCISSORS, SCISSORS, PAPER, SCISSORS, PAPER, ROCK), 6);

        assertEquals(3, game.countResults(PLAYER1_WINS));
        assertEquals(2, game.countResults(PLAYER2_WINS));
        assertEquals(1, game.countResults(DRAW));
        assertThrows(NullPointerException.class, () -> game.countResults(null));
    }
}
