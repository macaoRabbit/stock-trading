package com.tim.trade;

import com.tim.result.GroupTradeResult;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupTradeDayGapRatioTradingTest {

    @Test
    public void AverageSplitGroupGapTradingAnalyzeTest() {

        String dir = "C:\\GitHubProjects\\testData\\";
        String f1 = "a1.csv";
        String f2 = "a2.csv";
        String f3 = "a3.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, seedCost);
        Trading t2 = new GapTrading(dir + f2, seedCost);
        Trading t3 = new GapTrading(dir + f3, seedCost);
        GroupTradeDayGapRatioTrading g = new GroupTradeDayGapRatioTrading();
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
        g.report();
        assertTrue(g.getAnnualizedReturn() > 0);
        assertEquals(g.getAnnualizedReturn(),  0.282, 0.01);
    }

    @Test
    public void LossMajorSplitGroupGapTradingAnalyzeTest() {
        String dir = "C:\\GitHubProjects\\testData\\";
        String f1 = "a1.csv";
        String f2 = "a2.csv";
        String f3 = "a3.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, seedCost);
        Trading t2 = new GapTrading(dir + f2, seedCost);
        Trading t3 = new GapTrading(dir + f3, seedCost);
        GroupTradeDayGapRatioTrading g = new GroupTradeDayGapRatioTrading();
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
        g.report();
        assertTrue(g.getAnnualizedReturn() > 0);
        assertEquals(g.getAnnualizedReturn(),  0.233, 0.01);
    }

    @Test
    public void GainMajorSplitGroupGapTradingAnalyzeTest() {
        String dir = "C:\\GitHubProjects\\testData\\";
        String f1 = "a1.csv";
        String f2 = "a2.csv";
        String f3 = "a3.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, seedCost);
        Trading t2 = new GapTrading(dir + f2, seedCost);
        Trading t3 = new GapTrading(dir + f3, seedCost);
        GroupTradeDayGapRatioTrading g = new GroupTradeDayGapRatioTrading();
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
        g.report();
        GroupTradeResult r = g.collectResult();
        assertTrue(g.getAnnualizedReturn() > 0);
        assertEquals(g.getAnnualizedReturn(),  0.329, 0.01);
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
        GroupTradeDayGapRatioTrading g = new GroupTradeDayGapRatioTrading();
        t1.setRecordLimit(5);
        t2.setRecordLimit(5);
        t3.setRecordLimit(5);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.getTradings().add(t3);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();

        Float gap = 0.05f;
        Integer splitRatioPower = 0;
        g.setGapSize(gap);
        g.setupSplitRatio();
        g.analyze();
        g.report();
        g.reportSummary();
        assertTrue(g.getAnnualizedReturn() > 0);
    }
}
