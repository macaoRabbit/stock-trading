package com.tim.trade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupGapTradingTest {

    @Test
    public void AverageSplitGroupGapTradingAnalyzeTest() {

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
        Float splitRatioPower = 0.0f;
        g.setGapSize(gap);
        g.setSplitRatioPower(splitRatioPower);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.getTradings().add(t3);
        g.setupSplitRatio();
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        assertTrue(g.getAnnualizedReturn() > 0);
    }

    @Test
    public void LossMajorSplitGroupGapTradingAnalyzeTest() {
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
        Float splitRatioPower = 2.0f;
        boolean isLossMajor = true;
        g.setLossMajor(isLossMajor);
        g.setGapSize(gap);
        g.setSplitRatioPower(splitRatioPower);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.getTradings().add(t3);
        g.setupSplitRatio();
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        assertTrue(g.getAnnualizedReturn() > 0);
    }

    @Test
    public void GainMajorSplitGroupGapTradingAnalyzeTest() {
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
        Float splitRatioPower = 2.0f;
        boolean isLossMajor = false;
        g.setLossMajor(isLossMajor);
        g.setGapSize(gap);
        g.setSplitRatioPower(splitRatioPower);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.getTradings().add(t3);
        g.setupSplitRatio();
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
        Integer splitRatioPower = 0;
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.getTradings().add(t3);
        g.setupSplitRatio();
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        assertTrue(g.getAnnualizedReturn() > 0);
    }
}
