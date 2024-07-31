package com.tim.trade;

import com.tim.parser.DailyQuote;

import java.util.List;

public class ControlTrade extends Trading {

    public ControlTrade(String dailyQuoteDataPath, Float seedCost) {
        super(dailyQuoteDataPath, seedCost);
    }

    public ControlTrade(List<DailyQuote> quotes, Float seedCost) {
        super(quotes, seedCost);
    }

    @Override
    public List<Trade> executeTrade() {
        Float shares = seedCost/quotes.get(0).getClose();
        quotes.forEach(i -> {
            Trade t = new Trade(i.getDate(), "", i.getClose(), shares, shares * i.getClose());
            trades.add(t);
        });
        return trades;
    }
}
