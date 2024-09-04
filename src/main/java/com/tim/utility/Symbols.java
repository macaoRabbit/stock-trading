package com.tim.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Symbols {
    public static String getSymbolList(String symbols) {
        StringBuilder s = new StringBuilder();
        String[] l = symbols.split(",");
        for (int i=0; i<l.length; i++) {
            s.append(l[i]);
            if (i<l.length - 1) {
                s.append("-");
            }
        }
        return s.toString();
    }

    public static String getStringDate() {
        return (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    }
}
