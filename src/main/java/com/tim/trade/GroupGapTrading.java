package com.tim.trade;

import com.tim.parser.DailyQuote;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class GroupGapTrading extends GroupTrading {
    Float gapSize = 0.05f;
    Integer splitRatioPower = 0;
    Boolean isLossMajor = true;
    List<Float> splitRatio = new ArrayList<>();

    @Override
    public void executeGroupTrade() {
        groupTradeDayIndex.clear();
        int days = tradings.get(0).getQuotes().size();
        int equities = tradings.size();
        tradings.forEach(i -> i.getTrades().clear());
        TreeMap<Float, Trading> tradingMap = new TreeMap<>();
        for (int day = 0; day < days; day++) {
            tradingMap.clear();
            Float minEquityRatio = (float) Math.pow(2, 30);
            Float maxEquityRatio = 0.0f;
            for (int equity = 0; equity < equities; equity++) {
                Trading t = tradings.get(equity);
                DailyQuote q =t.getQuotes().get(day);
                Float shares = 0.0f;
                if (day == 0) {
                    shares = t.getSeedCost()/q.getClose();
                } else {
                    shares = t.getTrades().get(day - 1).shares;
                }
                Float equityAmount = shares * q.getClose();
                Trade trade = new Trade(q.getDate(), "", q.getClose(), shares, equityAmount);
                List<Trade> trades = t.getTrades();
                trades.add(trade);
                int lastTradeIndex = getLastTradeIndex();
                Float equityRatio = equityAmount/trades.get(lastTradeIndex).getCost();
                tradingMap.put(equityRatio, t);
                if (equityRatio < minEquityRatio ) {
                    minEquityRatio = equityRatio;
                }
                if (maxEquityRatio < equityRatio) {
                    maxEquityRatio = equityRatio;
                }
            }
            if (Math.abs(maxEquityRatio - minEquityRatio) > gapSize) {
                String date = tradings.get(0).getQuotes().get(day).getStringDate();
                groupTradeDays.add(date);
                groupTradeDayIndex.add(day);
                int aDay = day;
                Float totalEquity = (float) tradings.stream().mapToDouble(i -> i.getTrades().get(aDay).getCost()).sum();
                int ratioIndex = 0;
                for (Trading t : tradingMap.values()) {
                    int myRatioIndex = ratioIndex;
                    if (!getLossMajor()) {
                        myRatioIndex = equities - ratioIndex;
                    }
                    Float myEquity = totalEquity * splitRatio.get(myRatioIndex);
                    DailyQuote q =t.getQuotes().get(day);
                    Trade trade = t.getTrades().get(day);
                    trade.setShares(myEquity/q.getClose());
                    trade.setCost(myEquity);
                    ratioIndex++;
                }
            }
        }
    }

    private int getLastTradeIndex() {
        if (groupTradeDayIndex == null || groupTradeDayIndex.size() == 0) {
            return 0;
        }
        int i = groupTradeDayIndex.size() - 1;
        return groupTradeDayIndex.get(i);
    }

    public void setupSplitRatio() {
        splitRatio.clear();
        for (int i=0; i<tradings.size(); i++) {
           splitRatio.add((float) Math.pow(i + 1, splitRatioPower));
        }
        Float base = (float) splitRatio.stream().mapToDouble(i -> i.floatValue()).sum();
        for (int i=0; i<tradings.size(); i++) {
            Float ratio = splitRatio.get(i);
            splitRatio.set(i, ratio/base);
        }
    }

    public Float getGapSize() {
        return gapSize;
    }

    public void setGapSize(Float gapSize) {
        this.gapSize = gapSize;
    }

    public Integer getSplitRatioPower() {
        return splitRatioPower;
    }

    public void setSplitRatioPower(Integer splitRatioPower) {
        this.splitRatioPower = splitRatioPower;
    }

    public Boolean getLossMajor() {
        return isLossMajor;
    }

    public void setLossMajor(Boolean lossMajor) {
        isLossMajor = lossMajor;
    }
}
