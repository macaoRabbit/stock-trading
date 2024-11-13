package com.tim.utility;

public enum ExperimentType {
    FullPair(0),
    FullTrio(1000),
    FullQuad(2000),
    RandomPair(500),
    RandomTrio(1000),
    RandomQuad(2000);

    private final int runCount;
    ExperimentType(int runCount) {
        this.runCount = runCount;
    }

    public int runCount() {
        return runCount;
    }
}
