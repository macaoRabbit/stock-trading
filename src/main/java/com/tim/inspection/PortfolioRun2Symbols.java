package com.tim.inspection;

import com.tim.experiment.PortfolioExperiment;

public class PortfolioRun2Symbols {
    static String symbols = "IVV,XLK";
    static String resultFile = "sp500_IVV_XLK";
    static Integer resultLimit = 10000;
    static boolean collect0TradeDayResults = true;

    public static void main(String[] args) {
        PortfolioExperiment.setSymbolsFileName(symbols, resultFile, resultLimit);
        PortfolioExperiment.setCollect0TradeDayResults(collect0TradeDayResults);
        PortfolioExperiment.run();
    }
}
