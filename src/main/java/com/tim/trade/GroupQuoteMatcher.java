package com.tim.trade;

import com.tim.parser.CsvParser;
import com.tim.parser.DailyQuote;

import java.util.ArrayList;
import java.util.List;

public class GroupQuoteMatcher {
    List<String> fileNames;
    List<List<DailyQuote>> allQuotes;

    public GroupQuoteMatcher() {
    }

    public GroupQuoteMatcher(List<List<DailyQuote>> allQuotes) {
        this.allQuotes = allQuotes;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public List<List<DailyQuote>> getAllQuotes() {
        return allQuotes;
    }

    public void setAllQuotes(List<List<DailyQuote>> allQuotes) {
        this.allQuotes = allQuotes;
    }

    List<GroupQuote> createMatchedQuotes() {
        List<GroupQuote> groupQuotes = new ArrayList<>();
        List<Integer> ptrs = new ArrayList<>();
        initailziePtrs(ptrs, allQuotes);
        while(hasValidePtrForAllQuoteSeries(allQuotes, ptrs)) {
            String minMaxDate = findMaxDate(allQuotes, ptrs);
            advanceAllPtrsToMaxDateOrLarger(allQuotes, ptrs, minMaxDate);
            if (hasValideAndEqualDatePtrForAllQuoteSeries(allQuotes, ptrs)) {
                GroupQuote g = new GroupQuote();
                for (int i = 0; i< allQuotes.size(); i++) {
                    g.getQuotes().add(allQuotes.get(i).get(ptrs.get(i)));
                    Integer p = ptrs.get(i);
                    p = p + 1;
                    ptrs.set(i, p);
                }
                groupQuotes.add(g);
            }
        }
        return groupQuotes;
    }

    private boolean hasValideAndEqualDatePtrForAllQuoteSeries(List<List<DailyQuote>> allQuotes, List<Integer> ptrs) {
        boolean r = true;
        if (allQuotes.get(0).size() <= ptrs.get(0)) {
            return false;
        }
        String m = allQuotes.get(0).get(ptrs.get(0)).getStringDate();
        for (int i = 0; i < allQuotes.size(); i++) {
            if (ptrs.get(i) >= allQuotes.get(i).size()) {
                return false;
            }
            if (!m.equalsIgnoreCase(allQuotes.get(i).get(ptrs.get(i)).getStringDate())) {
                return false;
            }
        }
        return r;
    }

    private void advanceAllPtrsToMaxDateOrLarger(List<List<DailyQuote>> allQuotes, List<Integer> ptrs, String maxDate) {
        for (int i = 0; i < allQuotes.size(); i++) {
           List<DailyQuote> quotes = allQuotes.get(i);
           Integer p = ptrs.get(i);
           while (p < quotes.size() && maxDate.compareTo(quotes.get(p).getStringDate()) > 0) {
                p = p + 1;
                ptrs.set(i, p);
            }
        }
    }

    private String findMaxDate(List<List<DailyQuote>> allQuotes, List<Integer> ptrs) {
        String m = allQuotes.get(0).get(ptrs.get(0)).getStringDate();
        for (int i = 0; i<allQuotes.size(); i++) {
            String xDate = allQuotes.get(i).get(ptrs.get(i)).getStringDate();
            int r = m.compareTo(xDate);
            if (r < 0) {
                m = xDate;
            }
        }
        return m;
    }

    private boolean hasValidePtrForAllQuoteSeries(List<List<DailyQuote>> allQuotes, List<Integer> ptrs) {
        boolean r = true;
        for (int i = 0; i < allQuotes.size(); i++) {
            if (allQuotes.get(i).size() <= ptrs.get(i)) {
                return false;
            }
        }
        return r;
    }

    private void initailziePtrs(List<Integer> ptrs, List<List<DailyQuote>> allQuotes) {
        allQuotes.forEach(i -> {
            ptrs.add(0);
        });
    }

    public void initAllDailyQuotesFromFiles() {
        allQuotes = new ArrayList<>();
        this.fileNames.forEach(i -> {
            List<DailyQuote> q = new CsvParser(i).getLatestStockData();
            allQuotes.add(q);
        });
    }
}
