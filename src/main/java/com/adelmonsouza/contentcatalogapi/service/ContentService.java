package com.adelmonsouza.contentcatalogapi.service;

import com.adelmonsouza.contentcatalogapi.dto.ContentCreateDTO;
import com.adelmonsouza.contentcatalogapi.dto.ContentResponseDTO;
import com.adelmonsouza.contentcatalogapi.dto.SearchRequestDTO;
import com.adelmonsouza.contentcatalogapi.exception.ContentNotFoundException;
import com.adelmonsouza.contentcatalogapi.model.Content;
import com.adelmonsouza.contentcatalogapi.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentService {
    
    private final ContentRepository contentRepository;
    
    @Transactional
    public ContentResponseDTO createContent(ContentCreateDTO dto) {
        Content content = Content.builder()
            .title(dto.title())
            .description(dto.description())
            .contentType(dto.contentType())
            .genre(dto.genre())
            .releaseYear(dto.releaseYear())
            .rating(dto.rating())
            .durationMinutes(dto.durationMinutes())
            .totalEpisodes(dto.totalEpisodes())
            .build();
        
        Content saved = contentRepository.save(content);
        return mapToResponseDTO(saved);
    }
    
    public Page<ContentResponseDTO> getAllContent(Pageable pageable) {
        return contentRepository.findAll(pageable)
            .map(this::mapToResponseDTO);
    }
    
    public ContentResponseDTO getContentById(Long id) {
        Content content = contentRepository.findById(id)
            .orElseThrow(() -> new ContentNotFoundException("Content not found with id: " + id));
        return mapToResponseDTO(content);
    }
    
    @Transactional
    public ContentResponseDTO updateContent(Long id, ContentCreateDTO dto) {
        Content content = contentRepository.findById(id)
            .orElseThrow(() -> new ContentNotFoundException("Content not found with id: " + id));
        
        content.setTitle(dto.title());
        content.setDescription(dto.description());
        content.setContentType(dto.contentType());
        content.setGenre(dto.genre());
        content.setReleaseYear(dto.releaseYear());
        content.setRating(dto.rating());
        content.setDurationMinutes(dto.durationMinutes());
        content.setTotalEpisodes(dto.totalEpisodes());
        
        Content updated = contentRepository.save(content);
        return mapToResponseDTO(updated);
    }
    
    @Transactional
    public void deleteContent(Long id) {
        if (!contentRepository.existsById(id)) {
            throw new ContentNotFoundException("Content not found with id: " + id);
        }
        contentRepository.deleteById(id);
    }
    
    public Page<ContentResponseDTO> searchContent(SearchRequestDTO searchRequest, Pageable pageable) {
        Page<Content> contentPage = contentRepository.searchContent(
            searchRequest.title(),
            searchRequest.contentType(),
            searchRequest.genre(),
            searchRequest.minYear(),
            searchRequest.maxYear(),
            searchRequest.minRating(),
            pageable
        );
        
        return contentPage.map(this::mapToResponseDTO);
    }
    
    private ContentResponseDTO mapToResponseDTO(Content content) {
        return ContentResponseDTO.builder()
            .id(content.getId())
            .title(content.getTitle())
            .description(content.getDescription())
            .contentType(content.getContentType())
            .genre(content.getGenre())
            .releaseYear(content.getReleaseYear())
            .rating(content.getRating())
            .durationMinutes(content.getDurationMinutes())
            .totalEpisodes(content.getTotalEpisodes())
            .createdAt(content.getCreatedAt())
            .updatedAt(content.getUpdatedAt())
            .build();
    }
}

