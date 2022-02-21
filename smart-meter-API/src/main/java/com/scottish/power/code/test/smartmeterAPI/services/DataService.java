package com.scottish.power.code.test.smartmeterAPI.services;


import java.util.logging.Logger;

public abstract class DataService<T,K> implements DataReader<T>, DataFinder<T,K>, DataSaver<T>{
}
