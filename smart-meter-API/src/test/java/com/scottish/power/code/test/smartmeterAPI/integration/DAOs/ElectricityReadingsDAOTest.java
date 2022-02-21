package com.scottish.power.code.test.smartmeterAPI.integration.DAOs;

import com.scottish.power.code.test.smartmeterAPI.DAOs.DAO;
import com.scottish.power.code.test.smartmeterAPI.DTOs.Account;
import com.scottish.power.code.test.smartmeterAPI.DTOs.ElectricityReading;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@DataJpaTest
public class ElectricityReadingsDAOTest {

    @Autowired
    private DAO<ElectricityReading,Long> electricityReadingsDAO;

    @Test
    public void testThatElectricityReadingsCanBeRetrieved(){
        List<ElectricityReading> electricityReadings = electricityReadingsDAO.findAll();
        Assertions.assertEquals(9, electricityReadings.size());
    }

    @Test
    public void testThatAnElectricityReadingCanBeRetrievedUsingId(){
        long id = 9;
        ElectricityReading electricityReading = electricityReadingsDAO.findById(id).get();
        Assertions.assertNotNull(electricityReading);
        Assertions.assertEquals(id, electricityReading.getId());
    }

    @Test
    public void testThatAnElectricityReadingCanBeStored(){
        long id = 10;
        long meterId = 4;
        double reading = 5000.8;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse("2022-04-01");
        } catch (ParseException e) {
            date = new Date();
        }
        Account account = new Account(1);
        ElectricityReading electricityReading = new ElectricityReading();
        electricityReading.setMeterID(meterId);
        electricityReading.setReading(reading);
        electricityReading.setReadingDate(date);
        electricityReading.setAccount(account);
        electricityReadingsDAO.saveAndFlush(electricityReading);
        ElectricityReading retrievedElectricityReading = electricityReadingsDAO.findById(id).get();
        Assertions.assertNotNull(retrievedElectricityReading);
        Assertions.assertEquals(id, retrievedElectricityReading.getId());
        Assertions.assertEquals(meterId, retrievedElectricityReading.getMeterID());
        Assertions.assertEquals(reading, retrievedElectricityReading.getReading());
        Assertions.assertEquals(date, retrievedElectricityReading.getReadingDate());
        Assertions.assertEquals(account.getId(), retrievedElectricityReading.getAccount().getId());
    }
}