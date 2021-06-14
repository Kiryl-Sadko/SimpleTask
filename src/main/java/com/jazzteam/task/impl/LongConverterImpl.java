package com.jazzteam.task.impl;

import com.jazzteam.task.ArrayReader;
import com.jazzteam.task.LongConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.jazzteam.task.StaticValues.*;

public class LongConverterImpl implements LongConverter {

    private final ArrayReader arrayReader = new ArrayReaderCSV();
    private final String[][] numberDimensions = arrayReader
            .fromFile(new File("src/main/resources/dimensions_library.csv"));

    public LongConverterImpl() {
    }

    public String toString(Long number) {
        StringBuilder resultString = new StringBuilder();
        List<Number> listOfPeriods = splitByPeriods(number);
        int periodCount = listOfPeriods.size();

        for (Number period : listOfPeriods) {
            int gender = Integer.parseInt(numberDimensions[periodCount][3]);
            long valueOfPeriod = Long.parseLong(period.toString());
            if (valueOfPeriod == 0 && periodCount > 1) {
                periodCount--;
                continue;
            }
            appendValueOfPeriod(resultString, gender, valueOfPeriod);
            appendNumberDimension(resultString, valueOfPeriod, periodCount);
            periodCount--;
        }
        return resultString.toString();
    }

    private void appendValueOfPeriod(StringBuilder resultString, int gender, long valueOfPeriod) {
        String valueOfPeriodString = fillPeriodUpToThreeNumbers(valueOfPeriod);
        appendHundreds(resultString, valueOfPeriod, valueOfPeriodString);

        int numberOfTens = Integer.parseInt(valueOfPeriodString.substring(1, 2));
        int numberOfUnits = Integer.parseInt(valueOfPeriodString.substring(2, 3));
        int numberOfDoubleDigits = Integer.parseInt(valueOfPeriodString.substring(1, 3));
        if (numberOfDoubleDigits > 20) {
            resultString
                    .append(TENS[numberOfTens])
                    .append(" ");
            resultString
                    .append(DIGIT_GENDER[gender][numberOfUnits])
                    .append(" ");
        } else {
            if (numberOfDoubleDigits > 9)
                resultString
                        .append(DOUBLE_DIGITS[numberOfDoubleDigits - 9])
                        .append(" ");
            else
                resultString
                        .append(DIGIT_GENDER[gender][numberOfUnits])
                        .append(" ");
        }
    }

    private void appendHundreds(StringBuilder resultString, long valueOfPeriod, String stringFromNumber) {
        if (valueOfPeriod > 99) {
            int numberOfHundred = Integer.parseInt(stringFromNumber.substring(0, 1));
            resultString
                    .append(HUNDREDS[numberOfHundred])
                    .append(" ");
        }
    }

    private void appendNumberDimension(StringBuilder resultString, long valueOfPeriod, int periodCount) {
        resultString
                .append(
                        convertToString(valueOfPeriod, numberDimensions[periodCount][0],
                                numberDimensions[periodCount][1], numberDimensions[periodCount][2]))
                .append(" ");
    }

    public String convertToString(long number, String formForUnit,
                                  String formForLessThanFiveNumber,
                                  String formForMoreThanFiveNumber) {

        number = Math.abs(number) % 100;
        if (number > 10 && number < 20) return formForMoreThanFiveNumber;

        long units = number % 10;
        if (units > 1 && units < 5) return formForLessThanFiveNumber;
        if (units == 1) return formForUnit;
        return formForMoreThanFiveNumber;
    }

    private String fillPeriodUpToThreeNumbers(long valueOfPeriod) {
        String valueOfPeriodString = String.valueOf(valueOfPeriod);
        if (valueOfPeriodString.length() == 1) valueOfPeriodString = "00" + valueOfPeriodString;
        if (valueOfPeriodString.length() == 2) valueOfPeriodString = "0" + valueOfPeriodString;
        return valueOfPeriodString;
    }


    private List<Number> splitByPeriods(long digit) {
        List<Number> listOfPeriods = new ArrayList<>();
        while (digit > 999) {
            long startPeriod = digit % 1000;
            long nextPeriod = digit / 1000;
            listOfPeriods.add(startPeriod);
            digit = nextPeriod;
        }
        listOfPeriods.add(digit);
        Collections.reverse(listOfPeriods);
        return listOfPeriods;
    }
}
