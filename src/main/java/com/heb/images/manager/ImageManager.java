package com.heb.images.manager;

import com.google.common.base.Strings;

import com.heb.images.adapter.GcpVisionAdapter;
import com.heb.images.adapter.ImageMetadataAdapter;
import com.heb.images.dao.ImageRepository;
import com.heb.images.model.Image;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class ImageManager {

    @Autowired
    private GcpVisionAdapter gcpVisionAdapter;
    @Autowired
    private ImageMetadataAdapter imageMetadataAdapter;
    @Autowired
    private ImageRepository imageRepository;

    public List<Image> getImages(String objects) {
        List<Image> images;
        if (Strings.isNullOrEmpty(objects)) {
            images = imageRepository.findAll();
        } else {
            List<String> tags = Arrays.asList(objects.split(",", -1))
                    .stream()
                    .map(str -> str.trim().toLowerCase())
                    .collect(Collectors.toList());
            images = imageRepository.findByTags(tags);
        }
        return images;
    }

    public Optional<Image> findById(Long imageId) {
        return imageRepository.findById(imageId);
    }

    public Image create(Image image) {
        image.setMetadata(imageMetadataAdapter.extractMetadata(image.getImageBase64()));
        image.setTags(gcpVisionAdapter.analyzeImage(image.getUri()));
        Image savedImage = imageRepository.save(image);
        return savedImage;
    }
}
