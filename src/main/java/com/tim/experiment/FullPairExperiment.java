package com.tim.experiment;

import com.tim.result.GroupTradeResult;
import com.tim.trade.ControlTrading;
import com.tim.trade.GroupControlTrading;
import com.tim.trade.GroupTradeDayGapRatioTrading;
import com.tim.trade.Trading;
import com.tim.utility.FloatRange;
import com.tim.utility.TradingAlgorithm;
import com.tim.utility.TradingHelper;

import java.util.ArrayList;
import java.util.List;

public class FullPairExperiment {
    List<Trading> tradings = new ArrayList<>();
    FloatRange gapRange = new FloatRange(0.0f, 0.2f, 0.025f);
    FloatRange powerRange = new FloatRange(0.0f, 5.0f, 1.0f);
    List<GroupTradeResult> results = new ArrayList<>();
    Integer resultLimit = 1000;
    boolean isLossMajor = true;
    Float seedCost = 0.0f;

    boolean collect0TradeDayResults = false;
    String resultDir = null;
    String resultFile = null;
    Integer runCount = 0;

    public FullPairExperiment(List<Trading> tradings) {
        this.tradings = tradings;
    }

    public FullPairExperiment(List<Trading> tradings, FloatRange gapRange, FloatRange powerRange, boolean isLossMajor) {
        this.tradings = tradings;
        this.gapRange = gapRange;
        this.powerRange = powerRange;
        this.isLossMajor = isLossMajor;
    }

    public FullPairExperiment(String dir, String symbols, Float seedCost, int recordCount, int minRecordCount, FloatRange gapRange, FloatRange powerRange, boolean isLossMajor) {
        List<Trading> tradings = TradingHelper.generate(dir, symbols, seedCost, recordCount, minRecordCount);
        this.tradings = tradings;
        this.gapRange = gapRange;
        this.powerRange = powerRange;
        this.isLossMajor = isLossMajor;
        this.seedCost = seedCost;
    }

    public List<GroupTradeResult> run(TradingAlgorithm tradingAlgorithm) {
        results.clear();
        for (int i = 0; i < tradings.size(); i++) {
            for (int j = i + 1; j < tradings.size(); j++) {
                List<Trading> thisTradingGroup = new ArrayList<>();
                Trading t1 = tradings.get(i);
                Trading t2 = tradings.get(j);
                thisTradingGroup.add(t1);
                thisTradingGroup.add(t2);

                runJustOne(tradingAlgorithm, thisTradingGroup);
            }
        }
        return results;
    }

    public void runJustOne(TradingAlgorithm tradingAlgorithm, List<Trading> thisTradingGroup) {
        List<Boolean> booleans = new ArrayList<>();
        booleans.add(true);
        booleans.add(false);

        GroupControlTrading c = new GroupControlTrading();
        c.getTradings().addAll(thisTradingGroup);
        TradingAlgorithm.manageSeedCost(c.getTradings(), TradingAlgorithm.CONTROL, seedCost);
        c.initQuotesWithCsvFileForAllTradings();
        c.matchQuotesForAllTradings();
        c.analyze();
        Float controlReturn = c.getAnnualizedReturn();

        for (Boolean b : booleans) {
            GroupTradeDayGapRatioTrading g = TradingAlgorithm.getAlgorithm(tradingAlgorithm);
            g.setAnyDayGap(b);
            runExperiment(tradingAlgorithm, g, thisTradingGroup, controlReturn);
            enforceResultLimit();
            System.out.println("Finished processing " + g.getSymbolList() + " " + " results=" + String.format("%8d", results.size()));
        }
    }

