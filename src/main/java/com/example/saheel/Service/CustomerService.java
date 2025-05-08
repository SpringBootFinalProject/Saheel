package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.DtoIn.CustomerDtoIn;
import com.example.saheel.Model.Customer;
import com.example.saheel.Model.User;
import com.example.saheel.Repository.CustomerRepository;
import com.example.saheel.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final HelperService helperService;

    public List<Customer> getAllCustomers(String username) {
        // Check if the admin in the database.
        helperService.getAdminOrThrow(username);

        // Return the customers
        return customerRepository.findAll();
    }

    public void registerCustomer(CustomerDtoIn customerDtoIn) {

        //Create the user and set the role.
        User user = new User(null, customerDtoIn.getUsername(), new BCryptPasswordEncoder().encode(customerDtoIn.getPassword()),
                "CUSTOMER", customerDtoIn.getFullName(), customerDtoIn.getAge(), customerDtoIn.getEmail(), customerDtoIn.getPhoneNumber()
                , null, null, null);

        // Save the user
        userRepository.save(user);

        // Create the customer and save it.
        Customer customer = new Customer(null, customerDtoIn.getLevel(), user, null, null);
        customerRepository.save(customer);

        // Set the user and save it.
        user.setCustomer(customer);
        userRepository.save(user);
    }

    public void updateCustomer(Integer userId, CustomerDtoIn customerDtoIn) {
        // Get the user and check if it's in the database.
        User user = helperService.getCustomerOrThrow(userId);

        // Update
        user.setPassword(new BCryptPasswordEncoder().encode(customerDtoIn.getPassword()));
        user.setUsername(customerDtoIn.getUsername());
        user.setEmail(customerDtoIn.getEmail());
        user.setPhoneNumber(customerDtoIn.getPhoneNumber());
        user.setAge(customerDtoIn.getAge());
        user.setFullName(customerDtoIn.getFullName());
        user.getCustomer().setLevel(customerDtoIn.getLevel());

        // Save
        userRepository.save(user);
    }

    public void deleteCustomer(Integer userId) {
        // Get the user and check if it's in the database.
        User user = helperService.getCustomerOrThrow(userId);

        // Delete
        userRepository.delete(user);
    }
}
