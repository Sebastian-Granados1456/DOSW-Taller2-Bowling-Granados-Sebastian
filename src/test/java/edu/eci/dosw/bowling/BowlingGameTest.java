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
}
