package com.tim.trade;

import java.util.TreeMap;

public class GroupGapPairSwapTrading extends GroupGapRatioTrading {

    @Override
    public Float findEquityGap(int equities, int day, TreeMap<Float, Trading> tradingMap) {
        return 0.0f;
    }
}
