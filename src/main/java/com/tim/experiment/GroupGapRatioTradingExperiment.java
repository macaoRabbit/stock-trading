package com.tim.experiment;

import com.tim.result.GroupTradeResult;
import com.tim.trade.GroupGapRatioTrading;
import com.tim.utility.FloatRange;

import java.util.ArrayList;
import java.util.List;

public class GroupGapRatioTradingExperiment {
    GroupGapRatioTrading g;
    Float gapLowerLimit = 0.0f;
    Float powerLowerLimit = 0.0f;
    Float gapUpperLimit = 0.0f;
    Float powerUpperLimit = 0.0f;

    Float gapIncrement = 0.025f;
    Float powerIncrement = 1.0f;
    Float controlReturn = 0.0f;
    boolean isLossMajor = true;
    List<GroupTradeResult> results = new ArrayList<>();

    public GroupGapRatioTradingExperiment(GroupGapRatioTrading g, Float gapLimit, Float powerLimit) {
        this.g = g;
        this.gapUpperLimit = gapLimit;
        this.powerUpperLimit = powerLimit;
    }

    public GroupGapRatioTradingExperiment(GroupGapRatioTrading g, FloatRange gapRange, FloatRange powerRange, Float controlReturn, List<GroupTradeResult> results, boolean isLossMajor) {
        this.g = g;
        this.gapLowerLimit = gapRange.getLower();
        this.gapUpperLimit = gapRange.getUpper();
        this.gapIncrement = gapRange.getIncrement();
        this.powerLowerLimit = powerRange.getLower();
        this.powerUpperLimit = powerRange.getUpper();
        this.powerIncrement = powerRange.getIncrement();
        this.controlReturn = controlReturn;
        this.results = results;
        this.isLossMajor = isLossMajor;
        g.setControlReturn(controlReturn);
    }

    public void run() {
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
//                  g.reportSummary();
                GroupTradeResult r = g.collectResult();
                results.add(r);
                currentPower = currentPower + powerIncrement;
            }
            currentGap = currentGap + gapIncrement;
        }
    }
}
