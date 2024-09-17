package com.heb.images.adapter;

import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
public class GcpVisionAdapter {

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private CloudVisionTemplate cloudVisionTemplate;

    public List<String> analyzeImage(String imageUri) {
        AnnotateImageResponse response =
                this.cloudVisionTemplate.analyzeImage(
                        this.resourceLoader.getResource(imageUri),
                        Feature.Type.LABEL_DETECTION
                );
        return getLabelAnnotationsList(response);
    }


    private List<String> getLabelAnnotationsList(AnnotateImageResponse response) {
        return response.getLabelAnnotationsList().stream()
                .map(EntityAnnotation::getDescription)
                .collect(Collectors.toList());
    }
}
