package com.tim.experiment;

import com.tim.trade.GapTrading;
import com.tim.trade.GroupGapTrading;
import com.tim.trade.Trading;
import org.junit.jupiter.api.Test;

public class GroupGapTradingExperimentTest {
    @Test
    public void groupGapTradingExperimentTest() {

        String dir = "C:\\GitHubProjects\\data\\";
        String f1 = "AMD.csv";
        String f2 = "NOW.csv";
        String f3 = "PAYC.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, seedCost);
        Trading t2 = new GapTrading(dir + f2, seedCost);
        Trading t3 = new GapTrading(dir + f3, seedCost);
        GroupGapTrading g = new GroupGapTrading();
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        g.getTradings().add(t3);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();

        GroupGapTradingExperiment e = new GroupGapTradingExperiment(g, 0.2f, 5.0f);
        e.run();
    }
}
