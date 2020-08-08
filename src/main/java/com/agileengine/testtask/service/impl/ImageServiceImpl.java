package com.agileengine.testtask.service.impl;

import com.agileengine.testtask.dto.ImageDto;
import com.agileengine.testtask.dto.RestPageImpl;
import com.agileengine.testtask.service.ImageService;
import com.agileengine.testtask.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Map;

import static java.util.Objects.nonNull;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TokenService tokenService;

    @Override
    public Page<ImageDto> doReadAll(Map<String, Object> searchRequest) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.set("Authorization", "Bearer " + tokenService.getCurrentToken());

        HttpEntity requestEntity = new HttpEntity<>(requestHeaders);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://interview.agileengine.com" + "/images");

        if (nonNull(searchRequest)) {
            searchRequest.forEach(builder::queryParam);
        }

        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<RestPageImpl<ImageDto>>() {
                }
        ).getBody();
    }

    @Override
    public ImageDto doRead(String id) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.set("Authorization", "Bearer " + tokenService.getCurrentToken());

        HttpEntity requestEntity = new HttpEntity<>(requestHeaders);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://interview.agileengine.com" + "/images" + "/" + id);

        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<ImageDto>() {
                }
        ).getBody();
    }
}
