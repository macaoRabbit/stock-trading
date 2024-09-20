package com.tim.inspection;

import com.tim.experiment.PortfolioExperiment;
import com.tim.utility.FullExperiment;
import com.tim.utility.Symbols;

public class PortfolioRunMultiSymbols {
//    static String symbols = "IJS,VHT";
    static String symbols = "CEG,LLY";
    static FullExperiment experiment = FullExperiment.Pair;
    static String resultFile = "portfolio_" + Symbols.getSymbolList(symbols) + "_" + experiment.toString() + "_" + Symbols.getStringDate();
    static Integer resultLimit = 10000;
    static boolean collect0TradeDayResults = true;
    static String subDir = Symbols.getSymbolList(symbols);

    public static void main(String[] args) {
        PortfolioExperiment.setExperiment(experiment);
        PortfolioExperiment.setSymbolsFileName(symbols, subDir, resultFile, resultLimit);
        PortfolioExperiment.setCollect0TradeDayResults(collect0TradeDayResults);
        PortfolioExperiment.run();
    }
}
