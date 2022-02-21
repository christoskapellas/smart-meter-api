package com.scottish.power.code.test.smartmeterAPI.DAOs;

import com.scottish.power.code.test.smartmeterAPI.DTOs.GasReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasReadingsDAO extends JpaRepository<GasReading,Long>, DAO<GasReading, Long>  {
}
