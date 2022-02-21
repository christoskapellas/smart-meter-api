package com.scottish.power.code.test.smartmeterAPI.mappers;

import com.scottish.power.code.test.smartmeterAPI.DTOs.GasReading;
import com.scottish.power.code.test.smartmeterAPI.model.GasReadingSummaryEntity;
import com.scottish.power.code.test.smartmeterAPI.util.DayDifferenceCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GasReadingsToGasReadingSummaryEntitiesMapper implements Mapper<List<GasReading>, List<GasReadingSummaryEntity>> {

    private final DayDifferenceCalculator dayDifferenceCalculator;

    public GasReadingsToGasReadingSummaryEntitiesMapper(DayDifferenceCalculator dayDifferenceCalculator) {
        this.dayDifferenceCalculator = dayDifferenceCalculator;
    }

    @Override
    public List<GasReadingSummaryEntity> map(List<GasReading> gasReadings) {
        List<GasReadingSummaryEntity> gasReadingSummaryEntities = gasReadings.stream().map(g->new GasReadingSummaryEntity(g.getId(),g.getMeterID(),g.getReading(),g.getReadingDate(),0,0,0)).collect(Collectors.toList());
        double avgDailyUsage = gasReadingSummaryEntities.size() > 1? (gasReadingSummaryEntities.get(gasReadingSummaryEntities.size()-1).getReading() - gasReadingSummaryEntities.get(0).getReading())/((double)dayDifferenceCalculator.daysBetween(gasReadingSummaryEntities.get(0).getDate(), gasReadingSummaryEntities.get(gasReadingSummaryEntities.size()-1).getDate())) : 0;
        GasReadingSummaryEntity previousGasSummaryEntity = null;
        for (GasReadingSummaryEntity gasReading : gasReadingSummaryEntities) {
            if (previousGasSummaryEntity != null) {
                gasReading.setUsageSinceLastRead(gasReading.getReading() - previousGasSummaryEntity.getReading());
                gasReading.setPeriodSinceLastRead(dayDifferenceCalculator.daysBetween(previousGasSummaryEntity.getDate(), gasReading.getDate()));
            }
            gasReading.setAvgDailyUsage(avgDailyUsage);
            previousGasSummaryEntity = gasReading;
        }
        return gasReadingSummaryEntities;
    }
}
