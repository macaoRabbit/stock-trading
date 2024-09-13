package com.tim.test;

import org.junit.jupiter.api.Test;

import java.io.File;

public class FileDirTest {
    @Test
    public void fileDirTest() {
        String pDir = "C:\\GitHubProjects\\testData\\";
        String mySymbols = "IJS--VHT";
        String dir = pDir + mySymbols;
        String fileName = "IJS--VHT_2024_09_13.csv";

        File directory = new File(dir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
