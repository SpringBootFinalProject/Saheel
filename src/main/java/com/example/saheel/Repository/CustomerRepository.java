package com.example.saheel.Repository;

import com.example.saheel.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findCustomerById(Integer customerId);
}
