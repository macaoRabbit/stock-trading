package com.tim.trade;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupAnyDayGapPairSwapMultipleSwapTest {
    @Test
    public void groupAnyDapGapPairSwapTradingAnalyzeLossMajorTrue4SymbolsTest() {

        String dir = "C:\\GitHubProjects\\testData\\";
        String f1 = "g1.csv";
        String f2 = "g2.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, seedCost);
        Trading t2 = new GapTrading(dir + f2, 0.0f);
        GroupTradeDayGapPairSwapTrading g = new GroupTradeDayGapPairSwapTrading();
        Float gap = 0.05f;
        g.setLossMajor(true);
        g.setAnyDayGap(true);
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        g.report();
        assertEquals(g.getGroupTradeDays().size(), 3);
        assertTrue(g.getAnnualizedReturn() > 0);
        assertEquals(0.11855030059814453, g.getAnnualizedReturn(), 0.01);
    }
    @Test
    public void groupAnyDapGapPairSwapTradingAnalyzeLossMajorTrue5SymbolsIITest() {

        String dir = "C:\\GitHubProjects\\testData\\";
        String f1 = "g1.csv";
        String f2 = "g2.csv";
        String f3 = "g3.csv";
        String f4 = "g4.csv";
        String f5 = "g5.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, 0.0f);
        Trading t2 = new GapTrading(dir + f2, 0.0f);
        Trading t3 = new GapTrading(dir + f3, seedCost);
        Trading t4 = new GapTrading(dir + f4, seedCost);
        Trading t5 = new GapTrading(dir + f5, 0.0f);
        GroupTradeDayGapPairSwapTrading g = new GroupTradeDayGapPairSwapTrading();
        Float gap = 0.05f;
        g.setLossMajor(true);
        g.setAnyDayGap(true);
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.getTradings().add(t3);
        g.getTradings().add(t4);
        g.getTradings().add(t5);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        g.report();
        assertEquals(g.getGroupTradeDays().size(), 5);
        assertTrue(g.getAnnualizedReturn() > 0);
        assertEquals(0.7697654366493225, g.getAnnualizedReturn(), 0.01);
    }

    @Test
    public void groupAnyDapGapPairSwapTradingAnalyzeLossMajorFalse4SymbolsTest() {

        String dir = "C:\\GitHubProjects\\testData\\";
        String f1 = "g1.csv";
        String f2 = "g2.csv";
        String f3 = "g3.csv";
        String f4 = "g4.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, 0.0f);
        Trading t2 = new GapTrading(dir + f2, 0.0f);
        Trading t3 = new GapTrading(dir + f3, seedCost);
        Trading t4 = new GapTrading(dir + f4, seedCost);
        GroupTradeDayGapPairSwapTrading g = new GroupTradeDayGapPairSwapTrading();
        Float gap = 0.05f;
        g.setLossMajor(false);
        g.setAnyDayGap(true);
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.getTradings().add(t3);
        g.getTradings().add(t4);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        g.report();
        assertEquals(g.getGroupTradeDays().size(), 2);
        assertTrue(g.getAnnualizedReturn() > 0);
        assertEquals(0.08278122544288635, g.getAnnualizedReturn(), 0.01);
    }

    @Test
    public void groupAnyDapGapPairSwapTradingAnalyzeLossMajorFalse5SymbolsIITest() {

        String dir = "C:\\GitHubProjects\\testData\\";
        String f1 = "g1.csv";
        String f2 = "g2.csv";
        String f3 = "g3.csv";
        String f4 = "g4.csv";
        String f5 = "g5.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, 0.0f);
        Trading t2 = new GapTrading(dir + f2, 0.0f);
        Trading t3 = new GapTrading(dir + f3, 0.0f);
        Trading t4 = new GapTrading(dir + f4, seedCost);
        Trading t5 = new GapTrading(dir + f5, seedCost);
        GroupTradeDayGapPairSwapTrading g = new GroupTradeDayGapPairSwapTrading();
        Float gap = 0.05f;
        g.setLossMajor(false);
        g.setAnyDayGap(true);
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.getTradings().add(t3);
        g.getTradings().add(t4);
        g.getTradings().add(t5);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        g.report();
        assertEquals(g.getGroupTradeDays().size(), 4);
        assertTrue(g.getAnnualizedReturn() > 0);
        assertEquals(0.16210490465164185, g.getAnnualizedReturn(), 0.01);
    }
}
