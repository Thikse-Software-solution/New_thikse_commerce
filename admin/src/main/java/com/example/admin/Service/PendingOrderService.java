package com.example.admin.Service;

import com.example.admin.Entity.PendingOrder;
import com.example.admin.Repository.PendingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PendingOrderService {
    @Autowired
    private PendingOrderRepository repository;

    public PendingOrder getPendingOrder(Long orderId) {
        return repository.findById(orderId).orElse(null);
    }

    // Additional methods as needed
}
