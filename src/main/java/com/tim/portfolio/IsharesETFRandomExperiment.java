package com.tim.portfolio;

import com.tim.experiment.FullExperiment;
import com.tim.experiment.RandomExpreiment;

public class IsharesETFRandomExperiment {
    static String symbols = "VHT,QQQ,XLK,IVV,IJH,IJR,IVW,IJK,IJT,IVE,IJJ,IJS";
    static String filePrefix = "iSharesETF_";
    static String subDir = "iShares";

    public static void main(String[] args) {
        FullExperiment e = new RandomExpreiment(symbols, subDir, filePrefix);
        e.run();
    }
}
