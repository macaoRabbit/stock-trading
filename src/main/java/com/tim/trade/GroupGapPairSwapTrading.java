package com.tim.trade;

import com.tim.parser.DailyQuote;

import java.util.List;
import java.util.TreeMap;

public class GroupGapPairSwapTrading extends GroupGapRatioTrading {
    @Override
    public Float findEquityGap(int equities, int day, TreeMap<Float, Trading> tradingMap, Boolean isLossMajor) {
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
            if (shares.intValue() == 0) {
                equityAmount = q.getClose();
            }
            Trade trade = new Trade(q.getDate(), "", q.getClose(), shares, equityAmount, q.getStringDate());
            List<Trade> trades = t.getTrades();
            trades.add(trade);
            int lastTradeIndex = getLastTradeIndex();
            Float equityRatio = equityAmount/trades.get(lastTradeIndex).getCost();
            tradingMap.put(equityRatio, t);
            if (equityRatio < minEquityRatio) {
                minEquityRatio = equityRatio;
            }
            if (maxEquityRatio < equityRatio) {
                maxEquityRatio = equityRatio;
            }
        }
        Float currentGap = Math.abs(maxEquityRatio - minEquityRatio);
        return currentGap;
    }

    @Override
    public List<Float>  getCurrentSplitRatio() {
        return null;
    }
}
