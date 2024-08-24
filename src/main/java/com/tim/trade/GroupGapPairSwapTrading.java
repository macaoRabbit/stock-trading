package com.tim.trade;

import com.tim.parser.DailyQuote;
import com.tim.utility.GapDetails;
import com.tim.utility.IndexRatio;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class GroupGapPairSwapTrading extends GroupGapRatioTrading {
    @Override
    public GapDetails findEquityGap(int equities, int day, TreeMap<Float, Trading> tradingMap, Boolean isLossMajor) {
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
            int lastTradeIndex = getLastTradeIndex();
            Float equityRatio = equityAmount / trades.get(lastTradeIndex).getCost();
            if (tradingMap.containsKey(equityRatio)) {
                equityRatio = addSmallAmount(equityRatio);
            }
            tradingMap.put(equityRatio, t);
            IndexRatio r = new IndexRatio(equity, equityRatio);
            if (isLossMajor) {
                if (isZeroShare(shares) && equityRatio < minEquityRatio) {
                    minEquityRatio = equityRatio;
                    mins.add(r);
                }
                if (!isZeroShare(shares) && maxEquityRatio < equityRatio) {
                    maxEquityRatio = equityRatio;
                    maxs.add(r);
                }
            } else {
                if (!isZeroShare(shares) && equityRatio < minEquityRatio) {
                    minEquityRatio = equityRatio;
                    mins.add(r);
                }
                if (isZeroShare(shares) && maxEquityRatio < equityRatio) {
                    maxEquityRatio = equityRatio;
                    maxs.add(r);
                }
            }
        }
        myGap = maxEquityRatio - minEquityRatio;
        GapDetails g = new GapDetails(myGap, mins, maxs);
        return g;
    }

    @Override
    public void equityReallocation(GapDetails gapDetails, int day, TreeMap<Float, Trading> tradingMap, int equities, Boolean isLossMajor) {
        if (isLossMajor) {
            swapTrades(day, gapDetails.getMaxs(), gapDetails.getMins());
        } else {
            swapTrades(day, gapDetails.getMins(), gapDetails.getMaxs());
        }
    }

    private void swapTrades(int day, List<IndexRatio> fTrades, List<IndexRatio> tTrades) {
        Float myGap = this.getGapSize();
        List<IndexRatio> fromTrades = sortTrades(fTrades);
        List<IndexRatio> toTrades = sortTrades(tTrades);
        this.getLossMajor();
        int i = 0;
        int j = toTrades.size() - 1;
        Float diff = fromTrades.get(i).getRatio() - toTrades.get(j).getRatio();
        if (!this.getLossMajor()) {
            diff = -1.0f * diff;
        }
        while (i < fromTrades.size() && j >= 0 && diff > myGap) {
            Trade fromTrade = tradings.get(fromTrades.get(i).getIndex()).getTrades().get(day);
            Trade toTrade = tradings.get(toTrades.get(j).getIndex()).getTrades().get(day);
            swapTrade(fromTrade, toTrade);
            i++;
            j--;
        }
    }

    private List<IndexRatio> sortTrades(List<IndexRatio> tTrades) {
        TreeMap<Float, IndexRatio> m = new TreeMap<>();
        tTrades.forEach(t -> {
            m.put(t.getRatio(), t);
        });
        return m.values().stream().collect(Collectors.toList());
    }

    private void swapTrade(Trade fromTrade, Trade toTrade) {
        Float equityAmount = fromTrade.getCost();
        fromTrade.setCost(equityAmount / fromTrade.getShares());
        fromTrade.setShares(0.0f);
        Float sharePrice = toTrade.getCost();
        toTrade.setShares(equityAmount / sharePrice);
        toTrade.setCost(equityAmount);
    }
}
