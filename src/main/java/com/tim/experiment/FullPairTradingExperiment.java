package com.tim.experiment;

import com.tim.result.GroupTradeResult;
import com.tim.trade.GroupControlTrading;
import com.tim.trade.GroupGapRatioTrading;
import com.tim.trade.Trading;
import com.tim.utility.FloatRange;
import com.tim.utility.TradingAlogirthm;
import com.tim.utility.TradingHelper;

import java.util.ArrayList;
import java.util.List;

public class FullPairTradingExperiment {
    List<Trading> tradings = new ArrayList<>();
    FloatRange gapRange = new FloatRange(0.0f, 0.2f, 0.025f);
    FloatRange powerRange = new FloatRange(0.0f, 5.0f, 1.0f);
    List<GroupTradeResult> results = new ArrayList<>();
    Integer resultLimit = 1000;
    boolean isLossMajor = true;
    Float seedCost = 0.0f;

    public FullPairTradingExperiment(List<Trading> tradings) {
        this.tradings = tradings;
    }

    public FullPairTradingExperiment(List<Trading> tradings, FloatRange gapRange, FloatRange powerRange, boolean isLossMajor) {
        this.tradings = tradings;
        this.gapRange = gapRange;
        this.powerRange = powerRange;
        this.isLossMajor = isLossMajor;
    }

    public FullPairTradingExperiment(String dir, String symbols, Float seedCost, int recordCount, int minRecordCount, FloatRange gapRange, FloatRange powerRange, boolean isLossMajor) {
        List<Trading> tradings = TradingHelper.generate(dir, symbols, seedCost, recordCount, minRecordCount);
        this.tradings = tradings;
        this.gapRange = gapRange;
        this.powerRange = powerRange;
        this.isLossMajor = isLossMajor;
        this.seedCost = seedCost;
    }

    public List<GroupTradeResult> run(TradingAlogirthm tradingAlogirthm) {
        results.clear();
        for (int i = 0; i < tradings.size(); i++) {
            for (int j = i + 1; j < tradings.size(); j++) {
                Trading t1 = tradings.get(i);
                Trading t2 = tradings.get(j);

                GroupControlTrading c = new GroupControlTrading();
                c.getTradings().add(t1);
                c.getTradings().add(t2);
                TradingAlogirthm.manageSeedCost(c.getTradings(), TradingAlogirthm.CONTROL, seedCost);
                c.initQuotesWithCsvFileForAllTradings();
                c.matchQuotesForAllTradings();
                c.analyze();
                Float controlReturn = c.getAnnualizedReturn();

                GroupGapRatioTrading g = TradingAlogirthm.getAlgorithm(tradingAlogirthm);
                g.getTradings().add(t1);
                g.getTradings().add(t2);
                TradingAlogirthm.manageSeedCost(g.getTradings(), tradingAlogirthm, seedCost);

                g.initQuotesWithCsvFileForAllTradings();
                g.matchQuotesForAllTradings();

                GroupGapRatioTradingExperiment e = new GroupGapRatioTradingExperiment(g, gapRange, powerRange, controlReturn, results, isLossMajor);
                e.run();
                enforceResultLimit();
                System.out.println("Finished processing " + g.getSymbolList() + " " + " results=" + String.format("%8d", results.size()));
            }
        }
        return results;
    }

    private void enforceResultLimit() {
        new GroupTradeResult().setLimit(results, resultLimit);
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
}
