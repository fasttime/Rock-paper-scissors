package rockpaperscissors.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import rockpaperscissors.logic.Game;
import rockpaperscissors.logic.Player;
import rockpaperscissors.logic.Round;
import rockpaperscissors.player.RandomPlayer;
import rockpaperscissors.player.RockPlayer;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static rockpaperscissors.logic.RoundResult.*;
import static rockpaperscissors.logic.Shape.*;
import static rockpaperscissors.ui.RockPaperScissors.PLAYER1_KEY;
import static rockpaperscissors.ui.RockPaperScissors.PLAYER2_KEY;

class RockPaperScissorsTest {

    private OutputStream baos;
    private PrintStream out;

    private static InputStream createInputStream(String... lines) {
        var input = Arrays.stream(lines).map(line -> line + System.lineSeparator()).collect(Collectors.joining());
        return new ByteArrayInputStream(input.getBytes(UTF_8));
    }

    private static void assertContainsLine(String actual, String line) {
        assertTrue(Arrays.asList(actual.split("\\R")).contains(line));
    }

    private static void assertHasLines(String actual, String... lines) {
        assertArrayEquals(lines, actual.split("\\R"));
    }

    @BeforeEach
    void setUp() throws UnsupportedEncodingException {
        baos = new ByteArrayOutputStream();
        out = new PrintStream(baos, false, UTF_8.toString());
    }

    @Test
    void inputPlayerRecognizesValidInput() {
        var in = createInputStream("1");
        var player = new RockPaperScissors(in, out).inputPlayer(PLAYER1_KEY);

        assertEquals(RandomPlayer.class, player.getClass());
        assertTrue(baos.toString().matches("(\\d+ - [^\n]*\\R)+Choose a strategy for Player 1 [^\n]*: $"));
    }

    @Test
    void inputPlayerSkipsInvalidInput() {
        var in = createInputStream("0", "xyz", "2");
        var player = new RockPaperScissors(in, out).inputPlayer(PLAYER2_KEY);

        assertEquals(RockPlayer.class, player.getClass());
        assertTrue(baos.toString().matches("((\\d+ - [^\n]*\\R)+Choose a strategy for Player 2 [^\n]*: ){3}$"));
    }

    @Test
    void inputRoundsToPlayRecognizesValidInput() {
        var in = createInputStream("42");
        var roundsToPlay = new RockPaperScissors(in, out).inputRoundsToPlay();

        assertEquals(42, roundsToPlay);
        assertTrue(baos.toString().matches("Number of rounds.*: $"));
    }

    @Test
    void inputRoundsToPlaySkipsInvalidInput() {
        var in = createInputStream("foo", "666", "", "7");
        var roundsToPlay = new RockPaperScissors(in, out).inputRoundsToPlay();

        assertEquals(7, roundsToPlay);
        assertTrue(baos.toString().matches("(Number of rounds.*: ){3}$"));
    }

    @Test
    void printRoundReportPrintsVictoryForPlayer1() {
        var round = Round.of(ROCK, SCISSORS);
        new RockPaperScissors(null, out).printRoundReport(42, round);

        assertHasLines(baos.toString(), "", "ROUND 42", "Player 1 plays rock.", "Player 2 plays scissors.", "Player 1 wins.");
    }

    @Test
    void printRoundReportPrintsVictoryForPlayer2() {
        var round = Round.of(SCISSORS, ROCK);
        new RockPaperScissors(null, out).printRoundReport(7, round);

        assertHasLines(baos.toString(), "", "ROUND 7", "Player 1 plays scissors.", "Player 2 plays rock.", "Player 2 wins.");
    }

    @Test
    void printRoundReportDraw() {
        var round = Round.of(PAPER, PAPER);
        new RockPaperScissors(null, out).printRoundReport(99, round);

        assertHasLines(baos.toString(), "", "ROUND 99", "Player 1 plays paper.", "Player 2 plays paper.", "It's a draw.");
    }

    @Test
    void printGameReportPrintsVictoryForPlayer1() {
        var game = mock(Game.class);
        when(game.countResults(PLAYER1_WINS)).thenReturn(1);
        new RockPaperScissors(null, out).printGameReport(game);

        assertContainsLine(baos.toString(), "1 victory for Player 1");
    }

