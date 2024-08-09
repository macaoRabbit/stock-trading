package com.tim.experiment;

import com.tim.trade.GroupGapTrading;
import com.tim.trade.Trading;

import java.util.ArrayList;
import java.util.List;

public class FullPairTradingExperiment {
    List<Trading> tradings = new ArrayList<>();

    public FullPairTradingExperiment(List<Trading> tradings) {
        this.tradings = tradings;
    }

    public List<Trading> getTradings() {
        return tradings;
    }

    public void setTradings(List<Trading> tradings) {
        this.tradings = tradings;
    }

    public void run() {
        for (int i = 0; i < tradings.size(); i++) {
            for (int j = i + 1; j < tradings.size(); j++) {
                Trading t1 = tradings.get(i);
                Trading t2 = tradings.get(j);

                GroupGapTrading g = new GroupGapTrading();
                g.getTradings().add(t1);
                g.getTradings().add(t2);
                g.initQuotesWithCsvFileForAllTradings();
                g.matchQuotesForAllTradings();

                GroupGapTradingExperiment e = new GroupGapTradingExperiment(g, 0.2f, 5.0f);
                e.run();
            }
        }
    }
}
