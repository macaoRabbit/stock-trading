package com.tim.trade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupTradingTest {
    @Test
    public void groupTradingAnalyzeTest() {
        String dir = "C:\\GitHubProjects\\testData\\";
        String f1 = "a1.csv";
        String f2 = "a2.csv";
        String f3 = "a3.csv";
        Float seedCost = 1000.0f;
        ControlTrading t1 = new ControlTrading(dir + f1, seedCost);
        ControlTrading t2 = new ControlTrading(dir + f2, seedCost);
        ControlTrading t3 = new ControlTrading(dir + f3, seedCost);
        GroupControlTrading g = new GroupControlTrading();
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.getTradings().add(t3);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        assertTrue(g.getAnnualizedReturn() > 0);
    }
}
