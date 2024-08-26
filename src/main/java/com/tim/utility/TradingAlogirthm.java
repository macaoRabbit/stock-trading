package com.tim.utility;

import com.tim.trade.GroupGapPairSwapTrading;
import com.tim.trade.GroupGapRatioTrading;
import com.tim.trade.Trading;

import java.util.List;

public enum TradingAlogirthm {
    RATIO_SPLIT,
    PAIR_SWAP,
    CONTROL;

    public static GroupGapRatioTrading getAlgorithm(TradingAlogirthm tradingAlogirthm) {
        GroupGapRatioTrading g = null;
        switch (tradingAlogirthm) {
            case RATIO_SPLIT:
                g = new GroupGapRatioTrading();
                break;
            case PAIR_SWAP:
                g = new GroupGapPairSwapTrading();
                break;
        }
        return g;
    }

    public static void manageSeedCost(List<Trading> tradings, TradingAlogirthm tradingAlogirthm, Float seedCost) {
        switch (tradingAlogirthm) {
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
}
