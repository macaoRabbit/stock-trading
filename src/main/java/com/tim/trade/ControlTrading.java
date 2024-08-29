package com.tim.trade;

import com.tim.parser.DailyQuote;
import com.tim.result.GroupTradeResult;
import com.tim.result.GroupTradeResultItem;
import com.tim.result.ReturnItemType;

import java.util.List;

public class ControlTrading extends Trading {

    public ControlTrading(String dailyQuoteDataPath, Float seedCost) {
        super(dailyQuoteDataPath, seedCost);
    }

    public ControlTrading(List<DailyQuote> quotes, String dailyQuoteDataPath, Float seedCost) {
        super(quotes, dailyQuoteDataPath, seedCost);
    }

    @Override
    public GroupTradeResult collectResult() {
        GroupTradeResult r = super.collectResult();
        GroupTradeResultItem i1 = new GroupTradeResultItem("symbol", String.format("%6s", getSymbol()), ReturnItemType.StringType);
        r.getResults().add(i1);
        return r;
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
