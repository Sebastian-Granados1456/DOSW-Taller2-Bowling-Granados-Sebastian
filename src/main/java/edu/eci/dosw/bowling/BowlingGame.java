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
            int lastIndex = frames.size() - 1;
            Frame last = frames.get(lastIndex);
            boolean lastIsTenth = lastIndex == 9;

            boolean lastStillOpen = lastIsTenth
                    ? !isTenthFrameDone(last)
                    : (!last.isStrike() && !last.hasSecondRoll());

            if (lastStillOpen) {
                if (lastIsTenth) {
                    if (!last.hasSecondRoll()) {
                        last.addSecondRoll(pins);
                    } else {
                        last.addThirdRoll(pins);
                    }
                } else {
                    if (last.getPins() + pins > 10) {
                        throw new IllegalArgumentException("La suma del frame no puede superar 10");
                    }
                    last.addSecondRoll(pins);
                }
                return;
            }
        }
        frames.add(new Frame(pins));
    }

    private boolean isTenthFrameDone(Frame tenth) {
        if (!tenth.hasSecondRoll()) return false;
        boolean earnedBonus = tenth.isStrike() || tenth.isSpare();
        return earnedBonus ? tenth.hasThirdRoll() : true;
    }

        /** Puntaje total. Lanza IllegalStateException si el juego no está completo. */
    public int score() {
        if (!isComplete()) {
            throw new IllegalStateException("El juego no esta completo");
        }
        return 0; // TODO: Modulo B
    }

    /** true cuando los 10 frames han sido completados. */
    public boolean isComplete() {
        if (frames.size() < 10){
            return false;
        }
        return isTenthFrameDone(frames.get(9));
    }

    public List<Frame> getFrames() { return List.copyOf(frames); }
}