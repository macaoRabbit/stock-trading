package com.tim.trade;

import com.tim.parser.DailyQuote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupTrading extends Trading{

    public List<Trading> tradings = new ArrayList<>();
    public List<String> groupTradeDays = new ArrayList<>();

    public GroupTrading() {
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
            for (int j=0; j<quotes.size(); j++) {
                List<DailyQuote> q = matchedQuotes.get(j);
                q.add(quotes.get(j));
            }
        });
        for (int i = 0; i< tradings.size(); i++) {
            tradings.get(i).setQuotes(matchedQuotes.get(i));
        }
    }

    @Override
    public List<Trade> executeTrade() {
        Float seedCost = (float) tradings.stream().mapToDouble(i -> i.getSeedCost()).sum();
        Float endBalance = (float) tradings.stream().mapToDouble(i -> i.getEndBalance()).sum();
        int n = tradings.get(0).getQuotes().size();
        List<Trade> trades = this.getTrades();
        for (int i=0; i<n; i++) {
            Date date = tradings.get(0).getQuotes().get(i).getDate();
            int finalI = i;
            Float shares = (float) tradings.stream().mapToDouble(j -> j.getTrades().get(finalI).getShares()).sum();
            Float cost = (float) tradings.stream().mapToDouble(j -> j.getTrades().get(finalI).getCost()).sum();
            Trade t = new Trade(date, "", 0.0f, shares, cost);
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
}
