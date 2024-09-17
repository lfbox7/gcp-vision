package com.heb.images.controller;

import com.heb.images.manager.ImageManager;
import com.heb.images.model.Image;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ImageController {

    @Autowired
    private ImageManager imageManager;

    @GetMapping("/images")
    public List<Image> getImages(@Param("objects") String objects) {
        return imageManager.getImages(objects);
    }

    @GetMapping("/images/{imageId}")
    public Optional<Image> findById(@PathVariable Long imageId) {
        return imageManager.findById(imageId);
    }

    @PostMapping("/images")
    public Image create(@RequestParam("file") File file) {
        return imageManager.create(file.toImage());
    }

    public static class File {
        private String imageBase64;
        private String label;
        private String uri;

        public Image toImage() {
            Image image = new Image();
            image.setImageBase64(imageBase64);
            image.setLabel(label);
            image.setUri(uri);
            return image;
        }
    }
}
