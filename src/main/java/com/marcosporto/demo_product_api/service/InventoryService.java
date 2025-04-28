package com.marcosporto.demo_product_api.service;

import java.util.List;

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

    @Transactional
    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    public Inventory searchInventory(Long id) {
        return inventoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Estoque não encontrado!", id)));
    }

    public Inventory deleteInventory(Long id) {
        Inventory inventory = searchInventory(id);
        inventoryRepository.deleteById(id);

        return inventory;

    }

    public Inventory updateInventory(Long id, Integer quantity, Long getproductid) {
        Inventory inventory = searchInventory(id);
        Product product = productRepository.findById(getproductid).orElseThrow(
                () -> new EntityNotFoundException("Produto não encontrado"));

        inventory.setQuantity(quantity);
        inventory.setProduct(product);

        return inventoryRepository.save(inventory);
    }

}
