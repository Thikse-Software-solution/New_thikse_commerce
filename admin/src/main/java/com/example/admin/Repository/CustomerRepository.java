//package com.example.admin.Repository;
//
//
//import com.example.admin.Entity.Customer;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface CustomerRepository extends JpaRepository<Customer, Long> {
//
//
//    List<Customer> findCustomersWithItemsInCart();
//}
package com.example.admin.Repository;
import com.example.admin.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