    private void runExperiment(TradingAlgorithm tradingAlgorithm, GroupTradeDayGapRatioTrading g, List<Trading> thisTradingGroup, Float controlReturn) {
        g.getTradings().addAll(thisTradingGroup);
        switch (tradingAlgorithm) {
            case RATIO_SPLIT:
                TradingAlgorithm.manageSeedCost(g.getTradings(), tradingAlgorithm, seedCost);
                runNow(g, controlReturn);
                break;
            case PAIR_SWAP:
                for (int index = 0; index < thisTradingGroup.size(); index++) {
                    TradingAlgorithm.manageSeedCost(index, g.getTradings(), tradingAlgorithm, seedCost);
                    Float thisControlReturn = getThisControlReturn(g, index);
                    runNow(g, thisControlReturn);
                }
                break;
        }
    }


    public void runControl() {
        results.clear();
        for (int i = 0; i < tradings.size(); i++) {
            Trading t = tradings.get(i);
            ControlTrading c = new ControlTrading(t.getQuotes(), t.getDailyQuoteDataPath(), t.getSeedCost());
            c.executeTrade();
            c.analyze();
            GroupTradeResult r = c.collectResult();
            results.add(r);
        }
    }

    public static Float getThisControlReturn(GroupTradeDayGapRatioTrading g, int index) {
        Trading t = g.getTradings().get(index);
        ControlTrading c = new ControlTrading(t.getQuotes(), t.getDailyQuoteDataPath(), t.getSeedCost());
        c.executeTrade();
        c.analyze();
        Float thisControlReturn = c.getAnnualizedReturn();
        return thisControlReturn;
    }

    private void runNow(GroupTradeDayGapRatioTrading g, Float controlReturn) {
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();

        GroupTradeDayGapRatioTradingExperiment e = new GroupTradeDayGapRatioTradingExperiment(g, gapRange, powerRange, controlReturn, results, isLossMajor);
        e.setCollect0TradeDayResults(collect0TradeDayResults);
        e.run();
    }

    private void enforceResultLimit() {
        boolean truncated = new GroupTradeResult().setLimit(results, resultLimit);
        if (truncated && resultDir != null && resultFile != null) {
            saveResult(resultDir, resultFile);
        }
    }

    public void processResult() {
        new GroupTradeResult().process(results);
    }
    public List<Trading> getTradings() {
        return tradings;
    }

    public FloatRange getGapRange() {
        return gapRange;
    }

    public void setGapRange(FloatRange gapRange) {
        this.gapRange = gapRange;
    }

    public FloatRange getPowerRange() {
        return powerRange;
    }

    public void setPowerRange(FloatRange powerRange) {
        this.powerRange = powerRange;
    }

    public void setTradings(List<Trading> tradings) {
        this.tradings = tradings;
    }

    public List<GroupTradeResult> getResults() {
        return results;
    }

    public void setResults(List<GroupTradeResult> results) {
        this.results = results;
    }

    public Integer getResultLimit() {
        return resultLimit;
    }

    public void setResultLimit(Integer resultLimit) {
        this.resultLimit = resultLimit;
    }

    public void saveResult(String saveFile) {
        new GroupTradeResult().save(results, saveFile);
    }

    public void saveResult(String dir, String saveFile) {
        new GroupTradeResult().save(results, dir, saveFile);
    }

    public boolean isLossMajor() {
        return isLossMajor;
    }

    public void setLossMajor(boolean lossMajor) {
        isLossMajor = lossMajor;
    }

    public Float getSeedCost() {
        return seedCost;
    }

    public void setSeedCost(Float seedCost) {
        this.seedCost = seedCost;
    }

    public void setCollect0TradeDayResults(boolean collect0TradeDayResults) {
        this.collect0TradeDayResults = collect0TradeDayResults;
    }

    public String getResultDir() {
        return resultDir;
    }

    public void setResultDir(String resultDir) {
        this.resultDir = resultDir;
    }

    public String getResultFile() {
        return resultFile;
    }

    public void setResultFile(String resultFile) {
        this.resultFile = resultFile;
    }

    public Integer getRunCount() {
        return runCount;
    }

    public void setRunCount(Integer runCount) {
        this.runCount = runCount;
    }
}
