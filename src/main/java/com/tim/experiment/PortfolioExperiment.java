package com.tim.experiment;

import com.tim.utility.FloatRange;
import com.tim.utility.FullExperiment;
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

    static FullExperiment experiment = FullExperiment.Pair;
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
        ratioSplitLossMajorTrue();
        ratioSplitLossMajorfalse();
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

        FullPairExperiment f = getFullExperiment(gapRange, powerRange, isLossMajor);
        f.setResultLimit(resultLimit);
        f.runControl();
        f.saveResult(myResultDir, myResultDir + "\\" + resultFile + runType + fileAppendix);
    }


    private static void ratioSplitLossMajorTrue() {
        FloatRange gapRange = new FloatRange(0.025f, 0.11f, 0.025f);
        FloatRange powerRange = new FloatRange(0.0f, 5.1f, 4.0f);
        boolean isLossMajor = true;
        String runType = "_ratioSplit_" + isLossMajor;

        FullPairExperiment f = getFullExperiment(gapRange, powerRange, isLossMajor);
        f.setResultLimit(resultLimit);
        f.setCollect0TradeDayResults(collect0TradeDayResults);
        f.run(TradingAlogirthm.RATIO_SPLIT);
        f.saveResult(myResultDir, myResultDir + "\\" + resultFile + runType + fileAppendix);
    }

    private static void ratioSplitLossMajorfalse() {
        FloatRange gapRange = new FloatRange(0.025f, 0.11f, 0.025f);
        FloatRange powerRange = new FloatRange(0.0f, 5.1f, 4.0f);
        boolean isLossMajor = false;
        String runType = "_ratioSplit_" + isLossMajor;

        FullPairExperiment f = getFullExperiment(gapRange, powerRange, isLossMajor);
        f.setResultLimit(resultLimit);
        f.setCollect0TradeDayResults(collect0TradeDayResults);
        f.run(TradingAlogirthm.RATIO_SPLIT);
        f.saveResult(myResultDir, myResultDir + "\\" + resultFile + runType + fileAppendix);
    }

    private static void pairswapLossMajorTrue() {
        FloatRange gapRange = new FloatRange(0.025f, 0.11f, 0.025f);
        FloatRange powerRange = new FloatRange(0.0f, 5.1f, 4.0f);
        boolean isLossMajor = true;
        String runType = "_pairswap_" + isLossMajor;

        FullPairExperiment f = getFullExperiment(gapRange, powerRange, isLossMajor);
        f.setResultLimit(resultLimit);
        f.setCollect0TradeDayResults(collect0TradeDayResults);
        f.run(TradingAlogirthm.PAIR_SWAP);
        f.saveResult(myResultDir, myResultDir + "\\" + resultFile + runType + fileAppendix);
    }

    private static void pairswapLossMajorFalse() {
        FloatRange gapRange = new FloatRange(0.025f, 0.11f, 0.025f);
        FloatRange powerRange = new FloatRange(0.0f, 5.1f, 4.0f);
        boolean isLossMajor = false;
        String runType = "_pairswap_" + isLossMajor;

        FullPairExperiment f = getFullExperiment(gapRange, powerRange, isLossMajor);
        f.setResultLimit(resultLimit);
        f.setCollect0TradeDayResults(collect0TradeDayResults);
        f.run(TradingAlogirthm.PAIR_SWAP);
        f.saveResult(myResultDir, myResultDir + "\\" + resultFile + runType + fileAppendix);
    }

    private static FullPairExperiment getFullExperiment(FloatRange gapRange, FloatRange powerRange, boolean isLossMajor) {
        switch(experiment) {
            case Pair:
                return new FullPairExperiment(dir, symbols, seedCost, recordCount, minRecordCount, gapRange, powerRange, isLossMajor);
            case Trio:
                return new FullTrioExperiment(dir, symbols, seedCost, recordCount, minRecordCount, gapRange, powerRange, isLossMajor);
            case Quad:
                return new FullQuadExperiment(dir, symbols, seedCost, recordCount, minRecordCount, gapRange, powerRange, isLossMajor);
        }
        return new FullPairExperiment(dir, symbols, seedCost, recordCount, minRecordCount, gapRange, powerRange, isLossMajor);
    }

    public static void setCollect0TradeDayResults(boolean collect0TradeDayResults) {
        PortfolioExperiment.collect0TradeDayResults = collect0TradeDayResults;
    }

    private static String getMyResultDir() {
        return resultDir + "\\" + subDir;
    }


    public static FullExperiment getExperiment() {
        return experiment;
    }

    public static void setExperiment(FullExperiment experiment) {
        PortfolioExperiment.experiment = experiment;
    }
}
