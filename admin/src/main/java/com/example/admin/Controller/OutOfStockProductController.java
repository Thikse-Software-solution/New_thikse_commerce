package com.example.admin.Controller;

import com.example.admin.Entity.OutOfStockProduct;
import com.example.admin.Service.OutOfStockProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics/out-of-stock-products")
public class OutOfStockProductController {
    @Autowired
    private OutOfStockProductService service;

    @GetMapping("/{productId}")
    public OutOfStockProduct getOutOfStockProduct(@PathVariable Long productId) {
        return service.getOutOfStockProduct(productId);
    }

    // Additional endpoints as needed
}
