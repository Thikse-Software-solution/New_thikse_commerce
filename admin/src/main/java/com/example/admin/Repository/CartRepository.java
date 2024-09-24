package com.example.admin.Repository;

import com.example.admin.Entity.Cart;
import com.example.admin.Entity.Product;
import com.example.admin.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
    List<Cart> findAllByAddedDateBefore(LocalDateTime dateTime);
    List<Cart> findByUserId(Long userId);
    Optional<Cart> findByUserIdAndProductId(Long userId, Long productId);

    Optional<Cart> findByUserAndProduct(User user, Product product);
}
