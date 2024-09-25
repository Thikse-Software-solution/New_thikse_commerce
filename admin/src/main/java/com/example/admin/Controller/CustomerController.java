package com.example.admin.Controller;

import com.example.admin.Entity.Customer;
import com.example.admin.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/customers")
@CrossOrigin(origins = "http://192.168.1.20:4200")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Get all customers
    @GetMapping("/get")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // Get a customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    // Add a new customer
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Customer> addCustomer(
            @RequestParam("name") String name,
            @RequestParam("text") String text,
            @RequestParam(value = "customerImg", required = false) MultipartFile customerImg) {

        Customer customer = new Customer();
        customer.setName(name);
        customer.setText(text);

        if (customerImg != null && !customerImg.isEmpty()) {
            try {
                // Convert file to bytes and set the image
                customer.setCustomerImg(customerImg.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(null); // Return 500 if there is an error with file upload
            }
        }

        Customer savedCustomer = customerService.addCustomer(customer);
        return ResponseEntity.status(201).body(savedCustomer); // Return 201 Created
    }

    // Update a customer
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        return updatedCustomer != null ? ResponseEntity.ok(updatedCustomer) : ResponseEntity.notFound().build();
    }

    // Delete a customer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (!customerService.getAllCustomers().stream().anyMatch(c -> c.getId().equals(id))) {
            return ResponseEntity.notFound().build(); // Check if customer exists
        }
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
