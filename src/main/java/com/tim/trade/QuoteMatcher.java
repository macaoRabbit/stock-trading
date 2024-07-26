package com.tim.trade;

import com.tim.parser.CsvParser;
import com.tim.parser.DailyQuote;

import java.util.ArrayList;
import java.util.List;

public class QuoteMatcher {
    String fileName1;
    String fileName2;

    public QuoteMatcher(String fileName1, String fileName2) {
        this.fileName1 = fileName1;
        this.fileName2 = fileName2;
    }

    List<PairedQuotes> generateMatchedQuotes() {
        List<PairedQuotes> quotes = new ArrayList<>();
        List<DailyQuote> qList1 = new CsvParser(fileName1).getLatestStockData();
        List<DailyQuote> qList2 = new CsvParser(fileName2).getLatestStockData();
        int ptr1 = 0;
        int ptr2 = 0;
        while (ptr1 < qList1.size() && ptr2 < qList2.size()) {
            DailyQuote q1 = qList1.get(ptr1);
            DailyQuote q2 = qList2.get(ptr2);
            int r = q1.getStringDate().compareTo(q2.getStringDate());
            if (r > 0) {
                r = 1;
            }
            if (r < 0) {
                r = -1;
            }
            switch (r) {
                case 0:
                    quotes.add(new PairedQuotes(q1, q2));
                    ptr1++;
                    ptr2++;
                    break;
                case 1:
                    ptr2++;
                    break;
                case -1:
                    ptr1++;
                    break;
            }
        }
        return quotes;
    }
}
