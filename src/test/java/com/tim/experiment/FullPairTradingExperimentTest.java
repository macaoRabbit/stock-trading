package com.tim.experiment;

import com.tim.trade.GapTrading;
import com.tim.trade.Trading;
import com.tim.utility.FloatRange;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FullPairTradingExperimentTest {
    @Test
    public void groupGapTradingExperimentTest() {

        String dir = "C:\\GitHubProjects\\data\\";
        String f1 = "AMD.csv";
        String f2 = "NOW.csv";
        String f3 = "PAYC.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, seedCost);
        Trading t2 = new GapTrading(dir + f2, seedCost);
        Trading t3 = new GapTrading(dir + f3, seedCost);

        List<Trading> tradings = new ArrayList<>();
        tradings.add(t1);
        tradings.add(t2);
        tradings.add(t3);

        FloatRange gapRange = new FloatRange(0.0f, 0.2f, 0.025f);
        FloatRange powerRange = new FloatRange(0.0f, 5.0f, 1.0f);

        FullPairTradingExperiment f = new FullPairTradingExperiment(tradings, gapRange, powerRange);
        f.run();
    }
}
