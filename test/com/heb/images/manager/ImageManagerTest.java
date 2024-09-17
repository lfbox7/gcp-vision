package com.heb.images.manager;

import com.google.common.collect.ImmutableList;
import com.heb.images.adapter.GcpVisionAdapter;
import com.heb.images.adapter.ImageMetadataAdapter;
import com.heb.images.controller.ImageController;
import com.heb.images.dao.ImageRepository;
import com.heb.images.model.Image;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImageManagerTest {

    @MockBean
    private GcpVisionAdapter gcpVisionAdapter;
    @MockBean
    private ImageMetadataAdapter imageMetadataAdapter;
    @Mock
    private ImageRepository imageRepository;

    private ImageManager toTest;

    private Image IMAGE_1 = new Image();

    @BeforeAll
    void setUp(){
        toTest = new ImageManager(gcpVisionAdapter, imageMetadataAdapter, imageRepository);
    }

    @Test
    void getImagesReturnsAllWhenStringEmpty() {
        List<Image> expected = ImmutableList.of(IMAGE_1);
        when(imageRepository.findAll())
                .thenReturn(ImmutableList.of(IMAGE_1));
        List<Image> result = toTest.getImages("");
        assertEquals(expected, result);
    }

    @Test
    void getImagesSuccessful() {
        List<Image> expected = ImmutableList.of(IMAGE_1);
        when(imageRepository.findByTags(any(List.class)))
                .thenReturn(ImmutableList.of(IMAGE_1));
        List<Image> result = toTest.getImages("");
        assertEquals(expected, result);
    }

    @Test
    void findById() {
        Optional<Image> expected = Optional.of(IMAGE_1);
        when(imageRepository.findById(anyLong()))
                .thenReturn(Optional.of(IMAGE_1));
        Optional<Image> result = toTest.findById(1234L);
        assertEquals(expected, result);
    }

    @Test
    void create() {
        Image expected = IMAGE_1;
        Image result = toTest.create(IMAGE_1);
        assertEquals(IMAGE_1, result);
    }
}