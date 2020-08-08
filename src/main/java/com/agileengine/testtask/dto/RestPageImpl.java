package com.agileengine.testtask.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestPageImpl<T> extends PageImpl<T> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RestPageImpl(@JsonProperty("pictures") List<T> content,
                        @JsonProperty("page") int number,
                        @JsonProperty("pageCount") int totalPages,
                        @JsonProperty("hasMore") boolean hasMore) {

        super(content, PageRequest.of(number, 10), totalPages);
    }

}
