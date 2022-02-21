package com.scottish.power.code.test.smartmeterAPI.integration.services;

import com.scottish.power.code.test.smartmeterAPI.DAOs.DAO;
import com.scottish.power.code.test.smartmeterAPI.DTOs.Account;
import com.scottish.power.code.test.smartmeterAPI.DTOs.ElectricityReading;
import com.scottish.power.code.test.smartmeterAPI.DTOs.GasReading;
import com.scottish.power.code.test.smartmeterAPI.mappers.ElectricityReadingsToElectricityReadingSummaryEntitiesMapper;
import com.scottish.power.code.test.smartmeterAPI.mappers.GasReadingsToGasReadingSummaryEntitiesMapper;
import com.scottish.power.code.test.smartmeterAPI.model.AccountSummary;
import com.scottish.power.code.test.smartmeterAPI.model.ElectricityReadingSummaryEntity;
import com.scottish.power.code.test.smartmeterAPI.model.GasReadingSummaryEntity;
import com.scottish.power.code.test.smartmeterAPI.services.AccountSummaryService;
import com.scottish.power.code.test.smartmeterAPI.services.DataService;
import com.scottish.power.code.test.smartmeterAPI.util.DayDifferenceCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DataJpaTest
public class AccountSummaryServiceTest {

    @Autowired
    private DAO<Account,Long> accountsDAO;
    @Autowired
    private DAO<GasReading,Long> gasReadingsDAO;
    @Autowired
    private DAO<ElectricityReading,Long> electricityReadingsDAO;
    private DataService<AccountSummary,Long> service;

    @BeforeEach
    public void setUp() {
        DayDifferenceCalculator dayDifferenceCalculator = new DayDifferenceCalculator();
        service = new AccountSummaryService(accountsDAO, gasReadingsDAO, electricityReadingsDAO, new ElectricityReadingsToElectricityReadingSummaryEntitiesMapper(dayDifferenceCalculator), new GasReadingsToGasReadingSummaryEntitiesMapper(dayDifferenceCalculator));
    }

    @Test
    public void testServiceReadsAccountSummaryData() {
        List<AccountSummary> accountSummaries = service.readData();
        Assertions.assertEquals(3, accountSummaries.size());
    }

    @Test
    public void testServiceFindsByIdAccountSummaryData() {
        long id = 1;
        AccountSummary accountSummary = service.find(id);
        Assertions.assertNotNull(accountSummary);
    }

    @Test
    public void testServiceAddsMultipleMeterReadingsToNewAccount() {
        long accountId = 4;
        int sizeOfAccounts = accountsDAO.findAll().size();
        int sizeOfGasReadings = gasReadingsDAO.findAll().size();
        int sizeOfElectricityReadings = electricityReadingsDAO.findAll().size();
        List<GasReadingSummaryEntity> gasReadings = new ArrayList<>();
        addAGasMeterReading(gasReadings);
        List<ElectricityReadingSummaryEntity> electricityReadings = new ArrayList<>();
        addElectricityMeterReading(electricityReadings);
        AccountSummary accountSummary = new AccountSummary(accountId, gasReadings, electricityReadings);
        service.save(accountSummary);
        Assertions.assertEquals(sizeOfAccounts + 1, accountsDAO.findAll().size());
        Assertions.assertEquals( sizeOfGasReadings + 1, gasReadingsDAO.findAll().size());
        Assertions.assertEquals(sizeOfElectricityReadings + 1, electricityReadingsDAO.findAll().size());
    }

    @Test
    public void testServiceAddsMultipleMeterReadingsToExistingAccount() {
        long accountId = 1;
        int sizeOfAccounts = accountsDAO.findAll().size();
        int sizeOfGasReadings = gasReadingsDAO.findAll().size();
        int sizeOfElectricityReadings = electricityReadingsDAO.findAll().size();
        List<GasReadingSummaryEntity> gasReadings = new ArrayList<>();
        addAGasMeterReading(gasReadings);
        List<ElectricityReadingSummaryEntity> electricityReadings = new ArrayList<>();
        addElectricityMeterReading(electricityReadings);
        AccountSummary accountSummary = new AccountSummary(accountId, gasReadings, electricityReadings);
        service.save(accountSummary);
        Assertions.assertEquals(sizeOfAccounts, accountsDAO.findAll().size());
        Assertions.assertEquals( sizeOfGasReadings + 1, gasReadingsDAO.findAll().size());
        Assertions.assertEquals(sizeOfElectricityReadings + 1, electricityReadingsDAO.findAll().size());
    }

