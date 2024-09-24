package com.example.admin.Service;

import com.example.admin.Entity.ShippingDetail;
import com.example.admin.Repository.ShippingDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingDetailService {
    @Autowired
    private ShippingDetailRepository repository;

    public ShippingDetail getShippingDetail(Long orderId) {
        return repository.findById(orderId).orElse(null);
    }

    // Additional methods as needed
}
