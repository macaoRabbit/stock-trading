package com.tim.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CsvParserTest {
    @Test
    public void fileNameTest () {
        String fileName = "stockQuote.csv";
        CsvParser p = new CsvParser(fileName);
        assertTrue(fileName.equalsIgnoreCase(p.getFileName()));
    }
}
