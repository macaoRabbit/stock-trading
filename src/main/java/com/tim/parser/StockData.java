package com.tim.parser;

import java.util.Date;

public class StockData {
    private String stringDate;
    private Date date;
    private Float open;
    private Float high;
    private Float low;
    private Float close;
    private Float volumn;

    public StockData(String stringDate, Date date, Float open, Float high, Float low, Float close, Float volumn) {
        this.stringDate = stringDate;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volumn = volumn;
    }

    public Date getDate() {
        return date;
    }

    public Float getOpen() {
        return open;
    }

    public Float getHigh() {
        return high;
    }

    public Float getLow() {
        return low;
    }

    public Float getClose() {
        return close;
    }

    public Float getVolumn() {
        return volumn;
    }

    public String getStringDate() {
        return stringDate;
    }
}
