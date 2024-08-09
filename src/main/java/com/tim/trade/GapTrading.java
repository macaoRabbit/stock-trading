package com.tim.trade;

import com.tim.parser.DailyQuote;

import java.util.List;

public class GapTrading extends Trading {

    public GapTrading(String dailyQuoteDataPath, Float seedCost) {
        super(dailyQuoteDataPath, seedCost);
    }

    public GapTrading(List<DailyQuote> quotes, Float seedCost) {
        super(quotes, seedCost);
    }

    @Override
    public List<Trade> executeTrade() {
        trades.clear();
        Float shares = seedCost/quotes.get(0).getClose();
        quotes.forEach(i -> {
            Trade t = new Trade(i.getDate(), "", i.getClose(), shares, shares * i.getClose(), i.getStringDate());
            trades.add(t);
        });
        return trades;
    }
}
