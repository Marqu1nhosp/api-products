package com.marcosporto.demo_product_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcosporto.demo_product_api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
