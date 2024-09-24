package com.example.admin.Service;

import com.example.admin.Entity.Address;
import com.example.admin.Entity.User;
import com.example.admin.Repository.AddressRepository;
import com.example.admin.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    @Transactional
    public Address updateAddress(Address address) {
        // Ensure the address has an ID and exists in the repository
        if (address.getId() == null || !addressRepository.existsById(address.getId())) {
            throw new RuntimeException("Address not found");
        }

        // Fetch the existing address (optional step if you want to preserve some fields)
        Address existingAddress = addressRepository.findById(address.getId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // Optional: Update fields if you are not sending a full update
        existingAddress.setName(address.getName());
        existingAddress.setPhone(address.getPhone());
        existingAddress.setAddressLine1(address.getAddressLine1());
        existingAddress.setAddressLine2(address.getAddressLine2());
        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());
        existingAddress.setZip(address.getZip());
        existingAddress.setType(address.getType());

        // If you have a user field, ensure the user remains unchanged unless you're updating it explicitly
        // existingAddress.setUser(existingAddress.getUser());

        // Save the updated address
        return addressRepository.save(existingAddress);
    }


    @Transactional
    public Address addAddressByUserId(Long userId, Address address) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        address.setUser(user);  // Assuming Address has a field 'user' of type User
        return addressRepository.save(address);
    }

    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Transactional
    public void selectAddress(Long userId, Long addressId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        user.setSelectedAddress(address);
        userRepository.save(user);
    }

    public Address getSelectedAddress(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getSelectedAddress();
    }


    public Address findById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
    }
}
