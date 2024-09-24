package com.example.admin.Repository;



import com.example.admin.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByPaymentId(String paymentId);
}