package com.tim.trade;

import com.tim.parser.DailyQuote;

import java.util.List;

public class GroupGapFromAverageTrading extends GroupTrading {
    Float gapSize;

    @Override
    public void executeGroupTrade() {
        int days = tradings.get(0).getQuotes().size();
        int equities = tradings.size();
        tradings.forEach(i -> i.getTrades().clear());
        for (int i = 0; i < days; i++) {
            Float totalEquity = 0.0f;
            Float minEquity = (float) Math.pow(2, 30);
            Float maxEquity = 0.0f;
            for (int j = 0; j < equities; j++) {
                Trading t = tradings.get(i);
                DailyQuote q =t.getQuotes().get(i);
                Float shares = 0.0f;
                if (i == 0) {
                    shares = t.getSeedCost()/q.getClose();
                } else {
                    shares = t.getTrades().get(i - 1).shares;
                }
                Float equity = shares * q.getClose();
                totalEquity += equity;
                if (equity < minEquity ) {
                    minEquity = equity;
                }
                if (maxEquity < equity) {
                    maxEquity = equity;
                }
                Trade trade = new Trade(q.getDate(), "", q.getClose(), shares, equity);
                List<Trade> trades = t.getTrades();
                trades.add(trade);
            }
            Float avgEquity = totalEquity / equities;
            if (Math.abs(maxEquity - minEquity) > gapSize * avgEquity) {
                for (int j = 0; j < equities; j++) {
                    Trading t = tradings.get(i);
                    DailyQuote q =t.getQuotes().get(i);
                    Trade trade = t.getTrades().get(i);
                    trade.setShares(avgEquity/q.getClose());
                    trade.setCost(avgEquity);
                }
            }
        }
    }
    public Float getGapSize() {
        return gapSize;
    }

    public void setGapSize(Float gapSize) {
        this.gapSize = gapSize;
    }
}
