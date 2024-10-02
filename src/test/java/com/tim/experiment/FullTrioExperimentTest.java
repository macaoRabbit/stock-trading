package com.tim.experiment;

import com.tim.result.GroupTradeResult;
import com.tim.trade.GapTrading;
import com.tim.trade.Trading;
import com.tim.utility.FloatRange;
import com.tim.utility.Symbols;
import com.tim.utility.TradingAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FullTrioExperimentTest {
    @Test
    public void groupGapExperimentTest() {

        String dir = "C:\\GitHubProjects\\data\\";
        String resultDir = "C:\\GitHubProjects\\result\\";
        String subDir = resultDir + "fullExperiment";
        String resultFile = subDir + "\\" + this.getClass().getSimpleName() + "_" + Symbols.getStringDate() + ".csv";
        String f1 = "AMD.csv";
        String f2 = "NOW.csv";
        String f3 = "PAYC.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, seedCost);
        Trading t2 = new GapTrading(dir + f2, seedCost);
        Trading t3 = new GapTrading(dir + f3, seedCost);
        int recordCount = 300;
        t1.setRecordLimit(recordCount);
        t2.setRecordLimit(recordCount);
        t3.setRecordLimit(recordCount);

        List<Trading> tradings = new ArrayList<>();
        tradings.add(t1);
        tradings.add(t2);
        tradings.add(t3);

        FloatRange gapRange = new FloatRange(0.0f, 0.2f, 0.001f);
        FloatRange powerRange = new FloatRange(0.0f, 6.1f, 1.0f);
        boolean isLossMajor = true;

        FullPairExperiment f = new FullTrioExperiment(tradings, gapRange, powerRange, isLossMajor);
        f.setResultLimit(5000);
        f.setSeedCost(seedCost);
        List<GroupTradeResult> results = f.run(TradingAlgorithm.RATIO_SPLIT);
        f.processResult();
        f.saveResult(subDir, resultFile);
        assertEquals(results.size(),2800);
    }
}
