package com.scottish.power.code.test.smartmeterAPI.integration.mappers;

import com.scottish.power.code.test.smartmeterAPI.DTOs.Account;
import com.scottish.power.code.test.smartmeterAPI.DTOs.GasReading;
import com.scottish.power.code.test.smartmeterAPI.mappers.GasReadingsToGasReadingSummaryEntitiesMapper;
import com.scottish.power.code.test.smartmeterAPI.mappers.Mapper;
import com.scottish.power.code.test.smartmeterAPI.model.GasReadingSummaryEntity;
import com.scottish.power.code.test.smartmeterAPI.util.DayDifferenceCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GasReadingsToGasReadingSummaryEntitiesMapperTest {

    @Test
    public void TestMapTwoReadingsWithCorrectDetails() throws ParseException {
        Mapper<List<GasReading>, List<GasReadingSummaryEntity>> mapper = new GasReadingsToGasReadingSummaryEntitiesMapper(new DayDifferenceCalculator());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<GasReading> gasReadings = new ArrayList<>();
        Account account = new Account(1);
        gasReadings.add(new GasReading(1, 2, 5.0, format.parse("2022-01-01"), account));
        gasReadings.add(new GasReading(2, 2, 64.0, format.parse("2022-03-01"), account));
        List<GasReadingSummaryEntity> gasReadingSummaryEntities = mapper.map(gasReadings);
        Assertions.assertEquals(2, gasReadingSummaryEntities.size());
        Assertions.assertEquals(0, gasReadingSummaryEntities.get(0).getPeriodSinceLastRead());
        Assertions.assertEquals(0, gasReadingSummaryEntities.get(0).getUsageSinceLastRead());
        Assertions.assertEquals(1, gasReadingSummaryEntities.get(1).getAvgDailyUsage());
        Assertions.assertEquals(59, gasReadingSummaryEntities.get(1).getPeriodSinceLastRead());
        Assertions.assertEquals(59, gasReadingSummaryEntities.get(1).getUsageSinceLastRead());
        Assertions.assertEquals(1, gasReadingSummaryEntities.get(1).getAvgDailyUsage());
    }
}