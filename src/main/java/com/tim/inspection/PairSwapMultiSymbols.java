package com.tim.inspection;

import com.tim.experiment.FullPairExperiment;
import com.tim.result.GroupTradeResult;
import com.tim.result.GroupTradeResultItem;
import com.tim.result.ReturnItemType;
import com.tim.trade.*;
import com.tim.utility.GapDetails;
import com.tim.utility.TradingHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PairSwapMultiSymbols {
    static String dir = "C:\\GitHubProjects\\data\\";
    static String resultDir = "C:\\GitHubProjects\\result\\";
    //    static String stockList = "CEG,LLY";
    static String stockList = "IJS,VHT";
    static String resultFile = "pair_swap_";
    static String date = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    static String fileAppendix = TradingHelper.FILE_TYPE;
    static boolean includeSummary = true;
    static boolean anyDayGap = true;
    static Float gap = 0.05f;
    static Integer tradeGroupSize = 2;

    public static void main(String[] args) {
        Float seedCost = 1000.0f;
        String stocks[] = stockList.split(",");
        tradeGroupSize = stocks.length;
        boolean lossMajor = true;

        for (int index = 0; index < tradeGroupSize; index++) {
            List<Trading> tradings = new ArrayList<>();
            for (int i = 0; i < getTradeGroupSize(); i++) {
                String filiName = stocks[i].trim() + fileAppendix;
                Trading t = new GapTrading(dir + filiName, 0.0f);
                if (i == index) {
                    t = new GapTrading(dir + filiName, seedCost);
                }
                tradings.add(t);
            }
            run(tradings, index, gap, lossMajor, includeSummary, anyDayGap);

        }
    }

    private static void run(List<Trading> tradings, int index, Float gap, boolean lossMajor, boolean includeSummary, boolean anyDayGap) {
        String num = "_" + String.format("%1d", index) + "_";
        GroupTradeDayGapPairSwapTrading g = new GroupTradeDayGapPairSwapTrading();
        g.setLossMajor(lossMajor);
        g.setGapSize(gap);
        g.setAnyDayGap(anyDayGap);
        g.getTradings().addAll(tradings);
        Float controlReturn = FullPairExperiment.getThisControlReturn(g, index);
        g.setControlReturn(controlReturn);
        g.initQuotesWithCsvFileForAllTradings();
        g.matchQuotesForAllTradings();
        g.analyze();
        g.report();
        List<GroupTradeResult> results = new ArrayList<>();
        if (includeSummary) {
            results.add(g.collectResult());
            creatSymbolList(g, results);
        }
        creatDailyResults(g, results);
        String summary = "";
        if (includeSummary) {
            summary = "_sum";
        }
        String anyDay = "_tradeD";
        if (anyDayGap) {
            anyDay = "_anyD";

        }
        String pGap = String.format("_G%03.0f", gap * 1000);
        String subDir = resultDir + g.getSymbolList().trim();
        String lossOrNot = "_" + String.format("%b", lossMajor);
        String saveFile = subDir + "\\" + resultFile + g.getSymbolList().trim() + pGap + anyDay + lossOrNot + summary + num + date + fileAppendix;
        new GroupTradeResult().save(results, subDir, saveFile, false);
    }

    private static void creatSymbolList(GroupTradeDayGapPairSwapTrading g, List<GroupTradeResult> results) {
        GroupTradeResult r = new GroupTradeResult();
        for (Trading t : g.getTradings()) {
            GroupTradeResultItem d = new GroupTradeResultItem("symbols", String.format("%12s", t.getSymbol()), ReturnItemType.StringType);
            r.getResults().add(d);
        }
        results.add(r);
    }

    private static void creatDailyResults(GroupTradeDayGapRatioTrading g, List<GroupTradeResult> results) {
        int days = g.getTradings().get(0).getTrades().size();
        for (int i = 0; i < days; i++) {
            GroupTradeResult r = new GroupTradeResult();
            addDate(g, i, r);
            addSharesAndEquity(g, i, r);
            addGapDetails(g, i, r);
            results.add(r);
        }
    }

    private static void addSharesAndEquity(GroupTradeDayGapRatioTrading g, int i, GroupTradeResult r) {
        Float cEquity = 0.0f;
        Float gEquity = 0.0f;
        for (Trading t : g.getTradings()) {
            Trade trade = t.getTrades().get(i);
            Float controlShares = t.getTrades().get(0).getShares();
            GroupTradeResultItem i1 = new GroupTradeResultItem(t.getSymbol() + "_price", String.format("%7.2f", trade.getSharePrice()), ReturnItemType.FloatType);
            GroupTradeResultItem i2 = new GroupTradeResultItem(t.getSymbol() + "_shares", String.format("%7.2f", trade.getShares()), ReturnItemType.FloatType);
            Float equity = trade.getSharePrice() * trade.getShares();
            GroupTradeResultItem i3 = new GroupTradeResultItem(t.getSymbol() + "_equity", String.format("%9.2f", equity), ReturnItemType.FloatType);
            r.getResults().add(i1);
            r.getResults().add(i2);
            r.getResults().add(i3);
            gEquity = gEquity + equity;
            cEquity = cEquity + trade.getSharePrice() * controlShares;
        }
        GroupTradeResultItem i1 = new GroupTradeResultItem("cEquity", String.format("%9.2f", cEquity), ReturnItemType.FloatType);
        GroupTradeResultItem i2 = new GroupTradeResultItem("gEquity", String.format("%9.2f", gEquity), ReturnItemType.FloatType);
        r.getResults().add(i1);
        r.getResults().add(i2);
    }

    private static void addDate(GroupTradeDayGapRatioTrading g, int i, GroupTradeResult r) {
        String date = g.getTradings().get(0).getTrades().get(i).getStringDay();
        GroupTradeResultItem d = new GroupTradeResultItem("date", String.format("%12s", date), ReturnItemType.StringType);
        r.getResults().add(d);
    }

    private static void addGapDetails(GroupTradeDayGapRatioTrading g, int i, GroupTradeResult r) {
        if (i >= g.getDailyGaps().size()) return;
        GapDetails d = g.getDailyGaps().get(i);
        Double min = d.getMins().stream().mapToDouble(x -> x.getRatio()).min().orElse(-1.0f);
        Double max = d.getMaxs().stream().mapToDouble(x -> x.getRatio()).max().orElse(-1.0f);
        GroupTradeResultItem i1 = new GroupTradeResultItem("gap", String.format("%9.3f", d.getGap()), ReturnItemType.FloatType);
        GroupTradeResultItem i2 = new GroupTradeResultItem("min", String.format("%9.3f", min), ReturnItemType.FloatType);
        GroupTradeResultItem i3 = new GroupTradeResultItem("max", String.format("%9.3f", max), ReturnItemType.FloatType);
        GroupTradeResultItem i4 = new GroupTradeResultItem("date", String.format("%12s", d.getGapDay()), ReturnItemType.StringType);
        GroupTradeResultItem i5 = new GroupTradeResultItem("duration", String.format("%6d", d.getDuration()), ReturnItemType.IntegerType);
        r.getResults().add(i1);
        r.getResults().add(i2);
        r.getResults().add(i3);
        r.getResults().add(i4);
        r.getResults().add(i5);
    }

    public static Integer getTradeGroupSize() {
        return tradeGroupSize;
    }

    public static void setTradeGroupSize(Integer tradeGroupSize) {
        PairSwapMultiSymbols.tradeGroupSize = tradeGroupSize;
    }
}
