package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    private List<Frame> frame;
    private final int pins;

    public Frame(int pins) {
        this.pins = pins;

    }

    public boolean isStrike() {
        return pins == 10;
    }

    public int getPins(){
        return pins;
    }
}
