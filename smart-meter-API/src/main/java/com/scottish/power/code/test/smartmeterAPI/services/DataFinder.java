package com.scottish.power.code.test.smartmeterAPI.services;

public interface DataFinder<T,K>{
    T find(K id);
}
