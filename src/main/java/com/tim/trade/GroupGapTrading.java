package com.tim.trade;

import com.tim.parser.DailyQuote;

import java.util.List;

public class GroupGapTrading extends GroupTrading {
    Float gapSize;

    @Override
    public void executeGroupTrade() {
        int days = tradings.get(0).getQuotes().size();
        int equities = tradings.size();
        tradings.forEach(i -> i.getTrades().clear());
        for (int day = 0; day < days; day++) {
            Float totalEquity = 0.0f;
            Float minEquity = (float) Math.pow(2, 30);
            Float maxEquity = 0.0f;
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
                totalEquity += equityAmount;
                if (equityAmount < minEquity ) {
                    minEquity = equityAmount;
                }
                if (maxEquity < equityAmount) {
                    maxEquity = equityAmount;
                }
                Trade trade = new Trade(q.getDate(), "", q.getClose(), shares, equityAmount);
                List<Trade> trades = t.getTrades();
                trades.add(trade);
            }
            Float avgEquity = totalEquity / equities;
            if (Math.abs(maxEquity - minEquity) > gapSize * avgEquity) {
                String date = tradings.get(0).getQuotes().get(day).getStringDate();
                groupTradeDays.add(date);
                for (int equity = 0; equity < equities; equity++) {
                    Trading t = tradings.get(equity);
                    DailyQuote q =t.getQuotes().get(day);
                    Trade trade = t.getTrades().get(day);
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
