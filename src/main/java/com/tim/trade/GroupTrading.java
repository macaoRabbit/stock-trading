package com.tim.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupTrading extends Trading{

    private List<Trading> tradings = new ArrayList<>();

    public GroupTrading() {
    }

    public List<Trading> getTradings() {
        return tradings;
    }

    public void matchQuotes() {

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
        return trades;
    }

    @Override
    public void analyze() {
        tradings.forEach(i -> i.analyze());
        this.executeTrade();
        super.analyze();
    }
}
