package com.example.admin.Repository;

import com.example.admin.Entity.ProductUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepositoryUser extends JpaRepository<ProductUser, Long> {
}
