package com.scottish.power.code.test.smartmeterAPI.DAOs;

import com.scottish.power.code.test.smartmeterAPI.DTOs.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsDAO extends JpaRepository<Account, Long>, DAO<Account, Long> {
}