    @Test
    void printGameReportPrintsVictoryForPlayer2() {
        var game = mock(Game.class);
        when(game.countResults(PLAYER2_WINS)).thenReturn(1);
        new RockPaperScissors(null, out).printGameReport(game);

        assertContainsLine(baos.toString(), "1 victory for Player 2");
    }

    @Test
    void printGameReportPrintsZeroVictoriesForPlayer1() {
        var game = mock(Game.class);
        when(game.countResults(PLAYER1_WINS)).thenReturn(0);
        when(game.countResults(PLAYER2_WINS)).thenReturn(1);
        when(game.countResults(DRAW)).thenReturn(1);
        new RockPaperScissors(null, out).printGameReport(game);

        assertContainsLine(baos.toString(), "0 victories for Player 1");
    }

    @Test
    void printGameReportPrintsManyVictoriesForPlayer1() {
        var game = mock(Game.class);
        when(game.countResults(PLAYER1_WINS)).thenReturn(42);
        new RockPaperScissors(null, out).printGameReport(game);

        assertContainsLine(baos.toString(), "42 victories for Player 1");
    }

    @Test
    void printGameReportPrintsZeroVictoriesForPlayer2() {
        var game = mock(Game.class);
        when(game.countResults(PLAYER1_WINS)).thenReturn(1);
        when(game.countResults(PLAYER2_WINS)).thenReturn(0);
        when(game.countResults(DRAW)).thenReturn(1);
        new RockPaperScissors(null, out).printGameReport(game);

        assertContainsLine(baos.toString(), "0 victories for Player 2");
    }

    @Test
    void printGameReportPrintsManyVictoriesForPlayer2() {
        var game = mock(Game.class);
        when(game.countResults(PLAYER2_WINS)).thenReturn(42);
        new RockPaperScissors(null, out).printGameReport(game);

        assertContainsLine(baos.toString(), "42 victories for Player 2");
    }

    @Test
    void printGameReportPrintsDraw() {
        var game = mock(Game.class);
        when(game.countResults(DRAW)).thenReturn(1);
        new RockPaperScissors(null, out).printGameReport(game);

        assertContainsLine(baos.toString(), "1 draw");
    }

    @Test
    void printGameReportPrintsZeroDraws() {
        var game = mock(Game.class);
        when(game.countResults(PLAYER1_WINS)).thenReturn(1);
        when(game.countResults(PLAYER2_WINS)).thenReturn(1);
        when(game.countResults(DRAW)).thenReturn(0);
        new RockPaperScissors(null, out).printGameReport(game);

        assertContainsLine(baos.toString(), "0 draws");
    }

    @Test
    void printGameReportPrintsManyDraws() {
        var game = mock(Game.class);
        when(game.countResults(DRAW)).thenReturn(42);
        new RockPaperScissors(null, out).printGameReport(game);

        assertContainsLine(baos.toString(), "42 draws");
    }

    @Test
    void runGame() {
        var rps = spy(new RockPaperScissors(null, out));
        var player1 = mock(Player.class);
        when(player1.play()).thenReturn(ROCK, SCISSORS, PAPER);
        doReturn(player1).when(rps).inputPlayer(PLAYER1_KEY);
        var player2 = mock(Player.class);
        when(player2.play()).thenReturn(SCISSORS, ROCK, PAPER);
        doReturn(player2).when(rps).inputPlayer(PLAYER2_KEY);
        var roundsToPlay = 42;
        doReturn(roundsToPlay).when(rps).inputRoundsToPlay();
        rps.runGame();

        var inOrder = inOrder(rps);
        inOrder.verify(rps).inputPlayer(PLAYER1_KEY);
        inOrder.verify(rps).inputPlayer(PLAYER2_KEY);
        inOrder.verify(rps).inputRoundsToPlay();
        var roundCaptor = ArgumentCaptor.forClass(Round.class);
        inOrder.verify(rps, times(roundsToPlay)).printRoundReport(anyInt(), roundCaptor.capture());
        var gameCaptor = ArgumentCaptor.forClass(Game.class);
        inOrder.verify(rps).printGameReport(gameCaptor.capture());
        var rounds = gameCaptor.getValue().getRounds();
        assertEquals(roundCaptor.getAllValues(), Arrays.asList(rounds));
        assertEquals(Round.of(ROCK, SCISSORS), rounds[0]);
        assertEquals(Round.of(SCISSORS, ROCK), rounds[1]);
        assertEquals(Round.of(PAPER, PAPER), rounds[2]);
        inOrder.verifyNoMoreInteractions();
    }
}
