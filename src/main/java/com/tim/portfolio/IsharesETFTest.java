package com.tim.portfolio;

import com.tim.experiment.FullPairExperiment;
import com.tim.utility.FloatRange;
import com.tim.utility.TradingAlogirthm;
import com.tim.utility.TradingHelper;

public class IsharesETFTest {
    static String dir = "C:\\GitHubProjects\\data\\";
    static String resultDir = "C:\\GitHubProjects\\result\\";
    static String symbols = "VHT,QQQ,XLK,IVV,IJH,IJR,IVW,IJK,IJT,IVE,IJJ,IJS";

    static String resultFile = "iSharesETF";
    static String fileAppendix = TradingHelper.FILE_TYPE;

    static Float seedCost = 1000.0f;
    static int recordCount = 1300;
    static int minRecordCount = 300;

    public static void main(String[] args) {
        equalSplitLossMajorTrue();
        equalSplitLossMajorfalse();
        pairswapLossMajorTrue();
        pairswapLossMajorFalse();
    }

    private static void equalSplitLossMajorTrue() {
        FloatRange gapRange = new FloatRange(0.025f, 0.11f, 0.025f);
        FloatRange powerRange = new FloatRange(0.0f, 1.1f, 4.0f);
        boolean isLossMajor = true;
        String runType = "_equalSplit_" + isLossMajor;

        FullPairExperiment f = new FullPairExperiment(dir, symbols, seedCost, recordCount, minRecordCount, gapRange, powerRange, isLossMajor);
        f.setResultLimit(200000);
        f.run(TradingAlogirthm.RATIO_SPLIT);
        f.saveResult(resultDir +  resultFile + runType + fileAppendix);
    }

    private static void equalSplitLossMajorfalse() {
        FloatRange gapRange = new FloatRange(0.025f, 0.11f, 0.025f);
        FloatRange powerRange = new FloatRange(0.0f, 1.1f, 4.0f);
        boolean isLossMajor = false;
        String runType = "_equalSplit_" + isLossMajor;

        FullPairExperiment f = new FullPairExperiment(dir, symbols, seedCost, recordCount, minRecordCount, gapRange, powerRange, isLossMajor);
        f.setResultLimit(200000);
        f.run(TradingAlogirthm.RATIO_SPLIT);
        f.saveResult(resultDir +  resultFile + runType + fileAppendix);
    }

    private static void pairswapLossMajorTrue() {
        FloatRange gapRange = new FloatRange(0.025f, 0.11f, 0.025f);
        FloatRange powerRange = new FloatRange(0.0f, 1.1f, 4.0f);
        boolean isLossMajor = true;
        String runType = "_pairswap_" + isLossMajor;

        FullPairExperiment f = new FullPairExperiment(dir, symbols, seedCost, recordCount, minRecordCount, gapRange, powerRange, isLossMajor);
        f.setResultLimit(200000);
        f.run(TradingAlogirthm.PAIR_SWAP);
        f.saveResult(resultDir +  resultFile + runType + fileAppendix);
    }

    private static void pairswapLossMajorFalse() {
        FloatRange gapRange = new FloatRange(0.025f, 0.11f, 0.025f);
        FloatRange powerRange = new FloatRange(0.0f, 1.1f, 4.0f);
        boolean isLossMajor = false;
        String runType = "_pairswap_" + isLossMajor;

        FullPairExperiment f = new FullPairExperiment(dir, symbols, seedCost, recordCount, minRecordCount, gapRange, powerRange, isLossMajor);
        f.setResultLimit(200000);
        f.run(TradingAlogirthm.PAIR_SWAP);
        f.saveResult(resultDir +  resultFile + runType + fileAppendix);
    }
}
