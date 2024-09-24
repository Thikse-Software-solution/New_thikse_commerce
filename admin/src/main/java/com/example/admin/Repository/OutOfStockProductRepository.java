package com.example.admin.Repository;

import com.example.admin.Entity.OutOfStockProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutOfStockProductRepository extends JpaRepository<OutOfStockProduct, Long> {
}
