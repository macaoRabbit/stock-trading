package com.tim.experiment;

import com.tim.result.GroupTradeResult;
import com.tim.trade.Trading;
import com.tim.utility.ExperimentType;
import com.tim.utility.FloatRange;
import com.tim.utility.TradingAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class RandoQuadExperiment extends RandomTrioExperiment{
    public RandoQuadExperiment(List<Trading> tradings) {
        super(tradings);
    }

    public RandoQuadExperiment(List<Trading> tradings, FloatRange gapRange, FloatRange powerRange, boolean isLossMajor) {
        super(tradings, gapRange, powerRange, isLossMajor);
    }

    public RandoQuadExperiment(String dir, String symbols, Float seedCost, int recordCount, int minRecordCount, FloatRange gapRange, FloatRange powerRange, boolean isLossMajor) {
        super(dir, symbols, seedCost, recordCount, minRecordCount, gapRange, powerRange, isLossMajor);
    }

    @Override
    public List<GroupTradeResult> run(TradingAlgorithm tradingAlgorithm) {
        results.clear();
        int currentCount = 0;
        ExperimentType e = ExperimentType.RandomQuad;
        while (currentCount < e.runCount()) {
            List<Trading> thisTradingGroup = new ArrayList<>();
            List<Integer> index = getExperimentIndex(e, tradings.size());
            Trading t1 = tradings.get(index.get(0));
            Trading t2 = tradings.get(index.get(1));
            Trading t3 = tradings.get(index.get(2));
            Trading t4 = tradings.get(index.get(3));
            thisTradingGroup.add(t1);
            thisTradingGroup.add(t2);
            thisTradingGroup.add(t3);
            thisTradingGroup.add(t4);

            runJustOne(tradingAlgorithm, thisTradingGroup);
        }
        return results;
    }
}
