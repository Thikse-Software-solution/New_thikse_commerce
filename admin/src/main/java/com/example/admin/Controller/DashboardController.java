package com.example.admin.Controller;

import com.example.admin.Entity.Product;
import com.example.admin.Service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Object>> getMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalSales", dashboardService.getTotalSales());
        metrics.put("totalOrders", dashboardService.getTotalOrders());
        metrics.put("totalProducts", dashboardService.getTotalProducts());
        metrics.put("totalCustomers", dashboardService.getTotalCustomers());
//        metrics.put("totalInventoryValue", dashboardService.getTotalInventoryValue());
        return ResponseEntity.ok(metrics);
    }
    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> getLowStockProducts() {
        List<Product> lowStockProducts = dashboardService.getLowStockProducts();
        return ResponseEntity.ok(lowStockProducts);
    }
}
