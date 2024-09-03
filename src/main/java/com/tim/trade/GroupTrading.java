package com.tim.trade;

import com.tim.parser.DailyQuote;
import com.tim.result.GroupTradeResult;
import com.tim.result.GroupTradeResultItem;
import com.tim.result.ReturnItemType;

import java.util.*;

public abstract class GroupTrading extends Trading {

    public List<Trading> tradings = new ArrayList<>();
    public List<String> groupTradeDays = new ArrayList<>();
    public List<Integer> groupTradeDayIndex = new ArrayList<>();
    final static Float VERY_SMALL_FLOAT = 0.00001f;

    public GroupTrading() {
    }

    public List<Integer> getGroupTradeDayIndex() {
        return groupTradeDayIndex;
    }

    public List<Trading> getTradings() {
        return tradings;
    }

    public List<String> getGroupTradeDays() {
        return groupTradeDays;
    }

    public void initQuotesWithCsvFileForAllTradings() {
        tradings.forEach(Trading::initQuotesWithCsvFile);
    }

    public Float addSmallAmount(Float f) {
        return (float) (f + VERY_SMALL_FLOAT * ((new Random()).nextFloat() - 0.5));
    }

    public void executeGroupTrade() {
        tradings.forEach(Trading::executeTrade);
    }

    public void matchQuotesForAllTradings() {
        List<List<DailyQuote>> allQuotes = new ArrayList<>();
        tradings.forEach(i -> {
            allQuotes.add(i.getQuotes());
        });
        GroupQuoteMatcher gMatcher = new GroupQuoteMatcher(allQuotes);
        List<GroupQuote> groupQuotes = gMatcher.createMatchedQuotes();
        List<List<DailyQuote>> matchedQuotes = new ArrayList<>();
        tradings.forEach(i -> {
            List<DailyQuote> dailyQuotes = new ArrayList<>();
            matchedQuotes.add(dailyQuotes);
        });
        groupQuotes.forEach(i -> {
            List<DailyQuote> quotes = i.getQuotes();
            for (int j = 0; j < quotes.size(); j++) {
                List<DailyQuote> q = matchedQuotes.get(j);
                q.add(quotes.get(j));
            }
        });
        for (int i = 0; i < tradings.size(); i++) {
            tradings.get(i).setQuotes(matchedQuotes.get(i));
        }
    }

    @Override
    public List<Trade> executeTrade() {
        Float seedCost = (float) tradings.stream().mapToDouble(i -> i.getSeedCost()).sum();
        Float endBalance = (float) tradings.stream().mapToDouble(i -> i.getEndBalance()).sum();
        int n = tradings.get(0).getQuotes().size();
        List<Trade> trades = this.getTrades();
        for (int i = 0; i < n; i++) {
            Date date = tradings.get(0).getQuotes().get(i).getDate();
            String stringDay = tradings.get(0).getQuotes().get(i).getStringDate();
            int finalI = i;
            Float shares = (float) tradings.stream().mapToDouble(j -> j.getTrades().get(finalI).getShares()).sum();
            Float cost = (float) tradings.stream().mapToDouble(j -> j.getTrades().get(finalI).getShares() * j.getTrades().get(finalI).getSharePrice()).sum();
            Float sharePrice = shares > 0.0f && cost > 0.0f? cost/shares : 0.0f;
            Trade t = new Trade(date, this.getSymbolList(), sharePrice, shares, cost, stringDay);
            trades.add(t);
        }
        this.seedCost = seedCost;
        this.endBalance = endBalance;
        return trades;
    }

    @Override
    public void analyze() {
        tradings.forEach(i -> {
            i.executeTrade();
            i.analyze();
        });
        this.executeGroupTrade();
        this.executeTrade();
        super.analyze();
    }

    @Override
    public void reportSummary() {
        reportTradingsSummary();
        super.reportSummary();
        reportTradDaysSummary();
    }

    private void reportTradingsSummary() {
        System.out.print(" ----> ");
        for (Trading t : tradings) {
            String s = t.getDailyQuoteDataPath();
            int i = s.lastIndexOf('\\');
            System.out.print(s.substring(i + 1) + " ");
        }
        System.out.println(" ----> ");
    }

    public void report() {
        reportSummary();
        reportTradDays();
        reportTradings();
        reportTradeDetails();
    }

    private void reportTradings() {
        for (int i = 0; i < tradings.get(0).getTrades().size(); i++) {
            Trade t = tradings.get(0).getTrades().get(i);
            System.out.print(t.stringDay + " ");
            for (Trading trading : tradings) {
                Trade u = trading.getTrades().get(i);
                u.simpleReport();
            }
            System.out.println();
        }
    }

    private void reportTradeDetails() {
        for (Trade t : trades) {
            t.report();
        }
    }

    private void reportTradDaysSummary() {
        String startDay = trades.get(0).getStringDay();
        String endDay = trades.get(trades.size() - 1).getStringDay();
        System.out.println("startDay: " + startDay + " endDay: " + endDay + " TradeDays: " + groupTradeDays.size());
    }

    private void reportTradDays() {
        for (String s : groupTradeDays) {
            System.out.println(s);
        }
    }

    protected void clear() {
        groupTradeDays.clear();
        groupTradeDayIndex.clear();
    }

    @Override
    public GroupTradeResult collectResult() {
        GroupTradeResult r = super.collectResult();
        String symbols = getSymbolList();
        String startDay = trades.get(0).getStringDay();
        String endDay = trades.get(trades.size() - 1).getStringDay();
        GroupTradeResultItem i1 = new GroupTradeResultItem("symbols", symbols, ReturnItemType.StringType);
        GroupTradeResultItem i2 = new GroupTradeResultItem("startDay", " " + startDay, ReturnItemType.StringType);
        GroupTradeResultItem i3 = new GroupTradeResultItem("endDay", " " + endDay, ReturnItemType.StringType);
        GroupTradeResultItem i4 = new GroupTradeResultItem("tradeDays", String.format("%5d", groupTradeDays.size()), ReturnItemType.IntegerType);
        r.getResults().add(i1);
        r.getResults().add(i2);
        r.getResults().add(i3);
        r.getResults().add(i4);
        return r;
    }

    public String getSymbolList() {
        List<String> symbols = new ArrayList<>();
        for (int j = 0; j < tradings.size(); j++) {
            Trading t = tradings.get(j);
            String s = t.getDailyQuoteDataPath();
            int i = s.lastIndexOf('\\');
            symbols.add(s.substring(i + 1));
        }
        Collections.sort(symbols);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < symbols.size(); i++) {
            String symbol = symbols.get(i);
            int index = symbol.indexOf("csv");
            s.append(symbol.substring(0, index - 1));
            if (i < symbols.size() - 1) {
                s.append("--");
            }
        }
        return String.format("%12s", s.toString());
    }
}
