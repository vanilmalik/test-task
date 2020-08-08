package com.agileengine.testtask.facade;

import com.agileengine.testtask.dto.ImageDto;
import com.agileengine.testtask.dto.PageableDto;
import com.agileengine.testtask.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Component
public class ImageFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageFacade.class);

    @Autowired
    private ImageService imageService;

    @Cacheable(cacheNames = "pictures")
    public Page<ImageDto> readAll(PageableDto pageableDto) {
        Page<ImageDto> imageDtos = imageService.doReadAll(
                Collections.unmodifiableMap(new HashMap<String, Object>() {{
                    put("page", pageableDto.getPage());
                }})
        );

        imageDtos.forEach(imageDto -> {
            ImageDto fullImageInfo = imageService.doRead(imageDto.getId());
            imageDto.setAuthor(fullImageInfo.getAuthor());
            imageDto.setCamera(fullImageInfo.getCamera());
            imageDto.setFullPicture(fullImageInfo.getFullPicture());
            imageDto.setTags(fullImageInfo.getTags());
        });

        return imageDtos;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Cacheable(cacheNames = "pictures")
    public Set<ImageDto> fetchAll() {
        Set<ImageDto> images = new HashSet<>();
        int page = 1;
        Page<ImageDto> imageDtos;

        do {
            imageDtos = readAll(new PageableDto(page));
            images.addAll(imageDtos.getContent());
            page++;
        } while (imageDtos.hasContent());

        LOGGER.debug("Retrieved all images {}", images);
        return images;
    }

    @Cacheable(cacheNames = "pictures")
    public Set<ImageDto> findByTerm(String term) {
        Set<ImageDto> images = fetchAll();
        Set<ImageDto> result = new HashSet<>();

        String finalTerm = term.toLowerCase();

        images.stream().parallel().forEach(imageDto -> {
            if ( (isNotEmpty(imageDto.getAuthor()) && imageDto.getAuthor().toLowerCase().contains(finalTerm))
            || (isNotEmpty(imageDto.getFullPicture())  && imageDto.getFullPicture().toLowerCase().contains(finalTerm))
                    || (isNotEmpty(imageDto.getCamera()) && imageDto.getCamera().toLowerCase().contains(finalTerm))
                    || (isNotEmpty(imageDto.getCroppedPicture()) && imageDto.getCroppedPicture().toLowerCase().contains(finalTerm))
                    || containsHashTag(imageDto, finalTerm)) {
                result.add(imageDto);
            }
        });

        return result;
    }

    private boolean containsHashTag(ImageDto imageDto, String term) {
        if (isNotEmpty(imageDto.getTags())) {
            String[] hashTags = imageDto.getTags().split("#");
            return Arrays.stream(hashTags).anyMatch(hashTag -> hashTag.toLowerCase().contains(term));
        }

        return false;
    }
}
