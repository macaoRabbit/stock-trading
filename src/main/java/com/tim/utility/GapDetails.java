package com.tim.utility;

public class GapDetails {
    Float gap;
    Integer minIndex;
    Integer maxIndex;

    public Float getGap() {
        return gap;
    }

    public void setGap(Float gap) {
        this.gap = gap;
    }

    public Integer getMinIndex() {
        return minIndex;
    }

    public void setMinIndex(Integer minIndex) {
        this.minIndex = minIndex;
    }

    public Integer getMaxIndex() {
        return maxIndex;
    }

    public void setMaxIndex(Integer maxIndex) {
        this.maxIndex = maxIndex;
    }

    public GapDetails(Float gap, Integer minIndex, Integer maxIndex) {
        this.gap = gap;
        this.minIndex = minIndex;
        this.maxIndex = maxIndex;
    }
}
