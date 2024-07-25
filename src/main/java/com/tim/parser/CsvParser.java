package com.tim.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvParser {
    static final String COMMA_DELIMITER = ",";
    static final Integer LASTEST_RECORDS = 1300;
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
        if (all.size() <= LASTEST_RECORDS + 1) {
            all.remove(0);
            return all;
        }
        List<List<String>> records = new ArrayList<>();
        int base = all.size() - LASTEST_RECORDS;
        for (int i = 0; i< LASTEST_RECORDS; i++) {
            records.add(all.get(base + i));
        }
        return records;
    }
}
