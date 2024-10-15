package com.tim.portfolio;

import com.tim.experiment.PortfolioExperiment;
import com.tim.utility.ExperimentType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IsharesETFTest {
    static String symbols = "VHT,QQQ,XLK,IVV,IJH,IJR,IVW,IJK,IJT,IVE,IJJ,IJS";
//    static String resultFile = "iSharesETF_2024_09_12";
//    static String symbols = "IVV,IJH,IJR,IVW,IJK,IJT,IVE,IJJ,IJS";
    static String date = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    static ExperimentType experiment = ExperimentType.Quad;
    static String resultFile = "iSharesETF_" + experiment.toString() + "_" + date ;
    static Integer resultLimit = 10000;
    static String subDir = "iShares";

    public static void main(String[] args) {
        run(ExperimentType.Pair);
        run(ExperimentType.Trio);
        run(ExperimentType.Quad);
    }

    private static void run(ExperimentType e) {
        experiment = e;
        resultFile = "iSharesETF_" + experiment.toString() + "_" + date ;
        PortfolioExperiment.setSymbolsFileName(symbols, subDir, resultFile, resultLimit);
        PortfolioExperiment.setExperiment(experiment);
        PortfolioExperiment.run();
    }
}
