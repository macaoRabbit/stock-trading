package com.tim.trade;

import java.util.Date;

public class Trade {
    Date date;
    String symbol;
    Float sharePrice;
    Float shares;
    Float cost;
    String stringDay;

    public Trade(Date date, String symbol, Float sharePrice, Float shares, Float cost, String stringDay) {
        this.date = date;
        this.symbol = symbol;
        this.sharePrice = myRound(sharePrice);
        this.shares = myRound(shares);
        this.cost = myRound(cost);
        this.stringDay = stringDay;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Float getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(Float sharePrice) {
        this.sharePrice = myRound(sharePrice);
    }

    public Float getShares() {
        return shares;
    }

    public void setShares(Float shares) {
        this.shares = myRound(shares);
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = myRound(cost);
    }

    private Float myRound(Float cost) {
        return cost;
    }

    public String getStringDay() {
        return stringDay;
    }

    public void setStringDay(String stringDay) {
        this.stringDay = stringDay;
    }

    public void report() {
        System.out.print(stringDay);
        System.out.print(" " + symbol);
        System.out.printf(" %7.2f", sharePrice);
        System.out.printf(" %7.2f", shares);
        System.out.printf(" %7.2f", cost);
        System.out.println();
    }

    public void simpleReport() {
        System.out.printf(" %7.2f", sharePrice);
        System.out.printf(" %7.2f", shares);
        System.out.printf(" %7.2f", cost);
    }
}
