package com.tim.experiment;

import com.tim.utility.ExperimentType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FullExperiment {
    Integer resultLimit = 10000;
    String symbols = "";
    String subDir = "";
    String filePrefix = "";

    public FullExperiment(String symbols, String subDir, String filePrefix ) {
        this.symbols = symbols;
        this.subDir = subDir;
        this.filePrefix = filePrefix;
    }

    public void run() {
        runOnseSet(ExperimentType.Pair);
        runOnseSet(ExperimentType.Trio);
        runOnseSet(ExperimentType.Quad);
    }

    private void runOnseSet(ExperimentType e) {
        String date = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        ExperimentType experiment = e;
        String resultFile = filePrefix + experiment.toString() + "_" + date ;
        PortfolioExperiment.setSymbolsFileName(symbols, subDir, resultFile, resultLimit);
        PortfolioExperiment.setExperiment(experiment);
        PortfolioExperiment.run();
    }
}
