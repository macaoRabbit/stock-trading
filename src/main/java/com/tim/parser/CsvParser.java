package com.tim.parser;

public class CsvParser {
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
}
