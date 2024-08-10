package com.tim.utility;

public class FloatRange {
    Float lower = 0.0f;
    Float upper = 0.0f;
    Float increment = 0.0f;

    public FloatRange(Float lower, Float upper, Float increment) {
        this.lower = lower;
        this.upper = upper;
        this.increment = increment;
    }

    public Float getLower() {
        return lower;
    }

    public void setLower(Float lower) {
        this.lower = lower;
    }

    public Float getUpper() {
        return upper;
    }

    public void setUpper(Float upper) {
        this.upper = upper;
    }

    public Float getIncrement() {
        return increment;
    }

    public void setIncrement(Float increment) {
        this.increment = increment;
    }
}
