package com.example.admin.Repository;

import com.example.admin.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);
    Optional<Address> findByAddressLine1AndCityAndStateAndZip(String addressLine1, String city, String state, String zip);
}
