package com.tim.experiment;

import com.tim.utility.ExperimentType;

public class RandomExpreiment extends FullExperiment {
    public RandomExpreiment(String symbols, String subDir, String filePrefix) {
        super(symbols, subDir, filePrefix);
    }

    @Override
    public void run() {
        runOnseSet(ExperimentType.RandomPair);
        runOnseSet(ExperimentType.RandomTrio);
        runOnseSet(ExperimentType.RandomQuad);
    }
}
