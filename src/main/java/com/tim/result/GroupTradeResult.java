package com.tim.result;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class GroupTradeResult {
    List<GroupTradeResultItem> results = new ArrayList<>();

    public GroupTradeResult() {
    }

    public GroupTradeResult(List<GroupTradeResultItem> results) {
        this.results = results;
    }

    public List<GroupTradeResultItem> getResults() {
        return results;
    }

    public void setResults(List<GroupTradeResultItem> results) {
        this.results = results;
    }

    void printNames() {
        for (GroupTradeResultItem i : results) {
            System.out.print(i.getName() + " ");
        }
        System.out.println();
    }

    void printValues() {
        for (GroupTradeResultItem i : results) {
            System.out.print(i.getValue() + " ");
        }
        System.out.println();
    }

    public void process(List<GroupTradeResult> resultList) {
        System.out.println("====================================================================================");
        resultList.get(0).printNames();
        TreeMap<Float, GroupTradeResult> m = new TreeMap();
        for (GroupTradeResult r : resultList) {
            m.put(Float.parseFloat(r.getResults().get(0).getValue()), r);
        }
        for (GroupTradeResult r : m.descendingMap().values()) {
            r.printValues();
        }
    }
}
