package com.example.admin.Controller;

import com.example.admin.Entity.ShippingDetail;
import com.example.admin.Service.ShippingDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics/shipping-details")
public class ShippingDetailController {
    @Autowired
    private ShippingDetailService service;

    @GetMapping("/{orderId}")
    public ShippingDetail getShippingDetail(@PathVariable Long orderId) {
        return service.getShippingDetail(orderId);
    }

    // Additional endpoints as needed
}
