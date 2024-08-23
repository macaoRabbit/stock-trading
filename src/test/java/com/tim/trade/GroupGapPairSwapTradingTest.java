package com.tim.trade;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupGapPairSwapTradingTest {
    @Test
    public void groupGapPairSwapTradingAnalyzeTest() {

        String dir = "C:\\GitHubProjects\\testData\\";
        String f1 = "p1.csv";
        String f2 = "p2.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, seedCost);
        Trading t2 = new GapTrading(dir + f2, 0.0f);
        GroupGapPairSwapTrading g = new GroupGapPairSwapTrading();
        Float gap = 0.05f;
        List<Float> splitRatios = new ArrayList<>();
        splitRatios.add(1.0f);
        splitRatios.add(0.0f);
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.setupSplitRatio(splitRatios);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        g.report();
        assertTrue(g.getAnnualizedReturn() > 0);
        assertEquals(g.getAnnualizedReturn(),  0.282, 0.01);
    }
}
