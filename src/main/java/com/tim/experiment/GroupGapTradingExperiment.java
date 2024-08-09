package com.tim.experiment;

import com.tim.trade.GroupGapTrading;

public class GroupGapTradingExperiment {
    GroupGapTrading g;
    Float gapLimit;
    Float powerLimit;

    Float gap = 0.025f;
    Float power = 1.0f;

    public GroupGapTradingExperiment(GroupGapTrading g, Float gapLimit, Float powerLimit) {
        this.g = g;
        this.gapLimit = gapLimit;
        this.powerLimit = powerLimit;
    }

    public void run() {
        boolean isLossMajor = true;

        do {
            Float currentGap = 0f;
            while (currentGap < gapLimit) {
                Float currentPower = 0f;
                while (currentPower < powerLimit) {
                    g.clear();
                    g.setLossMajor(isLossMajor);
                    g.setGapSize(currentGap);
                    g.setSplitRatioPower(currentPower);
                    g.setupSplitRatio();
                    g.analyze();
                    g.reportSummary();
                    currentPower = currentPower + power;
                }
                currentGap = currentGap + gap;
            }
            if (isLossMajor) {
                isLossMajor = false;
            } else {
                isLossMajor = true;
            }
        } while (isLossMajor == false);
    }
}
