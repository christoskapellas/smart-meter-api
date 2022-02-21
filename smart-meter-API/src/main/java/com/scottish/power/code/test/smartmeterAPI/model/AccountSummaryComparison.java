package com.scottish.power.code.test.smartmeterAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSummaryComparison {
    private long accountId;
    private List<GasReadingSummaryEntity> gasReadings;
    private List<ElectricityReadingSummaryEntity> elecReadings;
    private double gasComparison;
    private double elecComparison;
}
