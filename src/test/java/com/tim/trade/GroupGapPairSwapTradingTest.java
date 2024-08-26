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
        g.setLossMajor(true);
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.setupSplitRatio(splitRatios);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        g.report();
        assertEquals(g.getGroupTradeDays().size(), 1);
        assertTrue(g.getAnnualizedReturn() > 0);
        assertEquals(g.getAnnualizedReturn(),  0.4204, 0.01);
    }

    @Test
    public void groupGapPairSwapTradingAnalyzeLossMajorFalseNoTradeTest() {

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
        g.setLossMajor(false);
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.setupSplitRatio(splitRatios);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        g.report();
        assertEquals(g.getGroupTradeDays().size(), 0);
        assertTrue(g.getAnnualizedReturn() > 0);
        assertEquals(g.getAnnualizedReturn(),  0.4910, 0.01);
    }

    @Test
    public void groupGapPairSwapTradingAnalyzeLossMajorFalseTest() {

        String dir = "C:\\GitHubProjects\\testData\\";
        String f1 = "p1.csv";
        String f2 = "p3.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, seedCost);
        Trading t2 = new GapTrading(dir + f2, 0.0f);
        GroupGapPairSwapTrading g = new GroupGapPairSwapTrading();
        Float gap = 0.05f;
        g.setLossMajor(false);
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        g.report();
        assertEquals(g.getGroupTradeDays().size(), 2);
        assertTrue(g.getAnnualizedReturn() > 0);
        assertEquals(g.getAnnualizedReturn(),  0.23557, 0.01);
    }

    @Test
    public void groupGapPairSwapTradingAnalyzeLossMajorFalse3SymbolsTest() {

        String dir = "C:\\GitHubProjects\\testData\\";
        String f1 = "p1.csv";
        String f2 = "p2.csv";
        String f3 = "p3.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, 0.0f);
        Trading t2 = new GapTrading(dir + f2, seedCost);
        Trading t3 = new GapTrading(dir + f3, 0.0f);
        GroupGapPairSwapTrading g = new GroupGapPairSwapTrading();
        Float gap = 0.05f;
        g.setLossMajor(false);
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.getTradings().add(t3);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        g.report();
        assertEquals(g.getGroupTradeDays().size(), 3);
        assertTrue(g.getAnnualizedReturn() > 0);
        assertEquals(g.getAnnualizedReturn(),  0.08660110831260681, 0.01);
    }

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
        g.setLossMajor(false);
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.getTradings().add(t3);
        g.getTradings().add(t4);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        g.report();
        assertEquals(g.getGroupTradeDays().size(), 4);
        assertTrue(g.getAnnualizedReturn() < 0);
        assertEquals(-0.08257763832807541, g.getAnnualizedReturn(), 0.01);
    }

    @Test
    public void manualTest() {

        String dir = "C:\\GitHubProjects\\data\\";
        String f1 = "QQQ.csv";
        String f2 = "IVV.csv";
        Float seedCost = 1000.0f;
        int recordCount = 1300;
        Trading t1 = new GapTrading(dir + f1, seedCost);
        Trading t2 = new GapTrading(dir + f2, 0.0f);
        t1.setRecordLimit(recordCount);
        t2.setRecordLimit(recordCount);
        GroupGapPairSwapTrading g = new GroupGapPairSwapTrading();
        Float gap = 0.05f;
        g.setLossMajor(true);
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        g.report();
//        assertEquals(g.getGroupTradeDays().size(), 4);
//        assertTrue(g.getAnnualizedReturn() < 0);
//        assertEquals(-0.08257763832807541, g.getAnnualizedReturn(), 0.01);
    }
}
