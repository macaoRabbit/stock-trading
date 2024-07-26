package com.tim.trade;

import com.tim.parser.CsvParser;
import com.tim.parser.DailyQuote;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class Trading {
    String dailyQuoteDataPath;
    Float seedCost;
    Float endBalance;
    Float profit;
    Float annualizedReturn;
    List<DailyQuote> quotes;
    List<Trade> trades = new ArrayList<>();

    public Trading(String dailyQuoteDataPath, Float seedCost) {
        this.seedCost = seedCost;
        this.dailyQuoteDataPath = dailyQuoteDataPath;
        quotes = new CsvParser(dailyQuoteDataPath).getLatestStockData();
    }

    List<Trade> execute() {
        return trades;
    }

    public void analyze() {
        execute();
        Trade firstTrade = trades.get(0);
        Trade lastTrade = trades.get(trades.size() - 1);
        setEndBalance(lastTrade.getCost());
        setProfit(lastTrade.getCost() - seedCost);
        long days = TimeUnit.DAYS.convert(lastTrade.getDate().getTime() - firstTrade.getDate().getTime(), TimeUnit.MILLISECONDS);
        float years = (float) (1.0 * days/365);
        double r = Math.pow(endBalance/seedCost, 1.0/years) - 1;
        setAnnualizedReturn((float) r);
    }

    public String getDailyQuoteDataPath() {
        return dailyQuoteDataPath;
    }

    public Float getSeedCost() {
        return seedCost;
    }

    public void setSeedCost(Float seedCost) {
        this.seedCost = seedCost;
    }

    public Float getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(Float endBalance) {
        this.endBalance = endBalance;
    }

    public Float getProfit() {
        return profit;
    }

    public void setProfit(Float profit) {
        this.profit = profit;
    }

    public Float getAnnualizedReturn() {
        return annualizedReturn;
    }

    public void setAnnualizedReturn(Float annualizedReturn) {
        this.annualizedReturn = annualizedReturn;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }

    public List<DailyQuote> getQuotes() {
        return quotes;
    }
}
