package com.heb.images.controller;

import com.google.common.collect.ImmutableList;
import com.heb.images.manager.ImageManager;
import com.heb.images.model.Image;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImageControllerTest {

    @MockBean
    private ImageManager imageManager;
    @Mock
    private ImageController toTest;

    private Image IMAGE_1 = new Image();

    @BeforeAll
    void setUp() {
        toTest = new ImageController(imageManager);
    }

    @Test
    void getImagesIsSuccessful() {
        List<Image> expected = ImmutableList.of(IMAGE_1);
        when(imageManager.getImages(anyString()))
                .thenReturn(ImmutableList.of(IMAGE_1));
        List<Image> result = toTest.getImages("dog");
        assertEquals(expected, result);
    }

    @Test
    void findByIdIsSuccessful() {
        Optional<Image> expected = Optional.of(IMAGE_1);
        when(imageManager.findById(anyLong()))
                .thenReturn(Optional.of(IMAGE_1));
        Optional<Image> result = toTest.findById(1234L);
        assertEquals(expected, result);
    }

    @Test
    void create() {
        ImageController.File file = new ImageController.File();
        when(imageManager.create(IMAGE_1))
                .thenReturn(IMAGE_1);
        Image result = toTest.create(file);
        assertEquals(IMAGE_1, result);
    }
}