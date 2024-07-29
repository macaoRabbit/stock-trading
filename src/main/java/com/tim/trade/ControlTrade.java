package com.tim.trade;

import java.util.List;

public class ControlTrade extends Trading {

    public ControlTrade(String dailyQuoteDataPath, Float seedCost) {
        super(dailyQuoteDataPath, seedCost);
    }

    @Override
    public List<Trade> execute() {
        Float shares = seedCost/quotes.get(0).getClose();
        quotes.forEach(i -> {
            Trade t = new Trade(i.getDate(), "", i.getClose(), shares, shares * i.getClose());
            trades.add(t);
        });
        return trades;
    }
}
