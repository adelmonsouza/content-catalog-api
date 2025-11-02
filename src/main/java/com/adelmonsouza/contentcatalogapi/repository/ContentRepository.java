package com.adelmonsouza.contentcatalogapi.repository;

import com.adelmonsouza.contentcatalogapi.model.Content;
import com.adelmonsouza.contentcatalogapi.model.ContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    
    Optional<Content> findByTitle(String title);
    
    Page<Content> findByContentType(ContentType contentType, Pageable pageable);
    
    Page<Content> findByGenre(String genre, Pageable pageable);
    
    Page<Content> findByReleaseYearBetween(Integer minYear, Integer maxYear, Pageable pageable);
    
    Page<Content> findByRatingGreaterThanEqual(Double minRating, Pageable pageable);
    
    @Query("SELECT c FROM Content c WHERE " +
           "(:title IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:contentType IS NULL OR c.contentType = :contentType) AND " +
           "(:genre IS NULL OR c.genre = :genre) AND " +
           "(:minYear IS NULL OR c.releaseYear >= :minYear) AND " +
           "(:maxYear IS NULL OR c.releaseYear <= :maxYear) AND " +
           "(:minRating IS NULL OR c.rating >= :minRating)")
    Page<Content> searchContent(
        @Param("title") String title,
        @Param("contentType") ContentType contentType,
        @Param("genre") String genre,
        @Param("minYear") Integer minYear,
        @Param("maxYear") Integer maxYear,
        @Param("minRating") Double minRating,
        Pageable pageable
    );
}

