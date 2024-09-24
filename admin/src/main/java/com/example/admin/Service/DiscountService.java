package com.example.admin.Service;

import com.example.admin.Entity.Discount;
import com.example.admin.Repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    public void save(Discount discount) {
        discountRepository.save(discount);
    }
}
