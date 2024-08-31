package com.tim.inspection;

import com.tim.experiment.FullPairExperiment;
import com.tim.trade.GapTrading;
import com.tim.trade.GroupGapPairSwapTrading;
import com.tim.trade.Trading;

public class RatioSwap2Symbols {
    public static void main(String[] args) {

            String dir = "C:\\GitHubProjects\\data\\";
            String f1 = "AMD.csv";
            String f2 = "LLY.csv";
            Float seedCost = 1000.0f;
            Trading t1 = new GapTrading(dir + f1, 0.0f);
            Trading t2 = new GapTrading(dir + f2, seedCost);
            GroupGapPairSwapTrading g = new GroupGapPairSwapTrading();
            Float gap = 0.05f;
            g.setLossMajor(false);
            g.setGapSize(gap);
            g.getTradings().add(t1);
            g.getTradings().add(t2);
            Float controlReturn = FullPairExperiment.getThisControlReturn(g, 1);
            g.setControlReturn(controlReturn);
            g.initQuotesWithCsvFileForAllTradings();
            g.matchQuotesForAllTradings();
            g.analyze();
            g.report();
        }
}
