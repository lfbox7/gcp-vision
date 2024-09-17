package com.heb.images;

import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.heb.images.adapter.GcpVisionAdapter;
import com.heb.images.adapter.ImageMetadataAdapter;
import com.heb.images.dao.ImageRepository;
import com.heb.images.manager.ImageManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
@SpringBootApplication
public class ImagesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImagesApplication.class, args);
	}

	@Bean
	public GcpVisionAdapter getGcpVisionAdapter(
			ResourceLoader resourceLoader,
			CloudVisionTemplate cloudVisionTemplate
	) {
		return new GcpVisionAdapter(resourceLoader, cloudVisionTemplate);
	}

	@Bean
	ImageMetadataAdapter getImageMetadataAdapter() {
		return new ImageMetadataAdapter();
	}

	@Bean
	public ImageManager getImageManager(
			GcpVisionAdapter gcpVisionAdapter,
			ImageMetadataAdapter imageMetadataAdapter,
			ImageRepository imageRepository
	) {
		return new ImageManager(gcpVisionAdapter, imageMetadataAdapter, imageRepository);
	}

}
