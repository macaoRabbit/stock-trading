package com.tim.experiment;

import com.tim.trade.GroupGapTrading;
import com.tim.utility.FloatRange;

public class GroupGapTradingExperiment {
    GroupGapTrading g;
    Float gapLowerLimit = 0.0f;
    Float powerLowerLimit = 0.0f;
    Float gapUpperLimit = 0.0f;
    Float powerUpperLimit = 0.0f;

    Float gapIncrement = 0.025f;
    Float powerIncrement = 1.0f;

    public GroupGapTradingExperiment(GroupGapTrading g, Float gapLimit, Float powerLimit) {
        this.g = g;
        this.gapUpperLimit = gapLimit;
        this.powerUpperLimit = powerLimit;
    }

    public GroupGapTradingExperiment(GroupGapTrading g, FloatRange gapRange, FloatRange powerRange) {
        this.g = g;
        this.gapLowerLimit = gapRange.getLower();
        this.gapUpperLimit = gapRange.getUpper();
        this.gapIncrement = gapRange.getIncrement();
        this.powerLowerLimit = powerRange.getLower();
        this.powerUpperLimit = powerRange.getUpper();
        this.powerIncrement = powerRange.getIncrement();

    }

    public void run() {
        boolean isLossMajor = true;

        do {
            Float currentGap = gapLowerLimit;
            while (currentGap < gapUpperLimit) {
                Float currentPower = powerLowerLimit;
                while (currentPower < powerUpperLimit) {
                    g.clear();
                    g.setLossMajor(isLossMajor);
                    g.setGapSize(currentGap);
                    g.setSplitRatioPower(currentPower);
                    g.setupSplitRatio();
                    g.analyze();
                    g.reportSummary();
                    currentPower = currentPower + powerIncrement;
                }
                currentGap = currentGap + gapIncrement;
            }
            if (isLossMajor) {
                isLossMajor = false;
            } else {
                isLossMajor = true;
            }
        } while (isLossMajor == false);
    }
}
