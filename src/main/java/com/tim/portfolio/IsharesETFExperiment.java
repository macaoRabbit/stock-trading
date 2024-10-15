package com.tim.portfolio;

import com.tim.experiment.FullExperiment;

public class IsharesETFExperiment {
    static String symbols = "VHT,QQQ,XLK,IVV,IJH,IJR,IVW,IJK,IJT,IVE,IJJ,IJS";
    static String filePrefix = "iSharesETF_";
    static String subDir = "iShares";

    public static void main(String[] args) {
        FullExperiment e = new FullExperiment(symbols, subDir, filePrefix);
        e.run();
    }
}
