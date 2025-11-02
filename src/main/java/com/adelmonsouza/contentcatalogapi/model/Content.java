package com.adelmonsouza.contentcatalogapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "content", indexes = {
    @Index(name = "idx_content_type", columnList = "contentType"),
    @Index(name = "idx_content_genre", columnList = "genre"),
    @Index(name = "idx_content_rating", columnList = "rating")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String title;
    
    @NotBlank
    @Size(max = 1000)
    @Column(length = 1000)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentType contentType;
    
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String genre;
    
    @Min(1900)
    @Max(2100)
    @Column(nullable = false)
    private Integer releaseYear;
    
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    @Column(precision = 3, scale = 1)
    private Double rating;
    
    @Min(0)
    private Integer durationMinutes; // Para filmes e séries
    
    @Min(0)
    private Integer totalEpisodes; // Para séries
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

