package com.tim.result;

import java.util.ArrayList;
import java.util.List;

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

}
