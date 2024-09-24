//package com.example.admin.Repository;
//
//import com.example.admin.Entity.Favorite;
//import com.example.admin.Entity.Product;
//import com.example.admin.Entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
//    Optional<Favorite> findByUserAndProduct(User user, Product product);
//    List<Favorite> findByUser(User user);
//}
