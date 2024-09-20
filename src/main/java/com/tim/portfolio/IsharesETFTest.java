package com.tim.portfolio;

import com.tim.experiment.PortfolioExperiment;
import com.tim.utility.FullExperiment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IsharesETFTest {
//    static String symbols = "VHT,QQQ,XLK,IVV,IJH,IJR,IVW,IJK,IJT,IVE,IJJ,IJS";
//    static String resultFile = "iSharesETF_2024_09_12";
    static String symbols = "IVV,IJH,IJR,IVW,IJK,IJT,IVE,IJJ,IJS";
    static FullExperiment experiment = FullExperiment.Pair;
    static String date = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    static String resultFile = "iSharesETF_" + experiment.toString() + "_" + date ;
    static Integer resultLimit = 10000;
    static String subDir = "iShares";

    public static void main(String[] args) {
        PortfolioExperiment.setSymbolsFileName(symbols, subDir, resultFile, resultLimit);
        PortfolioExperiment.setExperiment(experiment);
        PortfolioExperiment.run();
    }
}
