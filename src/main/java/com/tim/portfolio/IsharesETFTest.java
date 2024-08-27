package com.tim.portfolio;

import com.tim.experiment.PortfolioExperiment;

public class IsharesETFTest {
    static String symbols = "VHT,QQQ,XLK,IVV,IJH,IJR,IVW,IJK,IJT,IVE,IJJ,IJS";
    static String resultFile = "iSharesETF_2024_08_27";
    static Integer resultLimit = 10000;

    public static void main(String[] args) {
        PortfolioExperiment.setSymbolsFileName(symbols, resultFile, resultLimit);
        PortfolioExperiment.run();
    }
}
