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
}
