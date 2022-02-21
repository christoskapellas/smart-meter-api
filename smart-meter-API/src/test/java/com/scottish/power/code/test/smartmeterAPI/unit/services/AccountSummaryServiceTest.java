package com.scottish.power.code.test.smartmeterAPI.unit.services;

import com.scottish.power.code.test.smartmeterAPI.DAOs.DAO;
import com.scottish.power.code.test.smartmeterAPI.DTOs.Account;
import com.scottish.power.code.test.smartmeterAPI.DTOs.ElectricityReading;
import com.scottish.power.code.test.smartmeterAPI.DTOs.GasReading;
import com.scottish.power.code.test.smartmeterAPI.mappers.Mapper;
import com.scottish.power.code.test.smartmeterAPI.model.AccountSummary;
import com.scottish.power.code.test.smartmeterAPI.model.ElectricityReadingSummaryEntity;
import com.scottish.power.code.test.smartmeterAPI.model.GasReadingSummaryEntity;
import com.scottish.power.code.test.smartmeterAPI.services.AccountSummaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountSummaryServiceTest {

    @Mock
    private DAO<Account,Long> accountsDAO;
    @Mock
    private DAO<GasReading,Long> gasReadingsDAO;
    @Mock
    private DAO<ElectricityReading,Long> electricityReadingsDAO;
    @Mock
    private Mapper<List<ElectricityReading>, List<ElectricityReadingSummaryEntity>> electricityReadingToSummaryEntityMapper;
    @Mock
    private Mapper<List<GasReading>, List<GasReadingSummaryEntity>> gasReadingToSummaryEntityMapper;
    private AccountSummaryService accountSummaryService;

    @BeforeEach
    public void setUp(){
        accountSummaryService = new AccountSummaryService(accountsDAO, gasReadingsDAO, electricityReadingsDAO, electricityReadingToSummaryEntityMapper, gasReadingToSummaryEntityMapper);
    }

    @Test
    public void readData() {
        accountSummaryService.readData();
        Mockito.verify(accountsDAO).findAll();
        Mockito.verify(gasReadingsDAO).findAll();
        Mockito.verify(electricityReadingsDAO).findAll();
    }

    @Test
    public void find() {
        Mockito.when(accountsDAO.findById(1l)).thenReturn(Optional.of(new Account(1l)));
        accountSummaryService.find(1l);
        Mockito.verify(accountsDAO).findById(1l);
        Mockito.verify(gasReadingsDAO).findAll();
        Mockito.verify(electricityReadingsDAO).findAll();
    }

    @Test
    public void save() {
        AccountSummary accountSummary = new AccountSummary(1, new ArrayList<>(), new ArrayList<>());
        accountSummaryService.save(accountSummary);
        Mockito.verify(accountsDAO).saveAndFlush(new Account(accountSummary.getAccountId()));
    }
}