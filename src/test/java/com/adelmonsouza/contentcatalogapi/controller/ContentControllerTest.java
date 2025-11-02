package com.adelmonsouza.contentcatalogapi.controller;

import com.adelmonsouza.contentcatalogapi.dto.ContentCreateDTO;
import com.adelmonsouza.contentcatalogapi.dto.ContentResponseDTO;
import com.adelmonsouza.contentcatalogapi.model.ContentType;
import com.adelmonsouza.contentcatalogapi.service.ContentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContentController.class)
class ContentControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ContentService contentService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private ContentResponseDTO createMockResponse() {
        return ContentResponseDTO.builder()
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
    }
    
    @Test
    void createContent_ShouldReturnCreated() throws Exception {
        // Given
        ContentCreateDTO createDTO = ContentCreateDTO.builder()
            .title("The Matrix")
            .description("A hacker learns about the true nature of reality")
            .contentType(ContentType.MOVIE)
            .genre("Sci-Fi")
            .releaseYear(1999)
            .rating(8.7)
            .durationMinutes(136)
            .build();
        
        ContentResponseDTO response = createMockResponse();
        when(contentService.createContent(any(ContentCreateDTO.class))).thenReturn(response);
        
        // When & Then
        mockMvc.perform(post("/api/content")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("The Matrix"));
    }
    
    @Test
    void getAllContent_ShouldReturnPage() throws Exception {
        // Given
        Page<ContentResponseDTO> page = new PageImpl<>(
            List.of(createMockResponse()),
            PageRequest.of(0, 20),
            1
        );
        when(contentService.getAllContent(any())).thenReturn(page);
        
        // When & Then
        mockMvc.perform(get("/api/content"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(1));
    }
    
    @Test
    void getContentById_ShouldReturnContent() throws Exception {
        // Given
        ContentResponseDTO response = createMockResponse();
        when(contentService.getContentById(1L)).thenReturn(response);
        
        // When & Then
        mockMvc.perform(get("/api/content/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("The Matrix"));
    }
    
    @Test
    void updateContent_ShouldReturnUpdatedContent() throws Exception {
        // Given
        ContentCreateDTO updateDTO = ContentCreateDTO.builder()
            .title("The Matrix Reloaded")
            .description("Updated")
            .contentType(ContentType.MOVIE)
            .genre("Sci-Fi")
            .releaseYear(2003)
            .rating(7.2)
            .durationMinutes(138)
            .build();
        
        ContentResponseDTO response = createMockResponse();
        when(contentService.updateContent(any(Long.class), any(ContentCreateDTO.class)))
            .thenReturn(response);
        
        // When & Then
        mockMvc.perform(put("/api/content/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
    
    @Test
    void deleteContent_ShouldReturnNoContent() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/content/1"))
                .andExpect(status().isNoContent());
    }
}

