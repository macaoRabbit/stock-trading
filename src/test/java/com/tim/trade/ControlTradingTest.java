package com.tim.trade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ControlTradingTest {
    @Test
    public void congtrolTradeAnalyzeTest() {
        String dir = "C:\\GitHubProjects\\data\\";
        String f1 = "AMD.csv";
        Float seedCost = 1000.0f;
        ControlTrading t = new ControlTrading(dir + f1, seedCost);
        t.analyze();
        assertTrue(t.getAnnualizedReturn() > 0);
    }
}
