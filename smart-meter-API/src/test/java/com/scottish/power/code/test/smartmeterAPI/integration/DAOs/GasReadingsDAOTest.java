package com.scottish.power.code.test.smartmeterAPI.integration.DAOs;

import com.scottish.power.code.test.smartmeterAPI.DAOs.DAO;
import com.scottish.power.code.test.smartmeterAPI.DTOs.Account;
import com.scottish.power.code.test.smartmeterAPI.DTOs.GasReading;
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
public class GasReadingsDAOTest {

    @Autowired
    private DAO<GasReading,Long> gasReadingsDAO;

    @Test
    public void testThatGasReadingsCanBeRetrieved(){
        List<GasReading> gasReadings = gasReadingsDAO.findAll();
        Assertions.assertEquals(9, gasReadings.size());
    }

    @Test
    public void testThatAGasReadingCanBeRetrievedUsingId(){
        long id = 9;
        GasReading gasReading = gasReadingsDAO.findById(id).get();
        Assertions.assertNotNull(gasReading);
        Assertions.assertEquals(id, gasReading.getId());
    }

    @Test
    public void testThatAGasReadingCanBeStored(){
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
        GasReading gasReading = new GasReading();
        gasReading.setMeterID(meterId);
        gasReading.setReading(reading);
        gasReading.setReadingDate(date);
        gasReading.setAccount(account);
        gasReadingsDAO.saveAndFlush(gasReading);
        GasReading retrievedGasReading = gasReadingsDAO.findById(id).get();
        Assertions.assertNotNull(retrievedGasReading);
        Assertions.assertEquals(id, retrievedGasReading.getId());
        Assertions.assertEquals(meterId, retrievedGasReading.getMeterID());
        Assertions.assertEquals(reading, retrievedGasReading.getReading());
        Assertions.assertEquals(date, retrievedGasReading.getReadingDate());
        Assertions.assertEquals(account.getId(), retrievedGasReading.getAccount().getId());
    }
}