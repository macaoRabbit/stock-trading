package com.tim.trade;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupGapPairSwapTradingMultipleSwapTest {
    @Test
    public void groupGapPairSwapTradingAnalyzeLossMajorFalse4SymbolsTest() {

        String dir = "C:\\GitHubProjects\\testData\\";
        String f1 = "p1.csv";
        String f2 = "p2.csv";
        String f3 = "p3.csv";
        String f4 = "p4.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, 0.0f);
        Trading t2 = new GapTrading(dir + f2, seedCost);
        Trading t3 = new GapTrading(dir + f3, 0.0f);
        Trading t4 = new GapTrading(dir + f4, 0.0f);
        GroupGapPairSwapTrading g = new GroupGapPairSwapTrading();
        Float gap = 0.05f;
        g.setLossMajor(true);
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.getTradings().add(t3);
        g.getTradings().add(t4);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        g.report();
        assertTrue(g.getAnnualizedReturn() < 0);
        assertEquals(-0.08257763832807541, g.getAnnualizedReturn(), 0.01);
    }
}
