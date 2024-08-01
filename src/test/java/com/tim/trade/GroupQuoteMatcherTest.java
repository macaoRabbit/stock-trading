package com.tim.trade;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupQuoteMatcherTest {
    @Test
    public void matchGroupQuoteMissingDatesTest() {
        String dir = "C:\\GitHubProjects\\data\\";
        String f1 = "a1.csv";
        String f2 = "a2.csv";
        String f3 = "a3.csv";
        List<String> files = new ArrayList<>();
        files.add(dir + f1);
        files.add(dir + f2);
        files.add(dir + f3);
        GroupQuoteMatcher g = new GroupQuoteMatcher();
        g.setFileNames(files);
        g.initAllDailyQuotesFromFiles();
        List<GroupQuote> gQuotes = g.createMatchedQuotes();
        assertTrue(gQuotes.size() > 0);
    }
}
