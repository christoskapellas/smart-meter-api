package com.scottish.power.code.test.smartmeterAPI.mappers;

import com.scottish.power.code.test.smartmeterAPI.DTOs.ElectricityReading;
import com.scottish.power.code.test.smartmeterAPI.model.ElectricityReadingSummaryEntity;
import com.scottish.power.code.test.smartmeterAPI.util.DayDifferenceCalculator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ElectricityReadingsToElectricityReadingSummaryEntitiesMapper implements Mapper<List<ElectricityReading>, List<ElectricityReadingSummaryEntity>> {

    private final DayDifferenceCalculator dayDifferenceCalculator;

    public ElectricityReadingsToElectricityReadingSummaryEntitiesMapper(DayDifferenceCalculator dayDifferenceCalculator) {
        this.dayDifferenceCalculator = dayDifferenceCalculator;
    }

    @Override
    public List<ElectricityReadingSummaryEntity> map(List<ElectricityReading> electricityReadings) {
        List<ElectricityReadingSummaryEntity> electricityReadingSummaryEntities = electricityReadings.stream().map(e->new ElectricityReadingSummaryEntity(e.getId(),e.getMeterID(),e.getReading(),e.getReadingDate(),0,0,0)).collect(Collectors.toList());
        double avgDailyUsage = electricityReadingSummaryEntities.size() > 1? (electricityReadingSummaryEntities.get(electricityReadingSummaryEntities.size()-1).getReading() - electricityReadingSummaryEntities.get(0).getReading())/((double)dayDifferenceCalculator.daysBetween( electricityReadingSummaryEntities.get(0).getDate(), electricityReadingSummaryEntities.get(electricityReadingSummaryEntities.size()-1).getDate())) : 0;
        ElectricityReadingSummaryEntity previousElecReadingSummaryEntity = null;
        for (ElectricityReadingSummaryEntity electricityReadingSummaryEntity : electricityReadingSummaryEntities) {
            if (previousElecReadingSummaryEntity != null) {
                electricityReadingSummaryEntity.setUsageSinceLastRead(electricityReadingSummaryEntity.getReading() - previousElecReadingSummaryEntity.getReading());
                electricityReadingSummaryEntity.setPeriodSinceLastRead(dayDifferenceCalculator.daysBetween(previousElecReadingSummaryEntity.getDate(), electricityReadingSummaryEntity.getDate()));
            }
            electricityReadingSummaryEntity.setAvgDailyUsage(avgDailyUsage);
            previousElecReadingSummaryEntity = electricityReadingSummaryEntity;
        }
        return electricityReadingSummaryEntities;
    }


}
