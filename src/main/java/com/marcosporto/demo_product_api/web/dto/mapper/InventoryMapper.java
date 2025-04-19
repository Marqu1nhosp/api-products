package com.marcosporto.demo_product_api.web.dto.mapper;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.marcosporto.demo_product_api.entity.Inventory;
import com.marcosporto.demo_product_api.web.dto.InventoryCreateDto;
import com.marcosporto.demo_product_api.web.dto.InventoryResponseDto;

public class InventoryMapper {
    public static Inventory toInventory(InventoryCreateDto inventoryCreateDto) {
        return new ModelMapper().map(inventoryCreateDto, Inventory.class);
    }

    public static InventoryResponseDto toDto(Inventory inventory) {
        return new ModelMapper().map(inventory, InventoryResponseDto.class);
    }

    public static List<InventoryResponseDto> toListDto(List<Inventory> inventories) {
        return inventories.stream().map(inventory -> toDto(inventory)).collect(Collectors.toList());
    }

    // public static Inventory toUpdateInventory(InventoryUpdateDto
    // updateInventoryDto){
    // return new ModelMapper().map(updateInventoryDto, Inventory.class)
    // }
}
