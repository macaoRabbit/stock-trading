package com.tim.experiment;

import com.tim.utility.FloatRange;
import com.tim.utility.TradingAlogirthm;
import com.tim.utility.TradingHelper;

import java.time.Duration;
import java.time.Instant;

public class PortfolioExperiment {
    static String dir = "C:\\GitHubProjects\\data\\";
    static String subDir = "iSharesETF";
    static String resultDir = "C:\\GitHubProjects\\result\\";
    static String symbols = "VHT,QQQ,XLK,IVV,IJH,IJR,IVW,IJK,IJT,IVE,IJJ,IJS";
    static String myResultDir = getMyResultDir();

    static String resultFile = "iSharesETF";
    static String fileAppendix = TradingHelper.FILE_TYPE;


    static Float seedCost = 1000.0f;
    static int recordCount = 1300;
    static int minRecordCount = 300;
    static int resultLimit = 5000;
    static boolean collect0TradeDayResults = false;

    public static void setSymbolsFileName(String symbols, String dir, String resultFile, int resultLimit) {
        PortfolioExperiment.symbols = symbols;
        PortfolioExperiment.resultFile = resultFile;
        PortfolioExperiment.subDir = dir;
        PortfolioExperiment.resultLimit = resultLimit;
        PortfolioExperiment.myResultDir = getMyResultDir();
    }

    public static void run () {
        Instant start = Instant.now();
        runControl();
        equalSplitLossMajorTrue();
        equalSplitLossMajorfalse();
        pairswapLossMajorTrue();
        pairswapLossMajorFalse();
        Instant end = Instant.now();
        long timeElapsed = Duration.between(start, end).toSeconds();
        System.out.printf("finished in %d seconds\n", timeElapsed);
    }

    private static void runControl() {
        FloatRange gapRange = null;
        FloatRange powerRange = null;
        boolean isLossMajor = true;
        String runType = "_control";

        FullPairExperiment f = new FullPairExperiment(dir, symbols, seedCost, recordCount, minRecordCount, gapRange, powerRange, isLossMajor);
        f.setResultLimit(resultLimit);
        f.runControl();
        f.saveResult(myResultDir, myResultDir + "\\" + resultFile + runType + fileAppendix);
    }


    private static void equalSplitLossMajorTrue() {
        FloatRange gapRange = new FloatRange(0.025f, 0.11f, 0.025f);
        FloatRange powerRange = new FloatRange(0.0f, 1.1f, 4.0f);
        boolean isLossMajor = true;
        String runType = "_equalSplit_" + isLossMajor;

        FullPairExperiment f = new FullPairExperiment(dir, symbols, seedCost, recordCount, minRecordCount, gapRange, powerRange, isLossMajor);
        f.setResultLimit(resultLimit);
        f.setCollect0TradeDayResults(collect0TradeDayResults);
        f.run(TradingAlogirthm.RATIO_SPLIT);
        f.saveResult(myResultDir, myResultDir + "\\" + resultFile + runType + fileAppendix);
    }

    private static void equalSplitLossMajorfalse() {
        FloatRange gapRange = new FloatRange(0.025f, 0.11f, 0.025f);
        FloatRange powerRange = new FloatRange(0.0f, 1.1f, 4.0f);
        boolean isLossMajor = false;
        String runType = "_equalSplit_" + isLossMajor;

        FullPairExperiment f = new FullPairExperiment(dir, symbols, seedCost, recordCount, minRecordCount, gapRange, powerRange, isLossMajor);
        f.setResultLimit(resultLimit);
        f.setCollect0TradeDayResults(collect0TradeDayResults);
        f.run(TradingAlogirthm.RATIO_SPLIT);
        f.saveResult(myResultDir, myResultDir + "\\" + resultFile + runType + fileAppendix);
    }

    private static void pairswapLossMajorTrue() {
        FloatRange gapRange = new FloatRange(0.025f, 0.11f, 0.025f);
        FloatRange powerRange = new FloatRange(0.0f, 1.1f, 4.0f);
        boolean isLossMajor = true;
        String runType = "_pairswap_" + isLossMajor;

        FullPairExperiment f = new FullPairExperiment(dir, symbols, seedCost, recordCount, minRecordCount, gapRange, powerRange, isLossMajor);
        f.setResultLimit(resultLimit);
        f.setCollect0TradeDayResults(collect0TradeDayResults);
        f.run(TradingAlogirthm.PAIR_SWAP);
        f.saveResult(myResultDir, myResultDir + "\\" + resultFile + runType + fileAppendix);
    }

    private static void pairswapLossMajorFalse() {
        FloatRange gapRange = new FloatRange(0.025f, 0.11f, 0.025f);
        FloatRange powerRange = new FloatRange(0.0f, 1.1f, 4.0f);
        boolean isLossMajor = false;
        String runType = "_pairswap_" + isLossMajor;

        FullPairExperiment f = new FullPairExperiment(dir, symbols, seedCost, recordCount, minRecordCount, gapRange, powerRange, isLossMajor);
        f.setResultLimit(resultLimit);
        f.setCollect0TradeDayResults(collect0TradeDayResults);
        f.run(TradingAlogirthm.PAIR_SWAP);
        f.saveResult(myResultDir, myResultDir + "\\" + resultFile + runType + fileAppendix);
    }

    public static void setCollect0TradeDayResults(boolean collect0TradeDayResults) {
        PortfolioExperiment.collect0TradeDayResults = collect0TradeDayResults;
    }

    private static String getMyResultDir() {
        return resultDir + "\\" + subDir;
    }
}
