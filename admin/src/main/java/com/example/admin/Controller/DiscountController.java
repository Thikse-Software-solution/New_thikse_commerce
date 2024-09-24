package com.example.admin.Controller;

import com.example.admin.Entity.Discount;
import com.example.admin.Service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @GetMapping
    public List<Discount> getAllDiscounts() {
        return discountService.findAll();
    }

    @PostMapping
    public ResponseEntity<Void> createDiscount(@RequestBody Discount discount) {
        discountService.save(discount);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
