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
import java.util.OptionalDouble;

public class PairSwap2Symbols {
    static String dir = "C:\\GitHubProjects\\data\\";
    static String resultDir = "C:\\GitHubProjects\\result\\";
    static String stockList = "CEG,LLY";
//    static String stockList = "IJS,VHT";
    static String resultFile = "pair_swap_";
    static String date = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    static String fileAppendix = TradingHelper.FILE_TYPE;
    static boolean includeSummary = true;

    public static void main(String[] args) {
        Float seedCost = 1000.0f;
        String stocks[] = stockList.split(",");

        String f1 = stocks[0].trim() + fileAppendix;
        String f2 = stocks[1].trim() + fileAppendix;

        Trading t1 = new GapTrading(dir + f1, seedCost);
        Trading t2 = new GapTrading(dir + f2, 0.0f);
        String num = "_1_";
        int index = 0;
        Float gap = 0.05f;
        boolean lossMajor = true;
        includeSummary = false;
        run(t1, t2, num, index, gap, lossMajor, includeSummary);

        t1 = new GapTrading(dir + f1, 0.0f);
        t2 = new GapTrading(dir + f2, seedCost);
        num = "_2_";
        index = 1;
        lossMajor = true;
        gap = 0.075f;
        includeSummary = false;
        run(t1, t2, num, index, gap, lossMajor, includeSummary);
    }

    private static void run(Trading t1, Trading t2, String num, int index, Float gap, boolean lossMajor, boolean includeSummary) {
        GroupTradeDayGapPairSwapTrading g = new GroupTradeDayGapPairSwapTrading();
        g.setLossMajor(lossMajor);
        g.setGapSize(gap);
        g.getTradings().add(t1);
        g.getTradings().add(t2);
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
        String saveFile = resultDir + resultFile + g.getSymbolList().trim() + num +  date + fileAppendix;
        new GroupTradeResult().save(results, saveFile, false);
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
        for (int i=0; i<days; i++) {
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
        GapDetails d = g.getDailyGaps().get(i);
        OptionalDouble min = d.getMins().stream().mapToDouble(x -> x.getRatio()).min();
        OptionalDouble max = d.getMaxs().stream().mapToDouble(x -> x.getRatio()).max();
        GroupTradeResultItem i1 = new GroupTradeResultItem("gap", String.format("%9.3f", d.getGap()), ReturnItemType.FloatType);
        GroupTradeResultItem i2 = new GroupTradeResultItem("min", String.format("%9.3f", min.getAsDouble()), ReturnItemType.FloatType);
        GroupTradeResultItem i3 = new GroupTradeResultItem("max", String.format("%9.3f", max.getAsDouble()), ReturnItemType.FloatType);
        r.getResults().add(i1);
        r.getResults().add(i2);
        r.getResults().add(i3);
    }
}
