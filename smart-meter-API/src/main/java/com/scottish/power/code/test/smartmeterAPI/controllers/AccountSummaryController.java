package com.scottish.power.code.test.smartmeterAPI.controllers;

import com.scottish.power.code.test.smartmeterAPI.model.AccountSummary;
import com.scottish.power.code.test.smartmeterAPI.model.AccountSummaryComparison;
import com.scottish.power.code.test.smartmeterAPI.services.DataService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AccountSummaryController {

    private final DataService<AccountSummary,Long> dataService;

    public AccountSummaryController(DataService<AccountSummary, Long> dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/api/smart/reads")
    public List<AccountSummary> getAccountSummaries(){
        return dataService.readData();
    }

    @GetMapping("/api/smart/reads/{id}")
    public AccountSummary getAccountSummaryById(@PathVariable("id") Long id){
        return dataService.find(id);
    }

    @GetMapping("/api/smart/reads/{id}/compare/{comparisonId}")
    public AccountSummaryComparison getAccountSummaryComparisonsUsingIds(@PathVariable("id") Long id, @PathVariable("comparisonId") Long comparisonId){
        AccountSummary accountSummary = dataService.find(id);
        AccountSummary comparisonAccountSummary = dataService.find(comparisonId);
        double gasComparison = getLastGasReading(comparisonAccountSummary) - getLastGasReading(accountSummary);
        double elecComparison = getLastElectricityReading(comparisonAccountSummary) - getLastElectricityReading(accountSummary);
        return new AccountSummaryComparison(accountSummary.getAccountId(), accountSummary.getGasReadings(), accountSummary.getElecReadings(), gasComparison, elecComparison);
    }

    private double getLastGasReading(AccountSummary accountSummary){
        if (accountSummary == null) {
            return 0;
        }
        if (accountSummary.getGasReadings().size()==0){
            return 0;
        }
        return accountSummary.getGasReadings().get(accountSummary.getGasReadings().size()-1).getReading();
    }

    private double getLastElectricityReading(AccountSummary accountSummary){
        if (accountSummary == null) {
            return 0;
        }
        if (accountSummary.getElecReadings().size()==0){
            return 0;
        }
        return accountSummary.getElecReadings().get(accountSummary.getElecReadings().size()-1).getReading();
    }

    @PostMapping("/api/smart/reads")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAccountDetails(@RequestBody AccountSummary accountSummary)
    {
        dataService.save(accountSummary);
    }
}
