package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    private int firstRoll;
    private Integer secondRoll;

    public Frame(int firstRoll) {
        this.firstRoll = firstRoll;
    }

    public void addSecondRoll(int pins) {
        this.secondRoll = pins;
    }

    public int getPins() {
        return firstRoll + (secondRoll != null ? secondRoll : 0);
    }

    public boolean isStrike() {
        return firstRoll == 10;
    }

    public boolean isSpare() {
        return secondRoll != null && firstRoll + secondRoll == 10;
    }

    public boolean hasSecondRoll() {
        return secondRoll != null;
    }
}
