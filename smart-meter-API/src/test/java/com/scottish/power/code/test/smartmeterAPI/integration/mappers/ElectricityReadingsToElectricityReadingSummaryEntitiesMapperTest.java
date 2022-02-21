package com.scottish.power.code.test.smartmeterAPI.integration.mappers;

import com.scottish.power.code.test.smartmeterAPI.DTOs.Account;
import com.scottish.power.code.test.smartmeterAPI.DTOs.ElectricityReading;
import com.scottish.power.code.test.smartmeterAPI.mappers.ElectricityReadingsToElectricityReadingSummaryEntitiesMapper;
import com.scottish.power.code.test.smartmeterAPI.mappers.Mapper;
import com.scottish.power.code.test.smartmeterAPI.model.ElectricityReadingSummaryEntity;
import com.scottish.power.code.test.smartmeterAPI.util.DayDifferenceCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ElectricityReadingsToElectricityReadingSummaryEntitiesMapperTest {

    @Test
    public void TestMapTwoReadingsWithCorrectDetails() throws ParseException {
        Mapper<List<ElectricityReading>, List<ElectricityReadingSummaryEntity>> mapper = new ElectricityReadingsToElectricityReadingSummaryEntitiesMapper(new DayDifferenceCalculator());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<ElectricityReading> electricityReadings = new ArrayList<>();
        Account account = new Account(1);
        electricityReadings.add(new ElectricityReading(1, 2, 5.0, format.parse("2022-01-01"), account));
        electricityReadings.add(new ElectricityReading(2, 2, 36.0, format.parse("2022-02-01"), account));
        List<ElectricityReadingSummaryEntity> electricityReadingSummaryEntities = mapper.map(electricityReadings);
        Assertions.assertEquals(2, electricityReadingSummaryEntities.size());
        Assertions.assertEquals(0, electricityReadingSummaryEntities.get(0).getPeriodSinceLastRead());
        Assertions.assertEquals(0, electricityReadingSummaryEntities.get(0).getUsageSinceLastRead());
        Assertions.assertEquals(1, electricityReadingSummaryEntities.get(1).getAvgDailyUsage());
        Assertions.assertEquals(31, electricityReadingSummaryEntities.get(1).getPeriodSinceLastRead());
        Assertions.assertEquals(31, electricityReadingSummaryEntities.get(1).getUsageSinceLastRead());
        Assertions.assertEquals(1, electricityReadingSummaryEntities.get(1).getAvgDailyUsage());
    }
}