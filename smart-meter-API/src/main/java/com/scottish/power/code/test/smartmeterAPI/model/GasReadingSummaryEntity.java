package com.scottish.power.code.test.smartmeterAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GasReadingSummaryEntity {
    private long id;
    private long meterId;
    private double reading;
    private Date date;
    private double usageSinceLastRead;
    private int periodSinceLastRead;
    private double avgDailyUsage;
}
