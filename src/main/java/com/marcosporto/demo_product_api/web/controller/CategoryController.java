package com.marcosporto.demo_product_api.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcosporto.demo_product_api.entity.Category;
import com.marcosporto.demo_product_api.service.CategoryService;
import com.marcosporto.demo_product_api.web.dto.CategoryCreateDto;
import com.marcosporto.demo_product_api.web.dto.CategoryResponseDto;
import com.marcosporto.demo_product_api.web.dto.CategoryUpdateDto;
import com.marcosporto.demo_product_api.web.dto.mapper.CategoryMapper;
import com.marcosporto.demo_product_api.web.exception.ErrorMessage;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Categorias", description = "Contém todas as operações relativas aos recurso para cadastro, edição, exclusão e leitura de categorias.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Criar uma nova categoria.", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN", responses = {
            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryCreateDto.class))),
            @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponseDto> create(@Valid @RequestBody CategoryCreateDto categoryCreateDto) {
        Category newCategory = categoryService.save(CategoryMapper.toCategory(categoryCreateDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryMapper.toDto(newCategory));
    }

    @Operation(summary = "Listar todos as categorias.", description = "Requisição exige um Bearer Token.", responses = {
            @ApiResponse(responseCode = "200", description = "Recurso listado com sucesso.", content = @Content(mediaType = "application/json")),

    })
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        List<Category> categories = categoryService.findAll();

        return ResponseEntity.ok(CategoryMapper.toListDto(categories));
    }

    @Operation(summary = "Buscar uma categoria.", description = "Requisição exige um Bearer Token.", responses = {
            @ApiResponse(responseCode = "200", description = "Recurso buscado com sucesso.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> searchCategory(@PathVariable Long id) {
        Category category = categoryService.searchCategory(id);

        return ResponseEntity.ok(CategoryMapper.toDto(category));
    }

    @Operation(summary = "Deletar uma categoria.", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN", responses = {
            @ApiResponse(responseCode = "204", description = "Recurso deletado com sucesso.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        Category category = categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(category);
    }

    @Operation(summary = "Alterar uma nova categoria.", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN", responses = {
            @ApiResponse(responseCode = "200", description = "Recurso alterado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryCreateDto.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long id,
            @Valid @RequestBody CategoryUpdateDto categoryUpdateDto) {
        Category existingCategory = categoryService.searchCategory(id);

        if (existingCategory == null) {
            return ResponseEntity.notFound().build();
        }

        Category updateCategory = categoryService.updateCategory(id,
                categoryUpdateDto.getName());

        return ResponseEntity.ok(CategoryMapper.toDto(updateCategory));
    }
}
