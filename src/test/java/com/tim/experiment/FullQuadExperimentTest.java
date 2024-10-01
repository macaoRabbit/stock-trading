package com.tim.experiment;

import com.tim.result.GroupTradeResult;
import com.tim.trade.GapTrading;
import com.tim.trade.Trading;
import com.tim.utility.FloatRange;
import com.tim.utility.Symbols;
import com.tim.utility.TradingAlogirthm;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FullQuadExperimentTest {
    @Test
    public void groupFullQuadExperimentTest() {

        String dir = "C:\\GitHubProjects\\data\\";
        String resultDir = "C:\\GitHubProjects\\result\\";
        String subDir = resultDir + "fullExperiment";
        String resultFile = subDir + "\\" + this.getClass().getSimpleName() + "_" + Symbols.getStringDate() + ".csv";
        String f1 = "AMD.csv";
        String f2 = "NOW.csv";
        String f3 = "PAYC.csv";
        String f4 = "IBM.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, seedCost);
        Trading t2 = new GapTrading(dir + f2, seedCost);
        Trading t3 = new GapTrading(dir + f3, seedCost);
        Trading t4 = new GapTrading(dir + f4, seedCost);
        int recordCount = 300;
        t1.setRecordLimit(recordCount);
        t2.setRecordLimit(recordCount);
        t3.setRecordLimit(recordCount);
        t4.setRecordLimit(recordCount);

        List<Trading> tradings = new ArrayList<>();
        tradings.add(t1);
        tradings.add(t2);
        tradings.add(t3);
        tradings.add(t4);

        FloatRange gapRange = new FloatRange(0.0f, 0.2f, 0.001f);
        FloatRange powerRange = new FloatRange(0.0f, 6.1f, 1.0f);
        boolean isLossMajor = true;

        FullQuadExperiment f = new FullQuadExperiment(tradings, gapRange, powerRange, isLossMajor);
        f.setResultLimit(5000);
        f.setSeedCost(seedCost);
        List<GroupTradeResult> results = f.run(TradingAlogirthm.RATIO_SPLIT);
        f.processResult();
        f.saveResult(subDir, resultFile);
        assertEquals(results.size(),2800);
    }
}
