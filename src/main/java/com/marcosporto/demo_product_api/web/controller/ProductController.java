package com.marcosporto.demo_product_api.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcosporto.demo_product_api.entity.Product;
import com.marcosporto.demo_product_api.service.ProductService;
import com.marcosporto.demo_product_api.web.dto.ProductCreateDto;
import com.marcosporto.demo_product_api.web.dto.ProductResponseDto;
import com.marcosporto.demo_product_api.web.dto.ProductUpdateDto;
import com.marcosporto.demo_product_api.web.dto.mapper.ProductMapper;
import com.marcosporto.demo_product_api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "Produtos", description = "Contém todas as operações relativas aos recurso para cadastro, edição, exclusão e leitura de produdos.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Criar um novo produto.", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN", responses = {
            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCreateDto.class))),
            @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> create(@Valid @RequestBody ProductCreateDto productCreateDto) {
        Product newProduct = productService.save(productCreateDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.toDto(newProduct));
    }

    @Operation(summary = "Listar todos os produtos.", description = "Requisição exige um Bearer Token.", responses = {
            @ApiResponse(responseCode = "200", description = "Recurso listado com sucesso.", content = @Content(mediaType = "application/json")),

    })
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAll() {
        List<Product> products = productService.findAll();

        return ResponseEntity.ok(ProductMapper.toListDto(products));
    }

    @Operation(summary = "Buscar um produto.", description = "Requisição exige um Bearer Token.", responses = {
            @ApiResponse(responseCode = "200", description = "Recurso buscado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCreateDto.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> searchProduct(@PathVariable Long id) {
        Product product = productService.searchProduct(id);

        return ResponseEntity.ok(ProductMapper.toDto(product));
    }

    @Operation(summary = "Deletar um produto.", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN", responses = {
            @ApiResponse(responseCode = "204", description = "Recurso deletado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCreateDto.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        Product product = productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(product);
    }

    @Operation(summary = "Alterar um produto.", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN", responses = {
            @ApiResponse(responseCode = "200", description = "Recurso alterado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCreateDto.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id,
            @Valid @RequestBody ProductUpdateDto productUpdateDto) {

        Product existingProduct = productService.searchProduct(id);
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }

        Product updatedProduct = productService.updateProduct(id,
                productUpdateDto.getName(),
                productUpdateDto.getPrice(),
                productUpdateDto.getDescription(),
                productUpdateDto.getCategoryId());

        return ResponseEntity.ok(ProductMapper.toDto(updatedProduct));
    }

}
