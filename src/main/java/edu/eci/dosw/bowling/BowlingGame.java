package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor de un juego de Bowling para un jugador.
 * Un juego tiene exactamente 10 frames.
 */
public class BowlingGame {

    private final List<Frame> frames;
    private int currentFrame;

    public BowlingGame() {
        this.frames = new ArrayList<>();
        this.currentFrame = 0;
    }


    /** Registra pinos derribados. Lanza IllegalArgumentException si pines > 10.
     *  Lanza IllegalStateException si el juego ya terminó. */
    public void roll(int pins) {
        if (isComplete()) {
            throw new IllegalStateException("El juego ya termino");
        }
        if (pins < 0 || pins > 10) {
            throw new IllegalArgumentException("Los pines deben estar entre 0 y 10");
        }

        if (!frames.isEmpty()) {
            Frame last = frames.get(frames.size() - 1);
            if (!last.isStrike() && !last.hasSecondRoll()) {
                if (last.getPins() + pins > 10) {
                    throw new IllegalArgumentException("La suma del frame no puede superar 10");
                }
                last.addSecondRoll(pins);
                return;
            }
        }
        frames.add(new Frame(pins));
    }

        /** Puntaje total. Lanza IllegalStateException si el juego no está completo. */
    public int score() {
        // TODO: implementar con TDD
        return 0;
    }

    /** true cuando los 10 frames han sido completados. */
    public boolean isComplete() {
        if (frames.size() < 10) return false;
        Frame tenth = frames.get(9);
        return tenth.hasSecondRoll() || tenth.isStrike();
    }

    public List<Frame> getFrames() { return List.copyOf(frames); }
}