//package com.example.admin.Controller;
//
//import com.example.admin.Entity.Product;
//import com.example.admin.Entity.User;
//import com.example.admin.Service.FavoriteService;
//import com.example.admin.Service.ProductService;
//import com.example.admin.Service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.security.Principal;
//
////package com.example.admin.Controller;
////
////import com.example.admin.Entity.Product;
////import com.example.admin.Entity.User;
////import com.example.admin.Service.FavoriteService;
////import com.example.admin.Service.ProductService;
////import com.example.admin.Service.UserService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////
////import java.security.Principal;
////import java.util.List;
////import java.util.Optional;
////@RestController
////@RequestMapping("/api/favorites")
////public class FavoriteController {
////    @Autowired
////    private FavoriteService favoriteService;
////
////    @Autowired
////    private UserService userService;
////    // Assuming you have a service to get the current user
////@Autowired
////private  ProductService productService;
////    @PostMapping("/add")
////    public ResponseEntity<?> addFavorite(@RequestParam Long productId) {
////        User user = userService.getCurrentUser(); // Assuming you have logic for fetching the logged-in user
////        Product product = productService.getProductById(productId); // Get product by ID
////        boolean added = favoriteService.addFavorite(user, product);
////        return ResponseEntity.ok(added ? "Added to favorites" : "Already in favorites");
////    }
////
////    @PostMapping("/remove")
////    public ResponseEntity<?> removeFavorite(@RequestParam Long productId) {
////        User user = userService.getCurrentUser();
////        Product product = productService.getProductById(productId);
////        boolean removed = favoriteService.removeFavorite(user, product);
////        return ResponseEntity.ok(removed ? "Removed from favorites" : "Not in favorites");
////    }
////
////    @GetMapping
////    public ResponseEntity<List<Product>> getUserFavorites() {
////        User user = userService.getCurrentUser();
////        List<Product> favorites = favoriteService.getUserFavorites(user);
////        return ResponseEntity.ok(favorites);
////    }
////}
//@RestController
//@RequestMapping("/api/favorites")
//public class FavoriteController {
//
//    @Autowired
//    private FavoriteService favoriteService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private ProductService productService;
//
//    @PostMapping("/add")
//    public ResponseEntity<?> addFavorite(@RequestParam Long productId, Principal principal) {
//        if (principal == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
//        }
//
//        User user = userService.(principal.getName()); // Fetch the user based on the username
//        Product product = productService.getProductById(productId); // Get product by ID
//
//        if (product == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
//        }
//
//        boolean added = favoriteService.addFavorite(user, product);
//        return ResponseEntity.ok(added ? "Added to favorites" : "Already in favorites");
//    }
//
//    @PostMapping("/remove")
//    public ResponseEntity<?> removeFavorite(@RequestParam Long productId, Principal principal) {
//        if (principal == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
//        }
//
//        User user = userService.getUserByUsername(principal.getName()); // Fetch the user based on the username
//        Product product = productService.getProductById(productId); // Get product by ID
//
//        if (product == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
//        }
//
//        boolean removed = favoriteService.removeFavorite(user, product);
//        return ResponseEntity.ok(removed ? "Removed from favorites" : "Not in favorites");
//    }
//
//    @GetMapping
//    public ResponseEntity<?> getUserFavorites(Principal principal) {
//        if (principal == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
//        }
//
//        User user = userService.getUserByUsername(principal.getName()); // Fetch the user based on the username
//        List<Product> favorites = favoriteService.getUserFavorites(user);
//        return ResponseEntity.ok(favorites);
//    }
//}
//
