package com.agileengine.testtask.service;

public interface TokenService {

    String renewToken();

    String getCurrentToken();

    Boolean isValidToken();
}
