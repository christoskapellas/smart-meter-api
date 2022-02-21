package com.scottish.power.code.test.smartmeterAPI.services;

public interface DataSaver<T> {
    boolean save(T data);
}
