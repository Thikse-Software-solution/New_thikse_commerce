package com.example.admin.Repository;

import com.example.admin.Entity.Order;
import com.example.admin.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    boolean existsByUserIdAndProductId(Long userId, Long productId);
    List<Order> findByUser(User user);

    @Query("SELECT SUM(o.amount) FROM Order o")
    Double calculateTotalSales();
}