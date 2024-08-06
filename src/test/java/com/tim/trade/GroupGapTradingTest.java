package com.tim.trade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupGapTradingTest {

    @Test
    public void basicGroupGapFromAverageTradingAnalyzeTest() {

        String dir = "C:\\GitHubProjects\\data\\";
        String f1 = "a1.csv";
        String f2 = "a2.csv";
        String f3 = "a3.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, seedCost);
        Trading t2 = new GapTrading(dir + f2, seedCost);
        Trading t3 = new GapTrading(dir + f3, seedCost);
        GroupGapTrading g = new GroupGapTrading();
        Float gap = 0.05f;
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.getTradings().add(t3);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        assertTrue(g.getAnnualizedReturn() > 0);
    }
    @Test
    public void groupGapFromAverageTradingAnalyzeTest() {

        String dir = "C:\\GitHubProjects\\data\\";
        String f1 = "AMD.csv";
        String f2 = "NOW.csv";
        String f3 = "PAYC.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, seedCost);
        Trading t2 = new GapTrading(dir + f2, seedCost);
        Trading t3 = new GapTrading(dir + f3, seedCost);
        GroupGapTrading g = new GroupGapTrading();
        Float gap = 0.05f;
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.getTradings().add(t3);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        assertTrue(g.getAnnualizedReturn() > 0);
    }
}
