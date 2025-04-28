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

import com.marcosporto.demo_product_api.entity.Inventory;
import com.marcosporto.demo_product_api.service.InventoryService;
import com.marcosporto.demo_product_api.web.dto.InventoryCreateDto;
import com.marcosporto.demo_product_api.web.dto.InventoryResponseDto;
import com.marcosporto.demo_product_api.web.dto.InventoryUpdateDto;
import com.marcosporto.demo_product_api.web.dto.ProductCreateDto;
import com.marcosporto.demo_product_api.web.dto.mapper.InventoryMapper;
import com.marcosporto.demo_product_api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.*;

@Tag(name = "Estoques", description = "Contém todas as operações relativas aos recurso para cadastro, edição, exclusão e leitura de estoques.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @Operation(summary = "Criar um novo estoque.", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN", responses = {
            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCreateDto.class))),
            @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventoryResponseDto> create(@Valid @RequestBody InventoryCreateDto inventoryCreateDto) {
        Inventory newInventory = inventoryService.save(inventoryCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(InventoryMapper.toDto(newInventory));
    }

    @Operation(summary = "Listar todos os estoques.", description = "Requisição exige um Bearer Token.", responses = {
            @ApiResponse(responseCode = "200", description = "Recurso listado com sucesso.", content = @Content(mediaType = "application/json")),

    })
    @GetMapping
    public ResponseEntity<List<InventoryResponseDto>> getAll() {
        List<Inventory> inventories = inventoryService.findAll();

        return ResponseEntity.ok(InventoryMapper.toListDto(inventories));

    }

    @Operation(summary = "Buscar um Estoque.", description = "Requisição exige um Bearer Token.", responses = {
            @ApiResponse(responseCode = "200", description = "Recurso buscado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCreateDto.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponseDto> searchInventory(@PathVariable Long id) {
        Inventory inventory = inventoryService.searchInventory(id);

        return ResponseEntity.ok(InventoryMapper.toDto(inventory));
    }

    @Operation(summary = "Deletar um estoque.", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN", responses = {
            @ApiResponse(responseCode = "204", description = "Recurso deletado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCreateDto.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Inventory> deleteInventory(@PathVariable Long id) {
        Inventory inventory = inventoryService.deleteInventory(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(inventory);
    }

    @Operation(summary = "Alterar estoque.", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN", responses = {
            @ApiResponse(responseCode = "200", description = "Recurso alterado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCreateDto.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventoryResponseDto> updateinventory(@PathVariable Long id,
            @Valid @RequestBody InventoryUpdateDto inventoryUpdateDto) {
        Inventory existingInventory = inventoryService.searchInventory(id);
        if (existingInventory == null) {
            return ResponseEntity.notFound().build();
        }

        Inventory updatedInventory = inventoryService.updateInventory(id,
                inventoryUpdateDto.getQuantity(),
                inventoryUpdateDto.getProductId()

        );

        return ResponseEntity.ok(InventoryMapper.toDto(updatedInventory));

    }
}
