package com.tim.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CsvParser {
    static final String COMMA_DELIMITER = ",";
    Integer recordLimit = 1300;
    String fileName;

    public CsvParser(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getRecordLimit() {
        return recordLimit;
    }

    public void setRecordLimit(Integer recordLimit) {
        this.recordLimit = recordLimit;
    }

    public List<List<String>> getRecords() {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(getFileName()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

    public List<List<String>> getLastestRecords() {
        List<List<String>> all = getRecords();
        if (all.size() <= recordLimit + 1) {
            all.remove(0);
            return all;
        }
        List<List<String>> records = new ArrayList<>();
        int base = all.size() - recordLimit;
        for (int i = 0; i< recordLimit; i++) {
            records.add(all.get(base + i));
        }
        return records;
    }

    public List<DailyQuote> getLatestStockData() {
        List<DailyQuote> stockQuotes = new ArrayList<>();
        List<List<String>> latests = getLastestRecords();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        latests.forEach(i -> {
            try {
                Date date = formatter.parse(i.get(0));
                Float open = Float.parseFloat(i.get(1));
                Float high = Float.parseFloat(i.get(2));
                Float low = Float.parseFloat(i.get(3));
                Float close = Float.parseFloat(i.get(4));
                Float volumn = Float.parseFloat(i.get(6));
                DailyQuote s = new DailyQuote(i.get(0), date, open, high, low, close, volumn);
                stockQuotes.add(s);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        return stockQuotes;
    }
}
