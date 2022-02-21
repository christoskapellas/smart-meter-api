package com.scottish.power.code.test.smartmeterAPI.util;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DayDifferenceCalculator {
    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
}
