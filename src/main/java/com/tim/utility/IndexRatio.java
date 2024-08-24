package com.tim.utility;

public class IndexRatio {
    Integer index;
    Float ratio;

    public IndexRatio(Integer index, Float ratio) {
        this.ratio = ratio;
        this.index = index;
    }

    public Float getRatio() {
        return ratio;
    }

    public void setRatio(Float ratio) {
        this.ratio = ratio;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
