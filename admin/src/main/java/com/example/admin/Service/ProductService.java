package com.example.admin.Service;

import com.example.admin.Entity.Product;
import com.example.admin.Repository.ProductRepository;
import com.example.admin.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = getProductById(id);
        // Update fields as needed
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        // Continue setting other fields...
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Product not found with ID: " + id);
        }
    }
//
//    @Transactional
//    public Product getProductWithReviews(Long productId) {
//        return getProductById(productId);
//    }
//
//    public Product addRating(Long productId, int rating) {
//        Product product = getProductById(productId);
//        product.addRating(rating);  // Add new rating and update average
//        return productRepository.save(product);
//    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }


}
