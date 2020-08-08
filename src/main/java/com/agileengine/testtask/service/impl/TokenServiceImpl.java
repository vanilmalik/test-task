package com.agileengine.testtask.service.impl;

import com.agileengine.testtask.dto.ImageDto;
import com.agileengine.testtask.dto.RestPageImpl;
import com.agileengine.testtask.dto.TokenDto;
import com.agileengine.testtask.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Objects;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final String PING = "http://interview.agileengine.com/images?page=1";
    private static final String AUTH = "http://interview.agileengine.com/auth";
    private static final String API_KEY = "apiKey";
    private static final String API_KEY_VALUE = "23567b218376f79d9415";

    private String currentToken = "";

    @Autowired
    private RestTemplate restTemplate;

    public String renewToken() {
        TokenDto tokenDto = restTemplate.postForEntity(
                AUTH,
                new HashMap<String, Object>() {{
                    put(API_KEY, API_KEY_VALUE);
                }},
                TokenDto.class).getBody();

        if (nonNull(tokenDto) && isNotEmpty(tokenDto.getToken())) {
            currentToken = tokenDto.getToken();
            return currentToken;
        } else {
            throw new RuntimeException("Invalid token");
        }
    }

    @Override
    public String getCurrentToken() {
        if (isValidToken()) {
            return currentToken;
        }

        return renewToken();
    }

    public Boolean isValidToken() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(AUTHORIZATION, BEARER + currentToken);

        try {
            HttpStatus httpStatus = restTemplate.exchange(
                    PING,
                    HttpMethod.GET,
                    new HttpEntity<>(requestHeaders),
                    new ParameterizedTypeReference<RestPageImpl<ImageDto>>() {
                    }
            ).getStatusCode();

            return !(Objects.equals(httpStatus, HttpStatus.UNAUTHORIZED));
        } catch (HttpClientErrorException e) {
            return false;
        }

    }
}
