package com.tim.utility;

public enum ExperimentType {
    FullPair(0),
    FullTrio(0),
    FullQuad(0),
    RandomPair(100),
    RandomTrio(500),
    RandomQuad(1000);

    private final int runCount;
    ExperimentType(int runCount) {
        this.runCount = runCount;
    }

    public int runCount() {
        return runCount;
    }
}
