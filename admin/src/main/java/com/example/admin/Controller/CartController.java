package com.example.admin.Controller;

import com.example.admin.Entity.Cart;
import com.example.admin.Entity.Product;
import com.example.admin.Entity.User;
import com.example.admin.Service.CartService;
import com.example.admin.Service.ProductService;
import com.example.admin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    // Get cart items for a specific user
    @GetMapping("/items/{userId}")
    public ResponseEntity<List<Cart>> getCartItems(@PathVariable Long userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Cart> cartItems = cartService.getCartItemsByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }

    // Add or update cart items
    @PostMapping("/add")
    public ResponseEntity<?> addOrUpdateCartItem(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
        }

        Optional<Product> productOptional = Optional.ofNullable(productService.getProductById(productId));
        if (productOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with ID: " + productId);
        }

        try {
            // Pass the User and Product IDs to the service method
            Cart cart = cartService.addOrUpdateCartItem(userOptional.get().getId(), productOptional.get().getId(), quantity);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding/updating cart item: " + e.getMessage());
        }
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Map<String, String>> removeCartItem(@PathVariable Long cartItemId) {
        try {
            cartService.removeCartItem(cartItemId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Item removed successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error removing item: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/distinct-items/{userId}")
    public ResponseEntity<Integer> getTotalDistinctItems(@PathVariable Long userId) {
        // Get total distinct items from the service
        int totalDistinctItems = cartService.getTotalDistinctItems(userId);

        // Return the count as a JSON response
        return ResponseEntity.ok(totalDistinctItems);
    }
    
}
