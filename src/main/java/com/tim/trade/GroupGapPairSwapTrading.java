package com.tim.trade;

import com.tim.parser.DailyQuote;
import com.tim.utility.GapDetails;

import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class GroupGapPairSwapTrading extends GroupGapRatioTrading {
    @Override
    public GapDetails findEquityGap(int equities, int day, TreeMap<Float, Trading> tradingMap, Boolean isLossMajor) {
        Float minEquityRatio = (float) Math.pow(2, 30);
        Float maxEquityRatio = 0.0f;
        Float myGap = 0.0f;
        Integer minIndex = 0;
        Integer maxIndex = 0;
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
            if (isZeroShare(shares)) {
                equityAmount = q.getClose();
            }
            Trade trade = new Trade(q.getDate(), "", q.getClose(), shares, equityAmount, q.getStringDate());
            List<Trade> trades = t.getTrades();
            trades.add(trade);
            int lastTradeIndex = getLastTradeIndex();
            Float equityRatio = equityAmount/trades.get(lastTradeIndex).getCost();
            if (tradingMap.containsKey(equityRatio)) {
                equityRatio = addSmallAmount(equityRatio);
            }
            tradingMap.put(equityRatio, t);
            if (isLossMajor) {
                if (isZeroShare(shares) && equityRatio < minEquityRatio) {
                    minEquityRatio = equityRatio;
                    minIndex = equity;
                }
                if (!isZeroShare(shares) && maxEquityRatio < equityRatio) {
                    maxEquityRatio = equityRatio;
                    maxIndex = equity;
                }
            } else {
                if (!isZeroShare(shares) && equityRatio < minEquityRatio) {
                    minEquityRatio = equityRatio;
                    minIndex = equity;
                }
                if (isZeroShare(shares) && maxEquityRatio < equityRatio) {
                    maxEquityRatio = equityRatio;
                    maxIndex = equity;
                }
            }
            myGap = maxEquityRatio - minEquityRatio;
        }
        GapDetails g = new GapDetails(myGap, minIndex, maxIndex);
        return g;
    }

    @Override
    public void equityReallocation(GapDetails gapDetails, int day, TreeMap<Float, Trading> tradingMap, int equities, Boolean isLossMajor) {
        Trade minTrade = tradings.get(gapDetails.getMinIndex()).getTrades().get(day);
        Trade maxTrade = tradings.get(gapDetails.getMaxIndex()).getTrades().get(day);
        if (isLossMajor) {
            swapTrade(maxTrade, minTrade);
        } else {
            swapTrade(maxTrade, minTrade);
        }
    }

    private void swapTrade(Trade fromTrade, Trade toTrade) {
        Float equityAmount = fromTrade.getCost();
        fromTrade.setCost(equityAmount/fromTrade.getShares());
        fromTrade.setShares(0.0f);
        Float sharePrice = toTrade.getCost();
        toTrade.setShares(equityAmount/sharePrice);
        toTrade.setCost(equityAmount);
    }
}
