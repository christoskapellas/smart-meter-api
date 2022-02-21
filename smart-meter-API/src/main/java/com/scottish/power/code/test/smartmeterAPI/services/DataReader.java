package com.scottish.power.code.test.smartmeterAPI.services;

import java.util.List;

public interface DataReader<T>{
    List<T> readData();
}
