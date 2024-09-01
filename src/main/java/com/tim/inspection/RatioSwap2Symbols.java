package com.tim.inspection;

import com.tim.experiment.FullPairExperiment;
import com.tim.result.GroupTradeResult;
import com.tim.result.GroupTradeResultItem;
import com.tim.result.ReturnItemType;
import com.tim.trade.*;
import com.tim.utility.TradingHelper;

import java.util.ArrayList;
import java.util.List;

public class RatioSwap2Symbols {
    static String dir = "C:\\GitHubProjects\\data\\";
    static String resultDir = "C:\\GitHubProjects\\result\\";

    static String resultFile = "ration_swap_result_";
    static String date = "_2024_08_31";
    static String fileAppendix = TradingHelper.FILE_TYPE;

    public static void main(String[] args) {
        String f1 = "IVV.csv";
        String f2 = "XLK.csv";
        Float seedCost = 1000.0f;
        Trading t1 = new GapTrading(dir + f1, 0.0f);
        Trading t2 = new GapTrading(dir + f2, seedCost);
        GroupGapPairSwapTrading g = new GroupGapPairSwapTrading();
        Float gap = 0.05f;
        g.setLossMajor(false);
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
        Float controlReturn = FullPairExperiment.getThisControlReturn(g, 1);
        g.setControlReturn(controlReturn);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        g.report();
        List<GroupTradeResult> results = new ArrayList<>();
        results.add(g.collectResult());
        creatSymbolList(g, results);
        creatDailyResults(g, results);
        String saveFile = resultDir + resultFile + g.getSymbolList().trim() + date + fileAppendix;
        new GroupTradeResult().save(results, saveFile, false);
    }

    private static void creatSymbolList(GroupGapPairSwapTrading g, List<GroupTradeResult> results) {
        GroupTradeResult r = new GroupTradeResult();
        for (Trading t : g.getTradings()) {
            GroupTradeResultItem d = new GroupTradeResultItem("symbols", String.format("%12s", t.getSymbol()), ReturnItemType.StringType);
            r.getResults().add(d);
        }
        results.add(r);
    }

    private static void creatDailyResults(GroupGapRatioTrading g, List<GroupTradeResult> results) {
        int days = g.getTradings().get(0).getTrades().size();
        for (int i=0; i<days; i++) {
            Float sum = 0.0f;
            GroupTradeResult r = new GroupTradeResult();
            String date = g.getTradings().get(0).getTrades().get(i).getStringDay();
            GroupTradeResultItem d = new GroupTradeResultItem("date", String.format("%12s", date), ReturnItemType.StringType);
            r.getResults().add(d);
            for (Trading t : g.getTradings()) {
                Trade trade = t.getTrades().get(i);
                GroupTradeResultItem i1 = new GroupTradeResultItem("share_price", String.format("%7.2f", trade.getSharePrice()), ReturnItemType.FloatType);
                GroupTradeResultItem i2 = new GroupTradeResultItem("shares", String.format("%7.2f", trade.getShares()), ReturnItemType.FloatType);
                Float cost = trade.getSharePrice() * trade.getShares();
                GroupTradeResultItem i3 = new GroupTradeResultItem("cost", String.format("%9.2f", cost), ReturnItemType.FloatType);
                r.getResults().add(i1);
                r.getResults().add(i2);
                r.getResults().add(i3);
                sum = sum + cost;
            }
            GroupTradeResultItem i1 = new GroupTradeResultItem("cost", String.format("%9.2f", sum), ReturnItemType.FloatType);
            r.getResults().add(i1);
            results.add(r);
        }
    }
}
