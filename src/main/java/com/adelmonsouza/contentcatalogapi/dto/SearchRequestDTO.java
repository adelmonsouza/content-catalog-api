package com.adelmonsouza.contentcatalogapi.dto;

import com.adelmonsouza.contentcatalogapi.model.ContentType;
import lombok.Builder;

@Builder
public record SearchRequestDTO(
    String title,
    ContentType contentType,
    String genre,
    Integer minYear,
    Integer maxYear,
    Double minRating
) {}

