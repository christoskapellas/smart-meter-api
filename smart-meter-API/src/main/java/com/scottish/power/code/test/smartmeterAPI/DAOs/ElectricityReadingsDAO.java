package com.scottish.power.code.test.smartmeterAPI.DAOs;

import com.scottish.power.code.test.smartmeterAPI.DTOs.ElectricityReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectricityReadingsDAO extends JpaRepository<ElectricityReading,Long>, DAO<ElectricityReading, Long>  {
}
