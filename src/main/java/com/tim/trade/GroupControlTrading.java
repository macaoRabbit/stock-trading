package com.tim.trade;

public class GroupControlTrading extends GroupTrading {
    @Override
    public void executeGroupTrade() {
        tradings.forEach(Trading::executeTrade);
    }
}
