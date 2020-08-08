package com.agileengine.testtask.controller;

import com.agileengine.testtask.dto.ImageDto;
import com.agileengine.testtask.dto.PageableDto;
import com.agileengine.testtask.facade.ImageFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/images")
public class ImageController {

    @Autowired
    private ImageFacade imageFacade;

    @RequestMapping(method = RequestMethod.GET)
    public Page<ImageDto> readAll(PageableDto pageableDto) {
        return imageFacade.readAll(pageableDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search/{term}")
    public Set<ImageDto> readAll(@PathVariable("term") String term) {
        return imageFacade.findByTerm(term);
    }
}
