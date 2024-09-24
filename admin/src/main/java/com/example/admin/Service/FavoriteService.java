//package com.example.admin.Service;
//
//import com.example.admin.Entity.Favorite;
//import com.example.admin.Entity.Product;
//import com.example.admin.Entity.User;
//import com.example.admin.Repository.FavoriteRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class FavoriteService {
//
//    @Autowired
//    private FavoriteRepository favoriteRepository;
//
//    public boolean addFavorite(User user, Product product) {
//        if (favoriteRepository.findByUserAndProduct(user, product).isPresent()) {
//            return false; // Already favorited
//        }
//        Favorite favorite = new Favorite();
//        favorite.setUser(user);
//        favorite.setProduct(product);
//        favoriteRepository.save(favorite);
//        return true;
//    }
//
//    public boolean removeFavorite(User user, Product product) {
//        Optional<Favorite> favorite = favoriteRepository.findByUserAndProduct(user, product);
//        if (favorite.isPresent()) {
//            favoriteRepository.delete(favorite.get());
//            return true;
//        }
//        return false;
//    }
//
//    public List<Product> getUserFavorites(User user) {
//        List<Favorite> favorites = favoriteRepository.findByUser(user);
//        return favorites.stream()
//                .map(Favorite::getProduct)
//                .collect(Collectors.toList());
//    }
//
//
//}
