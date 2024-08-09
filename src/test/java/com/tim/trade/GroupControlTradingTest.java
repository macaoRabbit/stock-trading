package com.tim.trade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupControlTradingTest {
    @Test
    public void controlGroupTradingAnalyzeTest() {

        String dir = "C:\\GitHubProjects\\data\\";
        String f1 = "AMD.csv";
        String f2 = "NOW.csv";
        String f3 = "PAYC.csv";
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
        g.report();
        assertTrue(g.getAnnualizedReturn() > 0);
    }
}
