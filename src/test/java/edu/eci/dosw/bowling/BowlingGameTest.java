package edu.eci.dosw.bowling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BowlingGameTest {
    @Test
    @DisplayName("roll(0) no lanza excepción.")
    void rollZeroPins_doesNotThrow(){
        BowlingGame game = new BowlingGame();

        assertDoesNotThrow(() -> game.roll(0));
        assertEquals(0, game.getFrames().get(0).getPins());
    }

    @Test
    @DisplayName("roll(-1) lanza IllegalArgumentException")
    void rollNegativePins_throwsException() {
        BowlingGame game = new BowlingGame();
        assertThrows(IllegalArgumentException.class, () -> game.roll(-1));
    }

    @Test
    @DisplayName("roll(11) lanza IllegalArgumentException")
    void rollTooManyPins_throwsException() {
        BowlingGame game = new BowlingGame();
        assertThrows(IllegalArgumentException.class, () -> game.roll(11));
    }

    @Test
    @DisplayName("Dos tiros que suman mas de 10 lanza IllegalArgumentException")
    void rollSumExceeds10_throwsException() {
        BowlingGame game = new BowlingGame();
        game.roll(7);
        assertThrows(IllegalArgumentException.class, () -> game.roll(6));
    }

    @Test
    @DisplayName("roll() despues de terminar el juego lanza IllegalStateException")
    void rollAfterGameComplete_throwsException() {
        BowlingGame game = new BowlingGame();
        for (int i = 0; i < 20; i++) game.roll(0);
        assertThrows(IllegalStateException.class, () -> game.roll(0));
    }

    @Test
    @DisplayName("roll(10) marca el frame como strike")
    void rollTenPins_marksStrike() {
        BowlingGame game = new BowlingGame();
        game.roll(10);
        assertTrue(game.getFrames().get(0).isStrike());
    }

    @Test
    @DisplayName("roll(5) + roll(5) marca el frame como spare")
    void rollSpare_marksFrameAsSpare() {
        BowlingGame game = new BowlingGame();
        game.roll(5);
        game.roll(5);
        assertTrue(game.getFrames().get(0).isSpare());
    }

    @Test
    @DisplayName("Frame 10 con strike acepta hasta 3 tiros sin lanzar excepcion")
    void tenthFrameWithStrike_acceptsThreeRolls() {
        BowlingGame game = new BowlingGame();
        for (int i = 0; i < 18; i++) game.roll(0); // frames 1-9 completos (0+0 x9 = 18 rolls)
        assertDoesNotThrow(() -> {
            game.roll(10);
            game.roll(10);
            game.roll(10);
        });
    }

    @Test
    @DisplayName("score() antes de completar el juego lanza IllegalStateException")
    void scoreBeforeGameComplete_throwsException() {
        BowlingGame game = new BowlingGame();
        game.roll(5);
        assertThrows(IllegalStateException.class, game::score);
    }

    @Test
    @DisplayName("isComplete() al inicio del juego es false")
    void isComplete_atStart_isFalse() {
        BowlingGame game = new BowlingGame();
        assertFalse(game.isComplete());
    }

    @Test
    @DisplayName("isComplete() despues de 9 frames completos es false")
    void isComplete_after9Frames_isFalse() {
        BowlingGame game = new BowlingGame();
        for (int i = 0; i < 9; i++) { game.roll(3); game.roll(4); }
        assertFalse(game.isComplete());
    }

    @Test
    @DisplayName("10 frames normales completos es true")
    void isComplete_after10NormalFrames_isTrue() {
        BowlingGame game = new BowlingGame();
        for (int i = 0; i < 10; i++) { game.roll(3); game.roll(4); }
        assertTrue(game.isComplete());
    }
}
