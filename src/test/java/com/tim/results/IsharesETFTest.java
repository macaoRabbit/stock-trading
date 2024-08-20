package com.tim.results;

import com.tim.experiment.FullPairTradingExperiment;
import com.tim.trade.Trading;
import com.tim.utility.FloatRange;
import com.tim.utility.TradingHelper;
import org.junit.jupiter.api.Test;

import java.util.List;

public class IsharesETFTest {
    @Test
    public void iSharesETFExperimentTest() {

        String dir = "C:\\GitHubProjects\\data\\";
        String resultDir = "C:\\GitHubProjects\\result\\";
        String symbols = "VHT,QQQ,XLK,IVV,IJH,IJR,IVW,IJK,IJT,IVE,IJJ,IJS";

        String resultFile = "iSharesETF.csv";

        Float seedCost = 1000.0f;
        int recordCount = 1300;
        int minRecordCount = 300;
        List<Trading> tradings = TradingHelper.generate(dir, symbols, seedCost, recordCount, minRecordCount);

        FloatRange gapRange = new FloatRange(0.0f, 0.11f, 0.02f);
        FloatRange powerRange = new FloatRange(0.0f, 4.1f, 2.0f);

        FullPairTradingExperiment f = new FullPairTradingExperiment(tradings, gapRange, powerRange);
        f.setResultLimit(200000);
        f.run();
        f.saveResult(resultDir +  resultFile);
    }
}
