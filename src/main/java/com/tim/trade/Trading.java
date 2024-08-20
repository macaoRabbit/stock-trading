package com.tim.trade;

import com.tim.parser.CsvParser;
import com.tim.parser.DailyQuote;
import com.tim.result.GroupTradeResult;
import com.tim.result.GroupTradeResultItem;
import com.tim.result.ReturnItemType;

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
    Float controlReturn = 0.0f;
    Integer recordLimit = 0;

    public Trading(String dailyQuoteDataPath, Float seedCost) {
        this.seedCost = seedCost;
        this.dailyQuoteDataPath = dailyQuoteDataPath;
        initQuotesWithCsvFile();
    }

    public Trading(List<DailyQuote> quotes, Float seedCost) {
        this.seedCost = seedCost;
        this.quotes = quotes;
    }

    public Trading() {
    }

    public void initQuotesWithCsvFile() {
        CsvParser parser = new CsvParser(dailyQuoteDataPath);
        if (recordLimit > 0) {
            parser.setRecordLimit(recordLimit);
        }
        quotes = parser.getLatestStockData();
    }

    List<Trade> executeTrade() {
        return trades;
    }

    public void analyze() {
        Trade firstTrade = trades.get(0);
        Trade lastTrade = trades.get(trades.size() - 1);
        setEndBalance(lastTrade.getCost());
        setProfit(lastTrade.getCost() - seedCost);
        long days = TimeUnit.DAYS.convert(lastTrade.getDate().getTime() - firstTrade.getDate().getTime(), TimeUnit.MILLISECONDS);
        float years = (float) (1.0 * days / 365);
        double r = Math.pow(endBalance / seedCost, 1.0 / years) - 1;
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

    public void setQuotes(List<DailyQuote> quotes) {
        this.quotes = quotes;
    }

    public List<DailyQuote> getQuotes() {
        return quotes;
    }


    public Float getControlReturn() {
        return controlReturn;
    }

    public void setControlReturn(Float controlReturn) {
        this.controlReturn = controlReturn;
    }

    public Integer getRecordLimit() {
        return recordLimit;
    }

    public void setRecordLimit(Integer recordLimit) {
        this.recordLimit = recordLimit;
    }

    public void reportSummary() {
        System.out.printf("annualizedReturn= %7.3f ", annualizedReturn);
        System.out.printf("comparedWithControl= %7.3f ", (annualizedReturn - controlReturn));
        System.out.printf("seedCost= %7.2f ", seedCost);
        System.out.printf("endBalance= %7.2f ", endBalance);
        System.out.printf("profit= %7.2f ", profit);
        System.out.println();
    }

    public GroupTradeResult collectResult() {
        GroupTradeResult r =  new GroupTradeResult();
        GroupTradeResultItem i1 = new GroupTradeResultItem("annualizedReturn", String.format("%7.3f", annualizedReturn), ReturnItemType.FloatType);
        GroupTradeResultItem i2 = new GroupTradeResultItem("excessReturn", String.format("%7.3f", (annualizedReturn - controlReturn)), ReturnItemType.FloatType);
        GroupTradeResultItem i3 = new GroupTradeResultItem("seedCost", String.format("%9.2f", seedCost), ReturnItemType.FloatType);
        GroupTradeResultItem i4 = new GroupTradeResultItem("endBalance", String.format("%9.2f", endBalance), ReturnItemType.FloatType);
        GroupTradeResultItem i5 = new GroupTradeResultItem("profit", String.format("%9.2f", profit), ReturnItemType.FloatType);

        r.getResults().add(i1);
        r.getResults().add(i2);
        r.getResults().add(i3);
        r.getResults().add(i4);
        r.getResults().add(i5);
        return r;
    }
}
