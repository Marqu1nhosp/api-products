package com.marcosporto.demo_product_api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcosporto.demo_product_api.entity.Category;
import com.marcosporto.demo_product_api.entity.Inventory;
import com.marcosporto.demo_product_api.entity.Product;
import com.marcosporto.demo_product_api.exception.EntityNotFoundException;
import com.marcosporto.demo_product_api.repository.CategoryRepository;
import com.marcosporto.demo_product_api.repository.InventoryRepository;
import com.marcosporto.demo_product_api.repository.ProductRepository;
import com.marcosporto.demo_product_api.web.dto.ProductCreateDto;
import com.marcosporto.demo_product_api.web.dto.ProductResponseDto;

import lombok.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final InventoryRepository inventoryRepository;

    @Transactional
    public Product save(ProductCreateDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCreateDate(LocalDateTime.now());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Transactional
    public List<ProductResponseDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

        for (Product product : products) {
            Inventory inventory = inventoryRepository.findByProductId(product.getId());

            ProductResponseDto productResponseDto = new ProductResponseDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getCategory().getName(),
                    inventory != null ? inventory.getQuantity() : null);

            productResponseDtos.add(productResponseDto);
        }

        return productResponseDtos;
    }

    @Transactional(readOnly = true)
    public Product searchProduct(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Produto id=%s não encotrado!", id)));
    }

    @Transactional
    public Product deleteProduct(Long id) {
        Product product = searchProduct(id);
        productRepository.deleteById(id);

        return product;
    }

    @Transactional
    public Product updateProduct(Long id, String name, Double price, String description, Long categoryId) {
        Product product = searchProduct(id);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategory(category);

        return productRepository.save(product);
    }

}
