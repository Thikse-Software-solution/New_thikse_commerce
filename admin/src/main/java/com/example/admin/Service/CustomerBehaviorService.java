package com.example.admin.Service;

import com.example.admin.Entity.CustomerBehavior;
import com.example.admin.Repository.CustomerBehaviorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerBehaviorService {
    @Autowired
    private CustomerBehaviorRepository repository;

    public CustomerBehavior getCustomerBehavior(Long customerId) {
        return repository.findById(customerId).orElse(null);
    }

    // Additional methods as needed
}
