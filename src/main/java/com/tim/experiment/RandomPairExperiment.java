package com.tim.experiment;

import com.tim.result.GroupTradeResult;
import com.tim.trade.Trading;
import com.tim.utility.ExperimentType;
import com.tim.utility.FloatRange;
import com.tim.utility.TradingAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class RandomPairExperiment extends FullPairExperiment {
    public RandomPairExperiment(List<Trading> tradings) {
        super(tradings);
    }

    public RandomPairExperiment(List<Trading> tradings, FloatRange gapRange, FloatRange powerRange, boolean isLossMajor) {
        super(tradings, gapRange, powerRange, isLossMajor);
    }

    public RandomPairExperiment(String dir, String symbols, Float seedCost, int recordCount, int minRecordCount, FloatRange gapRange, FloatRange powerRange, boolean isLossMajor) {
        super(dir, symbols, seedCost, recordCount, minRecordCount, gapRange, powerRange, isLossMajor);
    }

    @Override
    public List<GroupTradeResult> run(TradingAlgorithm tradingAlgorithm) {
        results.clear();
        int currentCount = 0;
        ExperimentType e = ExperimentType.RandomPair;
        int runCount = this.getRunCount() > 0? this.getRunCount() : e.runCount();
        while (currentCount < runCount) {
            List<Trading> thisTradingGroup = new ArrayList<>();
            List<Integer> index = getExperimentIndex(e, tradings.size());
            Trading t1 = tradings.get(index.get(0));
            Trading t2 = tradings.get(index.get(1));
            thisTradingGroup.add(t1);
            thisTradingGroup.add(t2);

            runJustOne(tradingAlgorithm, thisTradingGroup);
            currentCount++;
        }
        return results;
    }

    public List<Integer> getExperimentIndex(ExperimentType e, int size) {
        int length = getVectorLength(e);
        List<Integer> index = new ArrayList<>();
        int current = 0;
        while (current < length) {
            int i = (int) (Math.random() * size);
            if (!index.contains(i)) {
                index.add(i);
                current++;
            }
        }
        return index;
    }

    private int getVectorLength(ExperimentType e) {
        switch (e) {
            case RandomPair:
                return 2;
            case RandomTrio:
                return 3;
            case RandomQuad:
                return 4;
        }
        return 0;
    }
}
