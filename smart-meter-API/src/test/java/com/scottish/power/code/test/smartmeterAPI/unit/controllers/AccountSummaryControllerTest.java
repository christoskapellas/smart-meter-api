package com.scottish.power.code.test.smartmeterAPI.unit.controllers;

import com.scottish.power.code.test.smartmeterAPI.controllers.AccountSummaryController;
import com.scottish.power.code.test.smartmeterAPI.model.AccountSummary;
import com.scottish.power.code.test.smartmeterAPI.services.DataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class AccountSummaryControllerTest {

    @Mock
    private DataService<AccountSummary,Long> dataService;
    private AccountSummaryController controller;

    @BeforeEach
    public void setUp() {
        controller = new AccountSummaryController(dataService);
    }

    @Test
    public void getAccountSummaries() {
        controller.getAccountSummaries();
        Mockito.verify(dataService).readData();
    }

    @Test
    public void getAccountSummaryById() {
        controller.getAccountSummaryById(1l);
        Mockito.verify(dataService).find(1l);
    }

    @Test
    public void getAccountSummaryComparisonsUsingIds() {

        Mockito.when(dataService.find(1l)).thenReturn(new AccountSummary(1, new ArrayList<>(), new ArrayList<>()));
        Mockito.when(dataService.find(2l)).thenReturn(new AccountSummary(2, new ArrayList<>(), new ArrayList<>()));
        controller.getAccountSummaryComparisonsUsingIds(1l,2l);
        Mockito.verify(dataService).find(1l);
        Mockito.verify(dataService).find(2l);
    }

    @Test
    public void saveAccountDetails() {
        AccountSummary accountSummary = new AccountSummary();
        controller.saveAccountDetails(accountSummary);
        Mockito.verify(dataService).save(accountSummary);
    }
}