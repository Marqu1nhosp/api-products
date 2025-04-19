package com.marcosporto.demo_product_api.service;

import org.springframework.stereotype.Service;

import com.marcosporto.demo_product_api.entity.Inventory;
import com.marcosporto.demo_product_api.entity.Product;
import com.marcosporto.demo_product_api.repository.InventoryRepository;
import com.marcosporto.demo_product_api.repository.ProductRepository;
import com.marcosporto.demo_product_api.web.dto.InventoryCreateDto;
import com.marcosporto.demo_product_api.exception.EntityNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Inventory save(InventoryCreateDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        Inventory inventory = new Inventory();
        inventory.setQuantity(dto.getQuantity());
        inventory.setProduct(product);

        return inventoryRepository.save(inventory);
    }
}
