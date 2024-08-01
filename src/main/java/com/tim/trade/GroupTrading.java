package com.tim.trade;

import com.tim.parser.DailyQuote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupTrading extends Trading{

    public List<Trading> tradings = new ArrayList<>();

    public GroupTrading() {
    }

    public List<Trading> getTradings() {
        return tradings;
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
        Date startDate = tradings.get(0).getQuotes().get(0).getDate();
        int n = tradings.get(0).getQuotes().size();
        Date endDate = tradings.get(0).getQuotes().get(n - 1).getDate();
        Trade firstTrade = new Trade(startDate, "", 0.0f, 0.0f, seedCost);
        Trade lastTrade = new Trade(endDate, "", 0.0f, 0.0f, endBalance);
        List<Trade> trades = this.getTrades();
        trades.add(firstTrade);
        trades.add(lastTrade);
        this.seedCost = seedCost;
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
