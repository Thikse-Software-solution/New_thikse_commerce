package com.example.admin.Service;

import com.example.admin.Entity.ProductUser;
import com.example.admin.Repository.ProductRepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceUser {

    @Autowired
    private ProductRepositoryUser productRepositoryUser;

    public List<ProductUser> getAllProducts() {
        return productRepositoryUser.findAll();
    }

    public ProductUser getProductById(Long id) {
        Optional<ProductUser> product = productRepositoryUser.findById(id);
        return product.orElse(null);
    }

    public ProductUser addProduct(ProductUser product) {
        return productRepositoryUser.save(product);
    }

    public ProductUser updateProduct(Long id, ProductUser product) {
        product.setId(id);
        return productRepositoryUser.save(product);
    }

    public void deleteProduct(Long id) {
        productRepositoryUser.deleteById(id);
    }
}
