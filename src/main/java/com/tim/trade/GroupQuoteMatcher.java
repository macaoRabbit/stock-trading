package com.tim.trade;

import com.tim.parser.CsvParser;
import com.tim.parser.DailyQuote;

import java.util.ArrayList;
import java.util.List;

public class GroupQuoteMatcher {
    List<String> fileNames;

    public GroupQuoteMatcher(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    List<GroupQuotes> getMatchedQuotes() {
        List<List<DailyQuote>> allQuotes = getAllDailyQuotes(fileNames);
        List<GroupQuotes> groupQuotes = new ArrayList<>();
        List<Integer> ptrs = new ArrayList<>();
        initailziePtrs(ptrs, allQuotes);
        while(hasValidePtrForAllQuoteSeries(allQuotes, ptrs)) {
            String maxDate = findMaxDate(allQuotes, ptrs);
            advanceAllPtrsToMaxDateOrLarger(allQuotes, ptrs, maxDate);
            if (hasValideAndEqualDatePtrForAllQuoteSeries(allQuotes, ptrs)) {
                GroupQuotes g = new GroupQuotes();
                for (int i = 0; i< allQuotes.size(); i++) {
                    g.getQuotes().add(allQuotes.get(i).get(ptrs.get(i)));
                    Integer p = ptrs.get(i);
                    p = p + 1;
                }
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
           while (maxDate.compareTo(quotes.get(p).getStringDate()) < 0) {
                p = p + 1;
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

    private List<List<DailyQuote>> getAllDailyQuotes(List<String> fileNames) {
        List<List<DailyQuote>> allQuotes = new ArrayList<>();
        fileNames.forEach(i -> {
            List<DailyQuote> q = new CsvParser(i).getLatestStockData();
            allQuotes.add(q);
        });
        return allQuotes;
    }
}
