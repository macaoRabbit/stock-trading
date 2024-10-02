package com.tim.experiment;

import com.tim.result.GroupTradeResult;
import com.tim.trade.Trading;
import com.tim.utility.FloatRange;
import com.tim.utility.TradingAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class FullTrioExperiment extends FullPairExperiment {
    public FullTrioExperiment(String dir, String symbols, Float seedCost, int recordCount, int minRecordCount, FloatRange gapRange, FloatRange powerRange, boolean isLossMajor) {
        super(dir, symbols, seedCost, recordCount, minRecordCount, gapRange, powerRange, isLossMajor);
    }

    public FullTrioExperiment(List<Trading> tradings, FloatRange gapRange, FloatRange powerRange, boolean isLossMajor) {
        super(tradings, gapRange, powerRange, isLossMajor);
    }

    @Override
    public List<GroupTradeResult> run(TradingAlgorithm tradingAlgorithm) {
        results.clear();
        int tradingsSize = tradings.size();
        for (int i = 0; i < tradingsSize; i++) {
            for (int j = i + 1; j < tradingsSize; j++) {
                for (int k = j + 1; k < tradingsSize; k++) {

                    List<Trading> thisTradingGroup = new ArrayList<>();
                    Trading t1 = tradings.get(i);
                    Trading t2 = tradings.get(j);
                    Trading t3 = tradings.get(k);
                    thisTradingGroup.add(t1);
                    thisTradingGroup.add(t2);
                    thisTradingGroup.add(t3);

                    runJustOne(tradingAlgorithm, thisTradingGroup);
                }
            }
        }
        return results;
    }
}
