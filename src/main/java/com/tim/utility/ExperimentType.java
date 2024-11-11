package com.tim.utility;

public enum ExperimentType {
    FullPair(0),
    FullTrio(0),
    FullQuad(0),
    RandomPair(10000),
    RandomTrio(50000),
    RandomQuad(250000);

    private final int runCount;
    ExperimentType(int runCount) {
        this.runCount = runCount;
    }

    public int runCount() {
        return runCount;
    }
}
