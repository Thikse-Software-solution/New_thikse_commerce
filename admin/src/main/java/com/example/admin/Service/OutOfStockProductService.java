package com.example.admin.Service;

import com.example.admin.Entity.OutOfStockProduct;
import com.example.admin.Repository.OutOfStockProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutOfStockProductService {
    @Autowired
    private OutOfStockProductRepository repository;

    public OutOfStockProduct getOutOfStockProduct(Long productId) {
        return repository.findById(productId).orElse(null);
    }

    // Additional methods as needed
}
