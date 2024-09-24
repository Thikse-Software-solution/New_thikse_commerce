package com.example.admin.Controller;

import com.example.admin.Entity.Address;
import com.example.admin.Service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add/user/{userId}")
    public ResponseEntity<Address> addAddressByUserId(@PathVariable Long userId, @RequestBody Address address) {
        Address savedAddress = addressService.addAddressByUserId(userId, address);
        return ResponseEntity.ok(savedAddress);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        Address address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }

    @PutMapping("/update")
    public ResponseEntity<Address> updateAddress(@RequestBody Address address) {
        try {
            // Call the service to update the address
            Address updatedAddress = addressService.updateAddress(address);
            return ResponseEntity.ok(updatedAddress);
        } catch (RuntimeException e) {
            // Return a 404 or 400 status code if the address is not found or invalid
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> getAddressesByUserId(@PathVariable Long userId) {
        List<Address> addresses = addressService.getAddressesByUserId(userId);
        return ResponseEntity.ok(addresses);
    }

    @PostMapping("/select/{addressId}")
    public ResponseEntity<Void> selectAddress(@PathVariable Long addressId, @RequestParam Long userId) {
        addressService.selectAddress(userId, addressId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/select/{userId}")
    public ResponseEntity<Address> getSelectedAddress(@PathVariable Long userId) {
        Address selectedAddress = addressService.getSelectedAddress(userId);
        return ResponseEntity.ok(selectedAddress);
    }
}
