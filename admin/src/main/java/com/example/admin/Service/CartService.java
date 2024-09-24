//package com.example.admin.Service;
//
//import com.example.admin.Entity.Cart;
//import com.example.admin.Entity.Product;
//import com.example.admin.Entity.User;
//import com.example.admin.Repository.CartRepository;
//import com.example.admin.Repository.ProductRepository;
//import com.example.admin.Repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CartService {
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    public List<Cart> getCartItemsByUserId(Long userId) {
//        return cartRepository.findByUserId(userId);
//    }
//    public Cart addOrUpdateCartItem(Long userId, Long productId, int quantity) {
//        // Fetch the user and product from the database to ensure they are in a persistent state
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
//
//        // Check if the cart item already exists for the user and product
//        Optional<Cart> existingCartItem = cartRepository.findByUserAndProduct(user, product);
//
//        Cart cart;
//        if (existingCartItem.isPresent()) {
//            cart = existingCartItem.get();
//            cart.setQuantity(cart.getQuantity() + quantity); // Update the quantity
//        } else {
//            cart = new Cart();
//            cart.setUser(user); // Set the persisted User
//            cart.setProduct(product); // Set the persisted Product
//            cart.setQuantity(quantity); // Set the quantity
//        }
//
//        cart.setTotalPrice(cart.getQuantity() * product.getPrice()); // Calculate total price
//        return cartRepository.save(cart); // Save the cart item
//    }
//
//    public void removeCartItem(Long cartItemId) {
//        cartRepository.deleteById(cartItemId);
//    }
//}
package com.example.admin.Service;

import com.example.admin.Entity.Cart;
import com.example.admin.Entity.Product;
import com.example.admin.Entity.User;
import com.example.admin.Repository.CartRepository;
import com.example.admin.Repository.ProductRepository;
import com.example.admin.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // Get cart items by user ID
    public List<Cart> getCartItemsByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    // Add or update cart item
    public Cart addOrUpdateCartItem(Long userId, Long productId, int quantity) {
        // Fetch the user and product from the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        // Check if the cart item already exists for the user and product
        Optional<Cart> existingCartItem = cartRepository.findByUserAndProduct(user, product);

        Cart cart;
        if (existingCartItem.isPresent()) {
            cart = existingCartItem.get();
            // Replace the quantity with the new value provided
            if (quantity <= 0) {
                throw new RuntimeException("Quantity cannot be less than or equal to zero");
            }
            cart.setQuantity(quantity); // Replace the existing quantity with the new value
        } else {
            if (quantity <= 0) {
                throw new RuntimeException("Quantity must be greater than zero for a new cart item");
            }
            // Create a new cart item
            cart = new Cart();
            cart.setUser(user); // Set the user
            cart.setProduct(product); // Set the product
            cart.setQuantity(quantity); // Set the quantity for the new cart item
        }

        // Calculate total price based on the quantity
        cart.setTotalPrice(cart.getQuantity() * product.getPrice());

        // Save and return the updated cart item
        return cartRepository.save(cart);
    }


    // Remove a cart item by ID
    public void removeCartItem(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }
    // Debugging in CartService
    public int getTotalDistinctItems(Long userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        System.out.println("Cart items for user ID " + userId + ": " + cartItems.size());
        return cartItems.size(); // Return the count of distinct items
    }

}
