package com.tim.yahoo;

import org.junit.Ignore;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@Ignore
public class StockQuoteTest {
    public void getYahooStockQuoteTest() {
        try {
            Stock stock = YahooFinance.get("INTC");
            List<HistoricalQuote> quotes = stock.getHistory();
            assertTrue(quotes.size() > 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
