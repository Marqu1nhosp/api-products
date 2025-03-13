package com.marcosporto.demo_product_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcosporto.demo_product_api.entity.Product;
import com.marcosporto.demo_product_api.exception.EntityNotFoundException;
import com.marcosporto.demo_product_api.repository.ProductRepository;

import lombok.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public List<Product> findAll() {
        return productRepository.findAll();
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
    public Product updateProduct(Long id, String name, Double price, String description) {
        Product product = searchProduct(id);
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);

        return productRepository.save(product);
    }

}
