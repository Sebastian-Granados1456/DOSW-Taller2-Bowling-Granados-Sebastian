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

    @Test
    @DisplayName("Juego sin strikes ni spares - suma directa de los pines")
    void noStrikesOrSpares_sumsAllPins() {
        BowlingGame game = new BowlingGame();
        for (int i = 0; i < 10; i++) {
            game.roll(3);
            game.roll(4);
        }
        BowlingScorer scorer = new BowlingScorer();
        assertEquals(70, scorer.calculate(game.getFrames()));
    }
}