    @Test
    public void testServiceAddsSingleMeterReadingsToNewAccount(){
        long accountId = 4;
        int sizeOfAccounts = accountsDAO.findAll().size();
        int sizeOfGasReadings = gasReadingsDAO.findAll().size();
        int sizeOfElectricityReadings = electricityReadingsDAO.findAll().size();
        List<GasReadingSummaryEntity> gasReadings = new ArrayList<>();
        addAGasMeterReading(gasReadings);
        AccountSummary accountSummary = new AccountSummary(accountId, gasReadings, null);
        service.save(accountSummary);
        Assertions.assertEquals(sizeOfAccounts + 1, accountsDAO.findAll().size());
        Assertions.assertEquals( sizeOfGasReadings + 1, gasReadingsDAO.findAll().size());
        Assertions.assertEquals(sizeOfElectricityReadings, electricityReadingsDAO.findAll().size());
    }

    @Test
    public void testServiceAddsSingleMeterReadingsToExistingAccount(){
        long accountId = 1;
        int sizeOfAccounts = accountsDAO.findAll().size();
        int sizeOfGasReadings = gasReadingsDAO.findAll().size();
        int sizeOfElectricityReadings = electricityReadingsDAO.findAll().size();
        List<ElectricityReadingSummaryEntity> electricityReadings = new ArrayList<>();
        addElectricityMeterReading(electricityReadings);
        AccountSummary accountSummary = new AccountSummary(accountId, null, electricityReadings);
        service.save(accountSummary);
        Assertions.assertEquals(sizeOfAccounts, accountsDAO.findAll().size());
        Assertions.assertEquals( sizeOfGasReadings, gasReadingsDAO.findAll().size());
        Assertions.assertEquals(sizeOfElectricityReadings + 1, electricityReadingsDAO.findAll().size());
    }

    @Test
    public void testServiceDoesNotAddDuplicateMeterReadings() {
        long accountId = 4;
        int sizeOfAccounts = accountsDAO.findAll().size();
        int sizeOfGasReadings = gasReadingsDAO.findAll().size();
        int sizeOfElectricityReadings = electricityReadingsDAO.findAll().size();
        List<GasReadingSummaryEntity> gasReadings = new ArrayList<>();
        addAGasMeterReading(gasReadings);
        addAGasMeterReading(gasReadings);
        List<ElectricityReadingSummaryEntity> electricityReadings = new ArrayList<>();
        addElectricityMeterReading(electricityReadings);
        addElectricityMeterReading(electricityReadings);
        AccountSummary accountSummary = new AccountSummary(accountId, gasReadings, electricityReadings);
        service.save(accountSummary);
        Assertions.assertEquals(sizeOfAccounts + 1, accountsDAO.findAll().size());
        Assertions.assertEquals( sizeOfGasReadings + 1, gasReadingsDAO.findAll().size());
        Assertions.assertEquals(sizeOfElectricityReadings + 1, electricityReadingsDAO.findAll().size());
    }

    private void addElectricityMeterReading(List<ElectricityReadingSummaryEntity> electricityReadings) {
        ElectricityReadingSummaryEntity electricityReading = new ElectricityReadingSummaryEntity();
        electricityReading.setMeterId(5);
        electricityReading.setReading(5.0);
        electricityReading.setDate(new Date());
        electricityReadings.add(electricityReading);
    }

    private void addAGasMeterReading(List<GasReadingSummaryEntity> gasReadings) {
        GasReadingSummaryEntity gasReading = new GasReadingSummaryEntity();
        gasReading.setMeterId(5);
        gasReading.setReading(5.0);
        gasReading.setDate(new Date());
        gasReadings.add(gasReading);
    }
}