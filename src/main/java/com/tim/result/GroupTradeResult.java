package com.tim.result;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
            System.out.print(i.getValue() + "  ");
        }
        System.out.println();
    }

    private String getCsvVaules() {
        return getCsvItem(true);
    }

    private String getCsvItem(boolean isValue) {
        StringBuffer s = new StringBuffer();
        for (int i=0; i< results.size(); i++) {
            if (isValue) {
                s.append(results.get(i).getValue());
            } else {
                s.append(results.get(i).getName());
            }
            if (i < results.size() - 1) {
                s.append(",");
            }
        }
        return s.toString();
    }

    private String getCsvNames() {
        return getCsvItem(false);
    }

    public void process(List<GroupTradeResult> resultList) {
        System.out.println("====================================================================================");
        resultList.get(0).printNames();
        List<GroupTradeResult> l = getSortedResults(resultList);
        for (GroupTradeResult r : l) {
            r.printValues();
        }
    }

    private static List<GroupTradeResult>  getSortedResults(List<GroupTradeResult> resultList) {
        TreeMap<Float, List<GroupTradeResult>> m = new TreeMap();
        for (GroupTradeResult r : resultList) {
            Float f = Float.parseFloat(r.getResults().get(0).getValue());
            List<GroupTradeResult> l = m.get(f);
            if (l == null) {
                l = new ArrayList<>();
            }
            l.add(r);
            m.put(f, l);
        }
        List<GroupTradeResult> l = new ArrayList<>();
        for (List<GroupTradeResult> r : m.descendingMap().values()) {
            l.addAll(r);
        }
        return l;
    }

    public void setLimit(List<GroupTradeResult> results, Integer resultLimit) {
        List<GroupTradeResult> l = getSortedResults(results);
        results.clear();
        int count = 0;
        for (GroupTradeResult r : l) {
            if (count < resultLimit) {
                results.add(r);
                count++;
            } else {
                break;
            }
        }
    }

    public void save(List<GroupTradeResult> results, String saveFile) {
        try {
            FileWriter file = new FileWriter(saveFile);
            BufferedWriter output = new BufferedWriter(file);

            String name = results.get(0).getCsvNames();
            output.write(name);
            output.newLine();
            List<GroupTradeResult> l = getSortedResults(results);
            for (GroupTradeResult r : l) {
                String value = r.getCsvVaules();
                output.write(value);
                output.newLine();
            }
            output.close();
        }

        catch (Exception e) {
            e.getStackTrace();
        }
    }
}
