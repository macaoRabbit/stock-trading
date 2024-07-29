package com.tim.trade;

import com.tim.parser.DailyQuote;

import java.util.ArrayList;
import java.util.List;

public class GroupQuotes {
    List<DailyQuote> quotes;

    public List<DailyQuote> getQuotes() {
        return quotes;
    }

    public GroupQuotes() {
        this.quotes = new ArrayList<>();
    }

    public GroupQuotes(List<DailyQuote> quotes) {
        this.quotes = new ArrayList<>();
        this.quotes.addAll(quotes);
    }
}
