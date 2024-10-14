package com.tim.inspection;

import com.tim.utility.TradingAlgorithm;

public class DetailRunOnce {
//    static String stockList = "CEG,LLY";
//    static String stockList = "IJS,VHT,IVW";
    static String stockList = "AMD,NOW,PAYC";
    static Float gap = 0.05f;
    static Float splitRatioPower = 6.0f;
    static TradingAlgorithm algorithm = TradingAlgorithm.RATIO_SPLIT;

    public static void main(String[] args) {
        DetailRunMultiSymbols.run(algorithm, stockList, gap, splitRatioPower);
    }
}
