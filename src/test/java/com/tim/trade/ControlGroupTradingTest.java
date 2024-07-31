package com.tim.trade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ControlGroupTradingTest {
    @Test
    public void congtrolGroupTradingAnalyzeTest() {
        String dir = "C:\\GitHubProjects\\data\\";
        String f1 = "a1.csv";
        String f2 = "a2.csv";
        Float seedCost = 1000.0f;
        ControlTrading t1 = new ControlTrading(dir + f1, seedCost);
        ControlTrading t2 = new ControlTrading(dir + f2, seedCost);
        ControlGroupTrading g = new ControlGroupTrading();
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.analyze();
        assertTrue(g.getAnnualizedReturn() > 0);
    }
}
