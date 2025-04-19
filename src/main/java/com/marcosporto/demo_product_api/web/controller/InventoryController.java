package com.marcosporto.demo_product_api.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcosporto.demo_product_api.entity.Inventory;
import com.marcosporto.demo_product_api.service.InventoryService;
import com.marcosporto.demo_product_api.web.dto.InventoryCreateDto;
import com.marcosporto.demo_product_api.web.dto.InventoryResponseDto;
import com.marcosporto.demo_product_api.web.dto.mapper.InventoryMapper;

import jakarta.validation.Valid;
import lombok.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventoryResponseDto> create(@Valid @RequestBody InventoryCreateDto inventoryCreateDto) {
        Inventory newInventory = inventoryService.save(inventoryCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(InventoryMapper.toDto(newInventory));
    }
}
