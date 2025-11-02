package com.adelmonsouza.contentcatalogapi.dto;

import com.adelmonsouza.contentcatalogapi.model.ContentType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ContentResponseDTO(
    Long id,
    String title,
    String description,
    ContentType contentType,
    String genre,
    Integer releaseYear,
    Double rating,
    Integer durationMinutes,
    Integer totalEpisodes,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}

