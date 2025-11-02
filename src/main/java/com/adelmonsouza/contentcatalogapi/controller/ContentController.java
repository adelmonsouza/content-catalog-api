package com.adelmonsouza.contentcatalogapi.controller;

import com.adelmonsouza.contentcatalogapi.dto.ContentCreateDTO;
import com.adelmonsouza.contentcatalogapi.dto.ContentResponseDTO;
import com.adelmonsouza.contentcatalogapi.dto.SearchRequestDTO;
import com.adelmonsouza.contentcatalogapi.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
@Tag(name = "Content Catalog API", description = "API para gerenciamento de catálogo de conteúdo")
public class ContentController {
    
    private final ContentService contentService;
    
    @PostMapping
    @Operation(summary = "Criar novo conteúdo", description = "Cria um novo item no catálogo")
    public ResponseEntity<ContentResponseDTO> createContent(@Valid @RequestBody ContentCreateDTO dto) {
        ContentResponseDTO created = contentService.createContent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @GetMapping
    @Operation(summary = "Listar conteúdo", description = "Lista todo o conteúdo com paginação")
    public ResponseEntity<Page<ContentResponseDTO>> getAllContent(
        @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<ContentResponseDTO> content = contentService.getAllContent(pageable);
        return ResponseEntity.ok(content);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar conteúdo por ID", description = "Retorna um conteúdo específico")
    public ResponseEntity<ContentResponseDTO> getContentById(@PathVariable Long id) {
        ContentResponseDTO content = contentService.getContentById(id);
        return ResponseEntity.ok(content);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar conteúdo", description = "Atualiza um conteúdo existente")
    public ResponseEntity<ContentResponseDTO> updateContent(
        @PathVariable Long id,
        @Valid @RequestBody ContentCreateDTO dto
    ) {
        ContentResponseDTO updated = contentService.updateContent(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar conteúdo", description = "Remove um conteúdo do catálogo")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/search")
    @Operation(summary = "Buscar conteúdo", description = "Busca conteúdo com filtros avançados e paginação")
    public ResponseEntity<Page<ContentResponseDTO>> searchContent(
        @RequestBody SearchRequestDTO searchRequest,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<ContentResponseDTO> results = contentService.searchContent(searchRequest, pageable);
        return ResponseEntity.ok(results);
    }
}

