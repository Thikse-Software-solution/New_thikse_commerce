package com.example.admin.Controller;

import com.example.admin.Entity.CustomerBehavior;
import com.example.admin.Service.CustomerBehaviorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics/customer-behavior")
public class CustomerBehaviorController {
    @Autowired
    private CustomerBehaviorService service;

    @GetMapping("/{customerId}")
    public CustomerBehavior getCustomerBehavior(@PathVariable Long customerId) {
        return service.getCustomerBehavior(customerId);
    }

    // Additional endpoints as needed
}
