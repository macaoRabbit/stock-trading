package com.tim.experiment;

import com.tim.trade.GroupGapTrading;
import com.tim.trade.Trading;
import com.tim.utility.FloatRange;

import java.util.ArrayList;
import java.util.List;

public class FullPairTradingExperiment {
    List<Trading> tradings = new ArrayList<>();
    FloatRange gapRange = new FloatRange(0.0f, 0.2f, 0.025f);
    FloatRange powerRange = new FloatRange(0.0f, 5.0f, 1.0f);

    public FullPairTradingExperiment(List<Trading> tradings) {
        this.tradings = tradings;
    }

    public List<Trading> getTradings() {
        return tradings;
    }

    public FloatRange getGapRange() {
        return gapRange;
    }

    public void setGapRange(FloatRange gapRange) {
        this.gapRange = gapRange;
    }

    public FloatRange getPowerRange() {
        return powerRange;
    }

    public void setPowerRange(FloatRange powerRange) {
        this.powerRange = powerRange;
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

//                GroupGapTradingExperiment e = new GroupGapTradingExperiment(g, 0.2f, 5.0f);
                GroupGapTradingExperiment e = new GroupGapTradingExperiment(g, gapRange, powerRange);
                e.run();
            }
        }
    }
}
