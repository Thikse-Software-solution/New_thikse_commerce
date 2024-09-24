package com.example.admin.Controller;
import com.example.admin.Entity.ProductUser;
import com.example.admin.Service.ProductServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productsuser")
public class ProductControllerUser {

    @Autowired
    private ProductServiceUser productServiceuser;

    @GetMapping
    public ResponseEntity<List<ProductUser>> getAllProducts() {
        List<ProductUser> products = productServiceuser.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductUser> getProductById(@PathVariable Long id) {
        ProductUser product = productServiceuser.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductUser> addProduct(@RequestBody ProductUser product) {
        ProductUser createdProduct = productServiceuser.addProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductUser> updateProduct(@PathVariable Long id, @RequestBody ProductUser product) {
        ProductUser updatedProduct = productServiceuser.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productServiceuser.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
