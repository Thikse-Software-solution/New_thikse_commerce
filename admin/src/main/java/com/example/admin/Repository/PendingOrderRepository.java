package com.example.admin.Repository;

import com.example.admin.Entity.PendingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingOrderRepository extends JpaRepository<PendingOrder, Long> {
}
