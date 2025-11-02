package com.adelmonsouza.contentcatalogapi.dto;

import com.adelmonsouza.contentcatalogapi.model.ContentType;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record ContentCreateDTO(
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be at most 255 characters")
    String title,
    
    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description must be at most 1000 characters")
    String description,
    
    @NotNull(message = "Content type is required")
    ContentType contentType,
    
    @NotBlank(message = "Genre is required")
    @Size(max = 100, message = "Genre must be at most 100 characters")
    String genre,
    
    @NotNull(message = "Release year is required")
    @Min(value = 1900, message = "Release year must be at least 1900")
    @Max(value = 2100, message = "Release year must be at most 2100")
    Integer releaseYear,
    
    @DecimalMin(value = "0.0", message = "Rating must be at least 0.0")
    @DecimalMax(value = "10.0", message = "Rating must be at most 10.0")
    Double rating,
    
    @Min(value = 0, message = "Duration must be positive")
    Integer durationMinutes,
    
    @Min(value = 0, message = "Total episodes must be positive")
    Integer totalEpisodes
) {}

