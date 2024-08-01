package com.tim.trade;

public class ControlGroupTrading extends GroupTrading {
    @Override
    public void executeGroupTrade() {
        tradings.forEach(Trading::executeTrade);
    }
}
