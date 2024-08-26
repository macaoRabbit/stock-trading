package com.tim.utility;

import com.tim.trade.GroupGapPairSwapTrading;
import com.tim.trade.GroupGapRatioTrading;

public enum TradingAlogirthm {
    RATIO_SPLIT,
    PAIR_SWAP;

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
}
