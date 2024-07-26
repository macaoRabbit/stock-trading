package com.tim.trade;

import java.util.Date;

public class Trade {
    Date date;
    String symbol;
    Float sharePrice;
    Float shares;
    Float cost;

    public Trade(Date date, String symbol, Float sharePrice, Float shares, Float cost) {
        this.date = date;
        this.symbol = symbol;
        this.sharePrice = sharePrice;
        this.shares = shares;
        this.cost = cost;
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
        this.sharePrice = sharePrice;
    }

    public Float getShares() {
        return shares;
    }

    public void setShares(Float shares) {
        this.shares = shares;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }
}
