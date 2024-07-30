package com.tim.trade;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupQuoteMatcherTest {
    @Test
    public void quoteMatcherTest() {
        String dir = "C:\\GitHubProjects\\data\\";
        String f1 = "AMD.csv";
        String f2 = "NOW.csv";
        List<PairedQuotes> pairedQuotesList = new QuoteMatcher(dir + f1, dir + f2).generateMatchedQuotes();
        assertTrue(pairedQuotesList.size() > 0);
    }
}
