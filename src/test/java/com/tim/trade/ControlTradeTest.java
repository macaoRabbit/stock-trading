package com.tim.trade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ControlTradeTest {
    @Test
    public void congtrolTradeAnalyzeTest() {
        String dir = "C:\\GitHubProjects\\data\\";
        String f1 = "AMD.csv";
        Float seedCost = 1000.0f;
        ControlTrade t = new ControlTrade(dir + f1, seedCost);
        t.analyze();
        assertTrue(t.getAnnualizedReturn() > 0);
    }
}
