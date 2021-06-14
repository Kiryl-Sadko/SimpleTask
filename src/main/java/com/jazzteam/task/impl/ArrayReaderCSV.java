package com.jazzteam.task.impl;

import com.jazzteam.task.ArrayReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.Scanner;

public class ArrayReaderCSV implements ArrayReader {

    private static final Logger LOGGER = LogManager.getLogger(ArrayReaderCSV.class);

    @Override
    public String[][] fromFile(File file) {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            String[][] resultArray = createArray(file);
            while (scanner.hasNextLine()) {
                int length = resultArray.length;
                for (int i = 0; i < length; i++) {
                    String[] line = scanner.nextLine().trim().split(",");
                    for (int j = 0; j < line.length; j++) {
                        resultArray[i][j] = String.valueOf(line[j]);
                    }
                }
            }
            return resultArray;

        } catch (FileNotFoundException fileNotFoundException) {
            String message = MessageFormat.format("File not found, check path: {0}", file.getPath());
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
    }


    private String[][] createArray(File file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            int columnCount = 0;
            int rowCount = 0;
            if (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().trim().split(",");
                columnCount = line.length;
                rowCount = 1;
                do {
                    scanner.nextLine();
                    rowCount++;
                } while (scanner.hasNext());
            }
            return new String[rowCount][columnCount];
        }
    }
}
