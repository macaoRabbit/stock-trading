package com.tim.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.List;

public class CsvParserTest {
    @Test
    public void fileNameTest() {
        String dir = "C:\\GitHubProjects\\data\\";
        String fileName = "AMD.csv";
        CsvParser p = new CsvParser(dir + fileName);
        List<List<String>> records = p.getRecords();
        List<List<String>> latests = p.getLastestRecords();
        List<StockData> latestQuotes = p.getLatestStockData();
        assertEquals(dir + fileName, p.getFileName());
    }
}
