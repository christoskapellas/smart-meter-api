package com.scottish.power.code.test.smartmeterAPI.DTOs;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "GAS_READINGS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GasReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private long id;

    @Column(name="METER_ID")
    private long meterID;

    @Column(name="READING")
    private double reading;

    @Column(name="READING_DATE")
    private Date readingDate;

    @ManyToOne
    @JoinColumn(name="ACCOUNT_ID", nullable=false)
    private Account account;
}
