package com.tim.trade;

import com.tim.parser.DailyQuote;

import java.util.ArrayList;
import java.util.List;

public class GroupQuote {
    List<DailyQuote> quotes;

    public List<DailyQuote> getQuotes() {
        return quotes;
    }

    public GroupQuote() {
        this.quotes = new ArrayList<>();
    }

    public GroupQuote(List<DailyQuote> quotes) {
        this.quotes = new ArrayList<>();
        this.quotes.addAll(quotes);
    }
}
