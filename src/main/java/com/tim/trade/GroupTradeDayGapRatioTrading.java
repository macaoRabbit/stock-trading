package com.tim.trade;

import com.tim.parser.DailyQuote;
import com.tim.result.GroupTradeResult;
import com.tim.result.GroupTradeResultItem;
import com.tim.result.ReturnItemType;
import com.tim.utility.GapDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class GroupTradeDayGapRatioTrading extends GroupTrading {
    Float gapSize = 0.05f;
    Float splitRatioPower = 0.00f;
    Boolean isLossMajor = true;
    List<Float> splitRatio = new ArrayList<>();
    Float currentGap = 0.0f;
    Float gapDiff = 0.0f;
    List<GapDetails> dailyGaps = new ArrayList<>();

    @Override
    public void executeGroupTrade() {
        groupTradeDayIndex.clear();
        int days = tradings.get(0).getQuotes().size();
        int equities = tradings.size();
        tradings.forEach(i -> i.getTrades().clear());
        TreeMap<Float, Trading> tradingMap = new TreeMap<>();
        for (int day = 0; day < days; day++) {
            String stringDay = tradings.get(0).getQuotes().get(day).getStringDate();
            tradingMap.clear();
            int lastTradeIndex = getLastTradeIndex();
            GapDetails gapDetails = findEquityGap(equities, day, tradingMap, isLossMajor, lastTradeIndex);
            dailyGaps.add(gapDetails);
            gapDiff = gapDetails.getGap() - gapSize;
            if (gapDetails.getGap() > gapSize) {
                addTradDay(day);
                equityReallocation(gapDetails, day, tradingMap, equities, isLossMajor);
            }
        }
    }

    public void equityReallocation(GapDetails gapDetails, int day, TreeMap<Float, Trading> tradingMap, int equities, Boolean isLossMajor) {
        List<Float> mySplitRatioList = getCurrentSplitRatio(gapDetails);
        splitRatioEquityAllocation(day, tradingMap, equities, mySplitRatioList);
    }

    public List<Float> getCurrentSplitRatio(GapDetails gapDetails) {
        return getSplitRatio();
    }

    public void splitRatioEquityAllocation(int day, TreeMap<Float, Trading> tradingMap, int equities, List<Float> mySplitRatioList) {
        int aDay = day;
        Float totalEquity = (float) tradings.stream().mapToDouble(i -> i.getTrades().get(aDay).getCost()).sum();
        int ratioIndex = 0;
        for (Trading t : tradingMap.values()) {
            int myRatioIndex = ratioIndex;
            if (getLossMajor()) {
                myRatioIndex = equities - ratioIndex - 1;
            }
            Float mySplitRatio = mySplitRatioList.get(myRatioIndex);
            Float myEquity = totalEquity * mySplitRatio;
            DailyQuote q = t.getQuotes().get(day);
            Trade trade = t.getTrades().get(day);
            trade.setShares(myEquity / q.getClose());
            trade.setCost(myEquity);
            ratioIndex++;
        }
    }

    private void addTradDay(int day) {
        String date = tradings.get(0).getQuotes().get(day).getStringDate();
        groupTradeDays.add(date);
        groupTradeDayIndex.add(day);
    }

    public GapDetails findEquityGap(int equities, int day, TreeMap<Float, Trading> tradingMap, Boolean isLossMajor, int lastTradeIndex) {
        Float minEquityRatio = (float) Math.pow(2, 30);
        Float maxEquityRatio = 0.0f;
        for (int equity = 0; equity < equities; equity++) {
            Trading t = tradings.get(equity);
            DailyQuote q = t.getQuotes().get(day);
            Float shares = 0.0f;
            if (day == 0) {
                shares = t.getSeedCost() / q.getClose();
            } else {
                shares = t.getTrades().get(day - 1).shares;
            }
            Float equityAmount = shares * q.getClose();
            Trade trade = new Trade(q.getDate(), "", q.getClose(), shares, equityAmount, q.getStringDate());
            List<Trade> trades = t.getTrades();
            trades.add(trade);
            Float equityRatio = equityAmount / trades.get(lastTradeIndex).getCost();
            if (tradingMap.containsKey(equityRatio)) {
                equityRatio = addSmallAmount(equityRatio);
            }
            tradingMap.put(equityRatio, t);
            if (equityRatio < minEquityRatio) {
                minEquityRatio = equityRatio;
            }
            if (maxEquityRatio < equityRatio) {
                maxEquityRatio = equityRatio;
            }
        }
        Float thisGap = Math.abs(maxEquityRatio - minEquityRatio);
        GapDetails g = new GapDetails(thisGap, null, null);
        return g;
    }

    public int getLastTradeIndex() {
        if (groupTradeDayIndex == null || groupTradeDayIndex.size() == 0) {
            return 0;
        }
        int i = groupTradeDayIndex.size() - 1;
        return groupTradeDayIndex.get(i);
    }

    public void setupSplitRatio(List<Float> splitRatios) {
        this.splitRatio = splitRatios;
    }

    public void setupSplitRatio() {
        splitRatio.clear();
        for (int i = 0; i < tradings.size(); i++) {
            splitRatio.add((float) Math.pow(i + 1, splitRatioPower));
        }
        Float base = (float) splitRatio.stream().mapToDouble(i -> i.floatValue()).sum();
        for (int i = 0; i < tradings.size(); i++) {
            Float ratio = splitRatio.get(i);
            splitRatio.set(i, ratio / base);
        }
    }

    @Override
    public void clear() {
        splitRatio.clear();
        super.clear();
    }

    @Override
    public void reportSummary() {
        reportDetails();
        super.reportSummary();
    }

    @Override
    public void report() {
        reportDetails();
        super.report();
    }

    private void reportDetails() {
        System.out.printf("GapSize: %7.3f", gapSize);
        System.out.printf(" SplitRatioPoswer: %7.2f", splitRatioPower);
        System.out.printf(" isLossMajor: %b", isLossMajor);
        for (Float r : splitRatio) {
            System.out.printf("%7.2f", r);
        }
        System.out.printf(" currentGap: %7.3f", currentGap);
        System.out.printf(" gapDiff: %7.3f", gapDiff);
        System.out.println();
    }

    @Override
    public GroupTradeResult collectResult() {
        GroupTradeResult r = super.collectResult();
        GroupTradeResultItem i1 = new GroupTradeResultItem("gap", String.format("%6.3f", gapSize), ReturnItemType.FloatType);
        GroupTradeResultItem i2 = new GroupTradeResultItem("ratioPower", String.format("%5.1f", splitRatioPower), ReturnItemType.FloatType);
        GroupTradeResultItem i3 = new GroupTradeResultItem("lossMajor", String.format("%6b", isLossMajor), ReturnItemType.BooleanType);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < splitRatio.size(); i++) {
            s.append(String.format("%.2f", splitRatio.get(i)));
            if (i < splitRatio.size() - 1) {
                s.append("--");
            }
        }
        GroupTradeResultItem i4 = new GroupTradeResultItem("ratio", s.toString(), ReturnItemType.StringType);
        GroupTradeResultItem i5 = new GroupTradeResultItem("currentGap", String.format("%7.3f", currentGap), ReturnItemType.FloatType);
        GroupTradeResultItem i6 = new GroupTradeResultItem("gapDiff", String.format("%7.3f", gapDiff), ReturnItemType.FloatType);
        r.getResults().add(i1);
        r.getResults().add(i2);
        r.getResults().add(i3);
        r.getResults().add(i4);
        r.getResults().add(i5);
        r.getResults().add(i6);
        return r;
    }

    public Float getGapSize() {
        return gapSize;
    }

    public void setGapSize(Float gapSize) {
        this.gapSize = gapSize;
    }

    public Float getSplitRatioPower() {
        return splitRatioPower;
    }

    public void setSplitRatioPower(Float splitRatioPower) {
        this.splitRatioPower = splitRatioPower;
    }

    public Boolean getLossMajor() {
        return isLossMajor;
    }

    public void setLossMajor(Boolean lossMajor) {
        isLossMajor = lossMajor;
    }

    public List<Float> getSplitRatio() {
        return splitRatio;
    }

    public void setSplitRatio(List<Float> splitRatio) {
        this.splitRatio = splitRatio;
    }

    public Float getCurrentGap() {
        return currentGap;
    }

    public void setCurrentGap(Float currentGap) {
        this.currentGap = currentGap;
    }

    public Float getGapDiff() {
        return gapDiff;
    }

    public void setGapDiff(Float gapDiff) {
        this.gapDiff = gapDiff;
    }

    public List<GapDetails> getDailyGaps() {
        return dailyGaps;
    }

    public void setDailyGaps(List<GapDetails> dailyGaps) {
        this.dailyGaps = dailyGaps;
    }


}
