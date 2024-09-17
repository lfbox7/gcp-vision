package com.heb.images.adapter;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.google.api.client.util.Lists;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ImageMetadataAdapter {

    public Map<String, String> extractMetadata(String imageBase64) {
        InputStream imageStream;
        imageStream = new ByteArrayInputStream(Base64.getDecoder().decode(imageBase64));
        return getMetadata(imageStream);
    }

    private Map<String, String> getMetadata(InputStream imageStream) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imageStream);
            return getMetadataDirectories(metadata);
        } catch (ImageProcessingException e) {
            log.warn("Image metadata could not be processed");
        } catch (IOException e) {
            throw new RuntimeException("Image metadata not found", e);
        }
        return new HashMap<>();
    }

    private Map<String, String> getMetadataDirectories(Metadata metadata) {
        Iterable<Directory> directories = metadata.getDirectories();
        return convertMetadataDirectoriesToList(directories);
    }

    private Map<String, String> convertMetadataDirectoriesToList(Iterable<Directory> directories) {
        List<Directory> directoriesList = Lists.newArrayList(directories);
        return convertMetadataListToMap(directoriesList);
    }
    private Map<String, String> convertMetadataListToMap(List<Directory> directoriesList) {
        List<Map<String, String>> directoriesMap  = directoriesList.stream()
                .map(directory -> getTagDescriptions(directory))
                .collect(Collectors.toList());
        return flattenDirectoriesMap(directoriesMap);
    }

    private Map<String, String> getTagDescriptions(Directory directory) {
        Collection<Tag> tags = directory.getTags();
        return tags.stream()
                .collect(Collectors.toMap(
                        tag -> String.format("[%s] %s", directory.getName(), tag.getTagName()),
                        tag -> tag.getDescription()
                ));
    }

    private Map<String, String> flattenDirectoriesMap(List<Map<String, String>> directoriesMap) {
        return directoriesMap.stream().flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
