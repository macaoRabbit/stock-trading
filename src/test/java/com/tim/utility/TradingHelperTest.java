package com.tim.utility;

import com.tim.trade.Trading;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TradingHelperTest {
    @Test
    public void generateTradingTest() {
        String dir = "C:\\GitHubProjects\\testData\\";
        String symbols = "a1,a2,a3";
        Float seedCost = 1000.0f;
        int recordCount = 300;
        int minRecordCount = 0;
        List<Trading> tradings = TradingHelper.generate(dir, symbols, seedCost, recordCount, minRecordCount);
        assertTrue(tradings.size() == 3);
    }
}
