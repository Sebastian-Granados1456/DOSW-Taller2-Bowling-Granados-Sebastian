package edu.eci.dosw.bowling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BowlingScorerTest {

    @Test
    @DisplayName("Juego con todos los tiros en 0 - score debe ser 0")
    void allZeros_scoresZero() {
        BowlingGame game = new BowlingGame();
        for (int i = 0; i < 20; i++) game.roll(0);

        BowlingScorer scorer = new BowlingScorer();
        assertEquals(0, scorer.calculate(game.getFrames()));
    }
}
