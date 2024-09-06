package com.tim.utility;

import java.util.List;

public class GapDetails {
    Float gap;
    List<IndexRatio> mins;
    List<IndexRatio> maxs;
    String gapDay;
    Integer duration;

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

    public String getGapDay() {
        return gapDay;
    }

    public Integer getDuration() {
        return duration;
    }

    public GapDetails(Float gap, List<IndexRatio> minIndex, List<IndexRatio> maxIndex, String gapDay, Integer duration) {
        this.gap = gap;
        this.mins = minIndex;
        this.maxs = maxIndex;
        this.gapDay = gapDay;
        this.duration = duration;
    }
}
