package com.heb.images.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;
import java.util.Map;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnore
    private String imageBase64;
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> metadata;
    private List<String> tags;
    private String label;
    private String uri;

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
