package com.scottish.power.code.test.smartmeterAPI.integration.DAOs;

import com.scottish.power.code.test.smartmeterAPI.DAOs.DAO;
import com.scottish.power.code.test.smartmeterAPI.DTOs.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class AccountsDAOTest {

    @Autowired
    private DAO<Account,Long> accountsDAO;

    @Test
    public void testThatAccountsCanBeRetrieved(){
        List<Account> accounts = accountsDAO.findAll();
        Assertions.assertEquals(3, accounts.size());
    }

    @Test
    public void testThatAnAccountCanBeRetrievedUsingId(){
        long id = 1;
        Optional<Account> account = accountsDAO.findById(id);
        Assertions.assertNotNull(account.get());
        Assertions.assertEquals(id, account.get().getId());
    }

    @Test
    public void testThatAnAccountCanBeStored(){
        long id = 4;
        Account account = new Account();
        account.setId(id);
        accountsDAO.saveAndFlush(account);
        Optional<Account> retrievedAccount = accountsDAO.findById(id);
        Assertions.assertEquals(4, accountsDAO.findAll().size());
        Assertions.assertNotNull(retrievedAccount);
        Assertions.assertEquals(id, retrievedAccount.get().getId());
    }
}