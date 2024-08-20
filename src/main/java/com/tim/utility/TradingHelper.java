package com.tim.utility;

import com.tim.trade.GapTrading;
import com.tim.trade.Trading;

import java.util.ArrayList;
import java.util.List;

public class TradingHelper {
    static final String FILE_TYPE = ".csv";
    public static List<Trading> generate(String dir, String symbols, Float seedCost, int recordCount, int minRecordCount) {
        List<Trading> tradings = new ArrayList<>();
        String[] s = symbols.split(",");
        for (String symbol : s) {
            String fileName = dir + symbol.trim() + FILE_TYPE;
            try {
                Trading t = new GapTrading(fileName, seedCost);
                t.setRecordLimit(recordCount);
                if (t.getQuotes().size() >= minRecordCount) {
                    tradings.add(t);
                }
            } catch (Exception e) {
                System.out.println("Error in processing file: " + fileName);
            }
        }
        return tradings;
    }
}
