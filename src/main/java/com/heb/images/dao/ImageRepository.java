package com.heb.images.dao;

import com.heb.images.model.Image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByTags(List<String> tags);
}
