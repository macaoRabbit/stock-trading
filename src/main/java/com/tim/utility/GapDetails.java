package com.tim.utility;

import java.util.List;

public class GapDetails {
    Float gap;
    List<IndexRatio> mins;
    List<IndexRatio> maxs;

    public Float getGap() {
        return gap;
    }

    public List<IndexRatio> getMins() {
        return mins;
    }

    public void setMins(List<IndexRatio> mins) {
        this.mins = mins;
    }

    public List<IndexRatio> getMaxs() {
        return maxs;
    }

    public void setMaxs(List<IndexRatio> maxs) {
        this.maxs = maxs;
    }

    public GapDetails(Float gap, List<IndexRatio> minIndex, List<IndexRatio> maxIndex) {
        this.gap = gap;
        this.mins = minIndex;
        this.maxs = maxIndex;
    }
}
