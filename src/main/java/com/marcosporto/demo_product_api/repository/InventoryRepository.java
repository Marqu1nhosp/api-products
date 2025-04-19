package com.marcosporto.demo_product_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcosporto.demo_product_api.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
