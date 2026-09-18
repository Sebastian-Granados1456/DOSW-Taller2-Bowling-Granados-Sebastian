package edu.eci.dosw.bowling;

import java.util.List;

public class BowlingScorer {

    public int calculate(List<Frame> frames) {
        int total = 0;
        for (int i = 0; i < frames.size(); i++) {
            Frame frame = frames.get(i);
            total += frame.getPins();

            if (frame.isStrike() && i + 1 < frames.size()) {
                total += strikeBonus(frames, i);
            } else if (frame.isSpare() && i + 1 < frames.size()) {
                total += frames.get(i + 1).getFirstRollValue();
            }
        }
        return total;
    }

    private int strikeBonus(List<Frame> frames, int i) {
        Frame next = frames.get(i + 1);
        if (next.getFirstRollValue() == 10 && i + 2 < frames.size()) {
            return 10 + frames.get(i + 2).getFirstRollValue();
        }
        return next.getPins();
    }
}

