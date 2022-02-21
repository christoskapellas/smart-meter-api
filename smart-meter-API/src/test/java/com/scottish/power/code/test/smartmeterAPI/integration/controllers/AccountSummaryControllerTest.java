package com.scottish.power.code.test.smartmeterAPI.integration.controllers;

import com.scottish.power.code.test.smartmeterAPI.DAOs.DAO;
import com.scottish.power.code.test.smartmeterAPI.DTOs.Account;
import com.scottish.power.code.test.smartmeterAPI.DTOs.ElectricityReading;
import com.scottish.power.code.test.smartmeterAPI.DTOs.GasReading;
import com.scottish.power.code.test.smartmeterAPI.controllers.AccountSummaryController;
import com.scottish.power.code.test.smartmeterAPI.mappers.ElectricityReadingsToElectricityReadingSummaryEntitiesMapper;
import com.scottish.power.code.test.smartmeterAPI.mappers.GasReadingsToGasReadingSummaryEntitiesMapper;
import com.scottish.power.code.test.smartmeterAPI.model.AccountSummary;
import com.scottish.power.code.test.smartmeterAPI.model.AccountSummaryComparison;
import com.scottish.power.code.test.smartmeterAPI.services.AccountSummaryService;
import com.scottish.power.code.test.smartmeterAPI.services.DataService;
import com.scottish.power.code.test.smartmeterAPI.util.DayDifferenceCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class AccountSummaryControllerTest {

    @Autowired
    private DAO<Account,Long> accountsDAO;
    @Autowired
    private DAO<GasReading,Long> gasReadingsDAO;
    @Autowired
    private DAO<ElectricityReading,Long> electricityReadingsDAO;
    private DataService<AccountSummary,Long> service;
    private AccountSummaryController controller;

    @BeforeEach
    public void setUp() {
        DayDifferenceCalculator dayDifferenceCalculator = new DayDifferenceCalculator();
        service = new AccountSummaryService(accountsDAO, gasReadingsDAO, electricityReadingsDAO, new ElectricityReadingsToElectricityReadingSummaryEntitiesMapper(dayDifferenceCalculator), new GasReadingsToGasReadingSummaryEntitiesMapper(dayDifferenceCalculator));
        controller = new AccountSummaryController(service);
    }

    @Test
    public void testGetAccountSummariesFromController() {
        List<AccountSummary> accountSummaries = controller.getAccountSummaries();
        Assertions.assertTrue(accountSummaries.size() == 3);
    }

    @Test
    public void testGetAccountSummaryByIdFromController() {
        Long id = 1l;
        AccountSummary accountSummary = controller.getAccountSummaryById(id);
        Assertions.assertNotNull(accountSummary);
        Assertions.assertEquals(1l, accountSummary.getAccountId());
        Assertions.assertEquals(3, accountSummary.getGasReadings().size());
        Assertions.assertEquals(3, accountSummary.getElecReadings().size());
    }

    @Test
    public void testGetComparisonOfTheDifferentAccountSummaries(){
        AccountSummaryComparison accountSummaryComparison = controller.getAccountSummaryComparisonsUsingIds(1l,2l);
        AccountSummary firstAccountSummary = service.find(1l);
        AccountSummary secondAccountSummary = service.find(2l);
        double expectedElecComparison = secondAccountSummary.getElecReadings().get(secondAccountSummary.getElecReadings().size()-1).getReading() - firstAccountSummary.getElecReadings().get(firstAccountSummary.getElecReadings().size()-1).getReading();
        double expectedGasComparison = secondAccountSummary.getGasReadings().get(secondAccountSummary.getGasReadings().size()-1).getReading() - firstAccountSummary.getGasReadings().get(firstAccountSummary.getGasReadings().size()-1).getReading();
        Assertions.assertEquals(firstAccountSummary.getAccountId(), accountSummaryComparison.getAccountId());
        Assertions.assertEquals(firstAccountSummary.getGasReadings().size(), accountSummaryComparison.getGasReadings().size());
        Assertions.assertEquals(firstAccountSummary.getElecReadings().size(), accountSummaryComparison.getElecReadings().size());
        Assertions.assertEquals(expectedElecComparison, accountSummaryComparison.getElecComparison());
        Assertions.assertEquals(expectedGasComparison, accountSummaryComparison.getGasComparison());
    }

    @Test
    public void testGetComparisonOfTheSameAccountSummaryGivesZeroComparisons(){
        AccountSummaryComparison accountSummaryComparison = controller.getAccountSummaryComparisonsUsingIds(1l,1l);
        Assertions.assertEquals(0d, accountSummaryComparison.getElecComparison());
        Assertions.assertEquals(0d, accountSummaryComparison.getGasComparison());
    }

    @Test
    public void testSaveAccountDetailsSavesTheAccount(){
        AccountSummary accountSummary = new AccountSummary(4, new ArrayList<>(), new ArrayList<>());
        controller.saveAccountDetails(accountSummary);
        Assertions.assertNotNull(service.find(4l));
        Assertions.assertEquals(0, service.find(4l).getGasReadings().size());
        Assertions.assertEquals(0, service.find(4l).getElecReadings().size());
    }

}