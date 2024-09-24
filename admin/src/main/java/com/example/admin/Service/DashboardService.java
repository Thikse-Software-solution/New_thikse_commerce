package com.example.admin.Service;

import com.example.admin.Entity.Product;
import com.example.admin.Repository.UserRepository;
import com.example.admin.Repository.OrderRepository;
import com.example.admin.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Product> getLowStockProducts() {
        return productRepository.findByStockQuantityLessThan(10);
    }
    public double getTotalSales() {
        // Assuming `Order` entity has a `totalPrice` field
        return orderRepository.findAll().stream()
                .mapToDouble(order -> order.getTotalPrice())
                .sum();
    }


    public long getTotalOrders() {
        return orderRepository.count();
    }

    public long getTotalProducts() {
        return productRepository.count();
    }

    public long getTotalCustomers() {
        return userRepository.count();
    }

//    public double getTotalInventoryValue() {
//        return inventoryRepository.calculateTotalInventoryValue();
//    }
}
