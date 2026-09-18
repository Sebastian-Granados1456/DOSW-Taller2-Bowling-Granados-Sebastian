package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    private int firstRoll;
    private Integer secondRoll;
    private Integer thirdRoll;

    public Frame(int firstRoll) {
        this.firstRoll = firstRoll;
    }

    public void addSecondRoll(int pins) {
        this.secondRoll = pins;
    }

    public void addThirdRoll(int pins) {
        this.thirdRoll = pins;
    }

    public int getPins() {
        int total = firstRoll;
        if (secondRoll != null) total += secondRoll;
        if (thirdRoll != null) total += thirdRoll;
        return total;
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

    public boolean hasThirdRoll() {
        return thirdRoll != null;
    }

    public int getFirstRollValue() {
        return firstRoll;
    }
}
