package edu.eci.dosw.bowling;

import java.util.List;

public class BowlingScorer {

    public int calculate(List<Frame> frames) {
        int total = 0;
        for (int i = 0; i < frames.size(); i++) {
            Frame frame = frames.get(i);
            boolean isLastFrame = i == frames.size() - 1;

            total += frame.getPins();

            if (!isLastFrame) {
                if (frame.isStrike()) {
                    total += strikeBonus(frames, i);
                } else if (frame.isSpare()) {
                    total += frames.get(i + 1).getFirstRollValue();
                }
            }
        }
        return total;
    }

    private int strikeBonus(List<Frame> frames, int i) {
        Frame next = frames.get(i + 1);
        boolean nextIsLastFrame = (i + 1) == frames.size() - 1;

        if (next.getFirstRollValue() == 10 && !nextIsLastFrame && i + 2 < frames.size()) {
            return 10 + frames.get(i + 2).getFirstRollValue();
        }
        if (next.getFirstRollValue() == 10 && nextIsLastFrame) {
            return 10 + next.getSecondRollValue();
        }
        return next.getPins();
    }
}

