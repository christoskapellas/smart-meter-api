package com.scottish.power.code.test.smartmeterAPI.DAOs;

import java.util.List;
import java.util.Optional;

public interface DAO<T,K>{
    List<T> findAll();
    Optional<T> findById(K id);
    T saveAndFlush(T data);
}
