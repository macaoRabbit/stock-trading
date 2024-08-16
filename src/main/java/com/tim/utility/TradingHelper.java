package com.tim.utility;

import com.tim.trade.GapTrading;
import com.tim.trade.Trading;

import java.util.ArrayList;
import java.util.List;

public class TradingHelper {
    static final String FILE_TYPE = ".csv";
    public static List<Trading> generate(String dir, String symbols, Float seedCost, int recordCount) {
        List<Trading> tradings = new ArrayList<>();
        String[] s = symbols.split(",");
        for (String symbol : s) {
            String fileName = dir + symbol + FILE_TYPE;
            Trading t = new GapTrading(fileName, seedCost);
            t.setRecordLimit(recordCount);
            tradings.add(t);
        }
        return tradings;
    }
}
