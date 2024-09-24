package com.example.admin.Repository;

import com.example.admin.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStockQuantityLessThan(int quantity);
    Optional<Product> findById(Long id);
}