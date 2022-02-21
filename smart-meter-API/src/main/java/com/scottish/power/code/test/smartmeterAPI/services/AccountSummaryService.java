package com.scottish.power.code.test.smartmeterAPI.services;

import com.scottish.power.code.test.smartmeterAPI.DAOs.DAO;
import com.scottish.power.code.test.smartmeterAPI.DTOs.Account;
import com.scottish.power.code.test.smartmeterAPI.DTOs.ElectricityReading;
import com.scottish.power.code.test.smartmeterAPI.DTOs.GasReading;
import com.scottish.power.code.test.smartmeterAPI.mappers.Mapper;
import com.scottish.power.code.test.smartmeterAPI.model.AccountSummary;
import com.scottish.power.code.test.smartmeterAPI.model.ElectricityReadingSummaryEntity;
import com.scottish.power.code.test.smartmeterAPI.model.GasReadingSummaryEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountSummaryService extends DataService<AccountSummary,Long> {

    private final DAO<Account,Long> accountsDAO;
    private final DAO<GasReading,Long> gasReadingsDAO;
    private final DAO<ElectricityReading,Long> electricityReadingsDAO;
    private final Mapper<List<ElectricityReading>, List<ElectricityReadingSummaryEntity>> electricityReadingToSummaryEntityMapper;
    private final Mapper<List<GasReading>, List<GasReadingSummaryEntity>> gasReadingToSummaryEntityMapper;

    public AccountSummaryService(DAO<Account,Long> accountsDAO, DAO<GasReading,Long> gasReadingsDAO, DAO<ElectricityReading,Long> electricityReadingsDAO, Mapper<List<ElectricityReading>, List<ElectricityReadingSummaryEntity>> electricityReadingToSummaryEntityMapper, Mapper<List<GasReading>, List<GasReadingSummaryEntity>> gasReadingToSummaryEntityMapper) {
        this.accountsDAO = accountsDAO;
        this.gasReadingsDAO = gasReadingsDAO;
        this.electricityReadingsDAO = electricityReadingsDAO;
        this.electricityReadingToSummaryEntityMapper = electricityReadingToSummaryEntityMapper;
        this.gasReadingToSummaryEntityMapper = gasReadingToSummaryEntityMapper;
    }

    @Override
    public List<AccountSummary> readData() {
        List<Account> accounts = accountsDAO.findAll();
        List<GasReading> gasReadings = gasReadingsDAO.findAll();
        List<ElectricityReading> electricityReadings = electricityReadingsDAO.findAll();
        List<AccountSummary> accountSummaries = new ArrayList<AccountSummary>();
        for (Account account: accounts) {
            accountSummaries.add(buildAccountSummary(account, gasReadings, electricityReadings));
        }
        return accountSummaries;
    }

    @Override
    public AccountSummary find(Long id) {
        Optional<Account> account = accountsDAO.findById(id);
        if (account.isEmpty()) {
            return null;
        }
        return buildAccountSummary(account.get(), gasReadingsDAO.findAll(), electricityReadingsDAO.findAll());
    }

    private AccountSummary buildAccountSummary(Account account, List<GasReading> gasReadings, List<ElectricityReading> electricityReadings) {
        long accountId = account.getId();
        List<GasReading> filteredGasReadings = gasReadings.stream().filter(g-> g.getAccount() != null ? g.getAccount().getId() == accountId : false ).collect(Collectors.toList());
        List<GasReadingSummaryEntity> gasReadingSummaryEntities = gasReadingToSummaryEntityMapper.map(filteredGasReadings);
        List<ElectricityReading> filteredElectricityReadings = electricityReadings.stream().filter(e-> e.getAccount() != null ? e.getAccount().getId() == accountId : false).collect(Collectors.toList());
        List<ElectricityReadingSummaryEntity> electricityReadingSummaryEntities = electricityReadingToSummaryEntityMapper.map(filteredElectricityReadings);
        return new AccountSummary(accountId, gasReadingSummaryEntities,electricityReadingSummaryEntities);
    }

    @Override
    public boolean save(AccountSummary data) {
        try{
            Optional<Account> account = accountsDAO.findById(data.getAccountId());
            account = createAccountIfNotExist(data, account);
            createGasReadingsIfNotExist(data, account);
            createElectricityReadingsIfNotExist(data, account);
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    private Optional<Account> createAccountIfNotExist(AccountSummary data, Optional<Account> account) {
        if (account.isEmpty()) {
            accountsDAO.saveAndFlush(new Account(data.getAccountId()));
            account = accountsDAO.findById(data.getAccountId());
        }
        return account;
    }

    private void createGasReadingsIfNotExist(AccountSummary data, Optional<Account> account) {
        if (data.getGasReadings() != null) {
            List<GasReadingSummaryEntity> gasReadings = data.getGasReadings();
            for (GasReadingSummaryEntity gasReadingSummaryEntity : gasReadings) {
                GasReading gasReading = new GasReading();
                gasReading.setMeterID(gasReadingSummaryEntity.getMeterId());
                gasReading.setReading(gasReadingSummaryEntity.getReading());
                gasReading.setReadingDate(gasReadingSummaryEntity.getDate());
                gasReading.setAccount(account.get());
                if (!gasReadingsDAO.findAll().stream().anyMatch(g->g.getMeterID()==gasReading.getMeterID() && g.getReading()==gasReading.getReading() && g.getReadingDate().equals(gasReading.getReadingDate()))) {
                    gasReadingsDAO.saveAndFlush(gasReading);
                }
            }
        }
    }

    private void createElectricityReadingsIfNotExist(AccountSummary data, Optional<Account> account) {
        if (data.getElecReadings() != null){
            List<ElectricityReadingSummaryEntity> electricityReadings = data.getElecReadings();
            for (ElectricityReadingSummaryEntity electricityReadingSummaryEntity : electricityReadings) {
                ElectricityReading electricityReading = new ElectricityReading();
                electricityReading.setMeterID(electricityReadingSummaryEntity.getMeterId());
                electricityReading.setReading(electricityReadingSummaryEntity.getReading());
                electricityReading.setReadingDate(electricityReadingSummaryEntity.getDate());
                electricityReading.setAccount(account.get());
                if (!electricityReadingsDAO.findAll().stream().anyMatch(e->e.getMeterID()==electricityReading.getMeterID() && e.getReading()==electricityReading.getReading() && e.getReadingDate().equals(electricityReading.getReadingDate()))) {
                    electricityReadingsDAO.saveAndFlush(electricityReading);
                }
            }
        }
    }
}
