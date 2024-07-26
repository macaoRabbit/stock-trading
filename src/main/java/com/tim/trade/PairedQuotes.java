package com.tim.trade;

import com.tim.parser.DailyQuote;

import java.util.ArrayList;
import java.util.List;

public class PairedQuotes {
    List<DailyQuote> quotes;

    public List<DailyQuote> getQuotes() {
        return quotes;
    }

    public PairedQuotes() {
        this.quotes = new ArrayList<>();
    }

    public PairedQuotes(DailyQuote q1, DailyQuote q2) {
        this.quotes = new ArrayList<>();
        this.quotes.add(q1);
        this.quotes.add(q2);
    }
}
