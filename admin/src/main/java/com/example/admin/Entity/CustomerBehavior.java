package com.example.admin.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_behavior")
public class CustomerBehavior {
    @Id
    private Long customerId;
    private String name;
    private String email;
    private String purchaseHistory; // Consider using a more structured format or separate entities
    private String browsingHistory;
    private String cartAbandonment;
    private String searchQueries;
    private String feedbackReviews;

    

}
