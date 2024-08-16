package com.tim.results;

import com.tim.experiment.FullPairTradingExperiment;
import com.tim.result.GroupTradeResult;
import com.tim.trade.GapTrading;
import com.tim.trade.Trading;
import com.tim.utility.FloatRange;
import com.tim.utility.TradingHelper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class IsharesETFTest {
    @Test
    public void iSharesETFExperimentTest() {

        String dir = "C:\\GitHubProjects\\data\\";
        String symbols = "QQQ,XLK,IVV,IJH,IJR,IVW,IJK,IJT,IVE,IJJ,IJS";
        Float seedCost = 1000.0f;
        int recordCount = 1300;
        List<Trading> tradings = TradingHelper.generate(dir, symbols, seedCost, recordCount);

        FloatRange gapRange = new FloatRange(0.0f, 0.2f, 0.001f);
        FloatRange powerRange = new FloatRange(0.0f, 6.1f, 1.0f);

        FullPairTradingExperiment f = new FullPairTradingExperiment(tradings, gapRange, powerRange);
        f.setResultLimit(10000);
        List<GroupTradeResult> results = f.run();
        f.processResult();
    }
}
