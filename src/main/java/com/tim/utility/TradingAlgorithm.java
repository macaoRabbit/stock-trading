package com.tim.utility;

import com.tim.trade.GroupTradeDayGapPairSwapTrading;
import com.tim.trade.GroupTradeDayGapRatioTrading;
import com.tim.trade.Trading;

import java.util.List;

public enum TradingAlgorithm {
    RATIO_SPLIT,
    PAIR_SWAP,
    CONTROL;

    public static GroupTradeDayGapRatioTrading getAlgorithm(TradingAlgorithm tradingAlgorithm) {
        GroupTradeDayGapRatioTrading g = null;
        switch (tradingAlgorithm) {
            case RATIO_SPLIT:
                g = new GroupTradeDayGapRatioTrading();
                break;
            case PAIR_SWAP:
                g = new GroupTradeDayGapPairSwapTrading();
                break;
        }
        return g;
    }

    public static void manageSeedCost(List<Trading> tradings, TradingAlgorithm tradingAlgorithm, Float seedCost) {
        switch (tradingAlgorithm) {
            case CONTROL:
            case RATIO_SPLIT:
                Float myShareSeedCost = seedCost / tradings.size();
                for (int i = 0; i<tradings.size(); i++) {
                    Trading t = tradings.get(i);
                    t.setSeedCost(myShareSeedCost);
                }
                break;
            case PAIR_SWAP:
                Trading t = tradings.get(0);
                t.setSeedCost(seedCost);
                for (int i = 1; i<tradings.size(); i++) {
                    t = tradings.get(i);
                    t.setSeedCost(0.0f);
                }
                break;
        }
    }

    public static void manageSeedCost(int index, List<Trading> tradings, TradingAlgorithm tradingAlgorithm, Float seedCost) {
        switch (tradingAlgorithm) {
            case CONTROL:
            case RATIO_SPLIT:
                manageSeedCost(tradings, tradingAlgorithm, seedCost);
                break;
            case PAIR_SWAP:
                Trading t = tradings.get(0);
                for (int i = 0; i<tradings.size(); i++) {
                    t = tradings.get(i);
                    if (i == index) {
                        t.setSeedCost(seedCost);
                    } else {
                        t.setSeedCost(0.0f);
                    }
                }
                break;
        }
    }
}
