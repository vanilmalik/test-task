package com.agileengine.testtask.service;

import com.agileengine.testtask.dto.ImageDto;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface ImageService {

    Page<ImageDto> doReadAll(Map<String, Object> searchRequest);

    ImageDto doRead(String id);
}
