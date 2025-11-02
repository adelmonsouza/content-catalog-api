package com.adelmonsouza.contentcatalogapi.service;

import com.adelmonsouza.contentcatalogapi.dto.ContentCreateDTO;
import com.adelmonsouza.contentcatalogapi.dto.ContentResponseDTO;
import com.adelmonsouza.contentcatalogapi.exception.ContentNotFoundException;
import com.adelmonsouza.contentcatalogapi.model.Content;
import com.adelmonsouza.contentcatalogapi.model.ContentType;
import com.adelmonsouza.contentcatalogapi.repository.ContentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {
    
    @Mock
    private ContentRepository contentRepository;
    
    @InjectMocks
    private ContentService contentService;
    
    private Content content;
    private ContentCreateDTO createDTO;
    
    @BeforeEach
    void setUp() {
        content = Content.builder()
            .id(1L)
            .title("The Matrix")
            .description("A hacker learns about the true nature of reality")
            .contentType(ContentType.MOVIE)
            .genre("Sci-Fi")
            .releaseYear(1999)
            .rating(8.7)
            .durationMinutes(136)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        
        createDTO = ContentCreateDTO.builder()
            .title("The Matrix")
            .description("A hacker learns about the true nature of reality")
            .contentType(ContentType.MOVIE)
            .genre("Sci-Fi")
            .releaseYear(1999)
            .rating(8.7)
            .durationMinutes(136)
            .build();
    }
    
    @Test
    void createContent_ShouldReturnContentResponseDTO() {
        // Given
        when(contentRepository.save(any(Content.class))).thenReturn(content);
        
        // When
        ContentResponseDTO result = contentService.createContent(createDTO);
        
        // Then
        assertNotNull(result);
        assertEquals("The Matrix", result.title());
        assertEquals(ContentType.MOVIE, result.contentType());
        verify(contentRepository, times(1)).save(any(Content.class));
    }
    
    @Test
    void getAllContent_ShouldReturnPageOfContentResponseDTO() {
        // Given
        Pageable pageable = PageRequest.of(0, 20);
        Page<Content> contentPage = new PageImpl<>(List.of(content), pageable, 1);
        when(contentRepository.findAll(pageable)).thenReturn(contentPage);
        
        // When
        Page<ContentResponseDTO> result = contentService.getAllContent(pageable);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("The Matrix", result.getContent().get(0).title());
        verify(contentRepository, times(1)).findAll(pageable);
    }
    
    @Test
    void getContentById_WhenContentExists_ShouldReturnContentResponseDTO() {
        // Given
        when(contentRepository.findById(1L)).thenReturn(Optional.of(content));
        
        // When
        ContentResponseDTO result = contentService.getContentById(1L);
        
        // Then
        assertNotNull(result);
        assertEquals("The Matrix", result.title());
        verify(contentRepository, times(1)).findById(1L);
    }
    
    @Test
    void getContentById_WhenContentNotFound_ShouldThrowException() {
        // Given
        when(contentRepository.findById(999L)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(ContentNotFoundException.class, () -> 
            contentService.getContentById(999L)
        );
        verify(contentRepository, times(1)).findById(999L);
    }
    
    @Test
    void updateContent_WhenContentExists_ShouldReturnUpdatedContent() {
        // Given
        ContentCreateDTO updateDTO = ContentCreateDTO.builder()
            .title("The Matrix Reloaded")
            .description("Updated description")
            .contentType(ContentType.MOVIE)
            .genre("Sci-Fi")
            .releaseYear(2003)
            .rating(7.2)
            .durationMinutes(138)
            .build();
        
        when(contentRepository.findById(1L)).thenReturn(Optional.of(content));
        when(contentRepository.save(any(Content.class))).thenReturn(content);
        
        // When
        ContentResponseDTO result = contentService.updateContent(1L, updateDTO);
        
        // Then
        assertNotNull(result);
        verify(contentRepository, times(1)).findById(1L);
        verify(contentRepository, times(1)).save(any(Content.class));
    }
    
    @Test
    void updateContent_WhenContentNotFound_ShouldThrowException() {
        // Given
        when(contentRepository.findById(999L)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(ContentNotFoundException.class, () -> 
            contentService.updateContent(999L, createDTO)
        );
        verify(contentRepository, never()).save(any());
    }
    
    @Test
    void deleteContent_WhenContentExists_ShouldDeleteSuccessfully() {
        // Given
        when(contentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(contentRepository).deleteById(1L);
        
        // When
        contentService.deleteContent(1L);
        
        // Then
        verify(contentRepository, times(1)).existsById(1L);
        verify(contentRepository, times(1)).deleteById(1L);
    }
    
    @Test
    void deleteContent_WhenContentNotFound_ShouldThrowException() {
        // Given
        when(contentRepository.existsById(999L)).thenReturn(false);
        
        // When & Then
        assertThrows(ContentNotFoundException.class, () -> 
            contentService.deleteContent(999L)
        );
        verify(contentRepository, never()).deleteById(any());
    }
}

