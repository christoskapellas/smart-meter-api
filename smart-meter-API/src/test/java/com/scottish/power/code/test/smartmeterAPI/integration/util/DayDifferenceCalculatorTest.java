package com.scottish.power.code.test.smartmeterAPI.integration.util;

import com.scottish.power.code.test.smartmeterAPI.util.DayDifferenceCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

public class DayDifferenceCalculatorTest {

    private DayDifferenceCalculator dayDifferenceCalculator;
    private DateFormat format;

    @BeforeEach
    void setUp() {
        format = new SimpleDateFormat("yyyy-MM-dd");
        dayDifferenceCalculator = new DayDifferenceCalculator();
    }

    @Test
    public void testTheDayDifferenceOfADayIsOneDay() throws ParseException {
        Assertions.assertEquals(1, dayDifferenceCalculator.daysBetween(format.parse("2022-01-01"), format.parse("2022-01-02")));
    }

    @Test
    public void testTheDayDifferenceOfAWeekIsSevenDays() throws ParseException {
        Assertions.assertEquals(7, dayDifferenceCalculator.daysBetween(format.parse("2022-01-01"), format.parse("2022-01-08")));
    }

    @Test
    public void testTheDayDifferenceOfAMonthIsThirtyOneDays() throws ParseException {
        Assertions.assertEquals(31, dayDifferenceCalculator.daysBetween(format.parse("2022-01-01"), format.parse("2022-02-01")));
    }
}