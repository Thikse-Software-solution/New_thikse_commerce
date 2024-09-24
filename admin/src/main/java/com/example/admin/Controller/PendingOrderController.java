package com.example.admin.Controller;

import com.example.admin.Entity.PendingOrder;
import com.example.admin.Service.PendingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics/pending-orders")
public class PendingOrderController {
    @Autowired
    private PendingOrderService service;

    @GetMapping("/{orderId}")
    public PendingOrder getPendingOrder(@PathVariable Long orderId) {
        return service.getPendingOrder(orderId);
    }

    // Additional endpoints as needed
}
