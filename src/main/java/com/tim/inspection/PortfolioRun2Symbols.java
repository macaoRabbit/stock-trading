package com.tim.inspection;

import com.tim.experiment.PortfolioExperiment;
import com.tim.utility.Symbols;

public class PortfolioRun2Symbols {
    static String symbols = "IJS,VHT";
    static String resultFile = "portfolio_" + Symbols.getSymbolList(symbols) + Symbols.getStringDate();
    static Integer resultLimit = 10000;
    static boolean collect0TradeDayResults = true;

    public static void main(String[] args) {
        PortfolioExperiment.setSymbolsFileName(symbols, resultFile, resultLimit);
        PortfolioExperiment.setCollect0TradeDayResults(collect0TradeDayResults);
        PortfolioExperiment.run();
    }
}
