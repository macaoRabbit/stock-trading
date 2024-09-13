package com.tim.portfolio;

import com.tim.experiment.PortfolioExperiment;

public class IsharesETFTest {
//    static String symbols = "VHT,QQQ,XLK,IVV,IJH,IJR,IVW,IJK,IJT,IVE,IJJ,IJS";
//    static String resultFile = "iSharesETF_2024_09_12";
    static String symbols = "IVV,IJH,IJR,IVW,IJK,IJT,IVE,IJJ,IJS";
    static String resultFile = "iSharesETF_ONLY_2024_09_12";
    static Integer resultLimit = 10000;
    static String subDir = "iShares";

    public static void main(String[] args) {
        PortfolioExperiment.setSymbolsFileName(symbols, subDir, resultFile, resultLimit);
        PortfolioExperiment.run();
    }
}
