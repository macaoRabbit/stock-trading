package com.tim.trade;

import com.tim.parser.DailyQuote;
import com.tim.result.GroupTradeResult;
import com.tim.result.GroupTradeResultItem;
import com.tim.result.ReturnItemType;
import com.tim.utility.GapDetails;
import com.tim.utility.IndexRatio;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class GroupTradeDayGapPairSwapTrading extends GroupTradeDayGapRatioTrading {
    @Override
    public GapDetails findEquityGap(int equities, int day, TreeMap<Float, Trading> tradingMap, Boolean isLossMajor, int gapDay) {
        Float minEquityRatio = (float) Math.pow(2, 20);
        Float maxEquityRatio = 0.0f;
        Float myGap = 0.0f;
        List<IndexRatio> mins = new ArrayList<>();
        List<IndexRatio> maxs = new ArrayList<>();
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
            if (isZeroShare(shares)) {
                equityAmount = q.getClose();
            }
            Trade trade = new Trade(q.getDate(), "", q.getClose(), shares, equityAmount, q.getStringDate());
            List<Trade> trades = t.getTrades();
            trades.add(trade);
            Float equityRatio = equityAmount / trades.get(gapDay).getCost();
            if (tradingMap.containsKey(equityRatio)) {
                equityRatio = addSmallAmount(equityRatio);
            }
            tradingMap.put(equityRatio, t);
            IndexRatio r = new IndexRatio(equity, equityRatio);
            if (isLossMajor) {
                if (isZeroShare(shares)) {
                    mins.add(r);
                    if (equityRatio < minEquityRatio) {
                        minEquityRatio = equityRatio;
                    }
                }
                if (!isZeroShare(shares)) {
                    maxs.add(r);
                    if (maxEquityRatio < equityRatio) {
                        maxEquityRatio = equityRatio;
                    }
                }
            } else {
                if (!isZeroShare(shares)) {
                    mins.add(r);
                    if (equityRatio < minEquityRatio) {
                        minEquityRatio = equityRatio;
                    }
                }
                if (isZeroShare(shares)) {
                    maxs.add(r);
                    if (maxEquityRatio < equityRatio) {
                        maxEquityRatio = equityRatio;
                    }
                }
            }
        }
        myGap = maxEquityRatio - minEquityRatio;
        GapDetails g = new GapDetails(myGap, mins, maxs);
        return g;
    }


    @Override
    public void equityReallocation(GapDetails gapDetails, int day, TreeMap<Float, Trading> tradingMap,
                                   int equities, Boolean isLossMajor) {
        if (isLossMajor) {
            swapTrades(day, gapDetails.getMaxs(), gapDetails.getMins());
        } else {
            swapTrades(day, gapDetails.getMins(), gapDetails.getMaxs());
        }
    }

    private void swapTrades(int day, List<IndexRatio> fTrades, List<IndexRatio> tTrades) {
        Float myGap = this.getGapSize();
        List<IndexRatio> fromTrades = sortTrades(fTrades, this.getLossMajor());
        List<IndexRatio> toTrades = sortTrades(tTrades, true);
        int i = 0;
        int j = toTrades.size() - 1;
        if (this.getLossMajor()) {
            while (i < fromTrades.size() && j >= 0 && findGapDiff(fromTrades, i, toTrades, j) > myGap) {
                doSwap(day, fromTrades, i, toTrades, j);
                i++;
                j--;
            }
        } else {
            j = 0;
            while (i < fromTrades.size() && j < toTrades.size() && findGapDiff(fromTrades, i, toTrades, j) > myGap) {
                doSwap(day, fromTrades, i, toTrades, j);
                i++;
                j++;
            }
        }
    }

    private void doSwap(int day, List<IndexRatio> fromTrades, int i, List<IndexRatio> toTrades, int j) {
        Trade fromTrade = tradings.get(fromTrades.get(i).getIndex()).getTrades().get(day);
        Trade toTrade = tradings.get(toTrades.get(j).getIndex()).getTrades().get(day);
        swapTrade(fromTrade, toTrade);
    }

    private List<IndexRatio> sortTrades(List<IndexRatio> tTrades, Boolean reverseOrder) {
        TreeMap<Float, IndexRatio> m = new TreeMap<>();
        tTrades.forEach(t -> {
            m.put(t.getRatio(), t);
        });
        if (reverseOrder) {
           return m.descendingMap().values().stream().collect(Collectors.toList());
        }
        return m.values().stream().collect(Collectors.toList());
    }

    private Float findGapDiff(List<IndexRatio> fromTrades, int i, List<IndexRatio> toTrades, int j) {
        Float diff = fromTrades.get(i).getRatio() - toTrades.get(j).getRatio();
        if (!this.getLossMajor()) {
            diff = -1.0f * diff;
        }
        return diff;
    }

    private void swapTrade(Trade fromTrade, Trade toTrade) {
        Float equityAmount = fromTrade.getCost();
        fromTrade.setCost(equityAmount / fromTrade.getShares());
        fromTrade.setShares(0.0f);
        Float sharePrice = toTrade.getCost();
        toTrade.setShares(equityAmount / sharePrice);
        toTrade.setCost(equityAmount);
    }

    @Override
    public GroupTradeResult collectResult() {
        GroupTradeResult r = super.collectResult();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < tradings.size(); i++) {
            s.append(String.format("%6.0f", tradings.get(i).getSeedCost()));
            if (i < splitRatio.size() - 1) {
                s.append("--");
            }
        }
        GroupTradeResultItem i1 = new GroupTradeResultItem("seedCostList", s.toString(), ReturnItemType.StringType);
        r.getResults().add(i1);
        return r;
    }
}
