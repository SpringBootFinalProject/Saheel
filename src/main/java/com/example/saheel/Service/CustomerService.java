package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.DTO.CustomerDTO;
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
    private final EmailService emailService;
    private final WhatsAppNotifications whatsAppNotifications;


    public List<Customer> getAllCustomers(String username) {
        // Check if the admin in the database.
        helperService.getAdminOrThrow(username);

        // Return the customers
        return customerRepository.findAll();
    }

    // ( #8 of 50 endpoints )
    public void registerCustomer(CustomerDTO customerDtoIn) {

        if (userRepository.existsByUsername(customerDtoIn.getUsername())) {
            throw new ApiException("This Username is already in use");
        }

        if (userRepository.existsByEmail(customerDtoIn.getEmail())) {
            throw new ApiException("This Email is already in use");
        }

        if (userRepository.existsByPhoneNumber(customerDtoIn.getPhoneNumber())) {
            throw new ApiException("This Phone Number is already in use");

        }
        //Create the user and set the role.
        User user = new User(null, customerDtoIn.getUsername(), new BCryptPasswordEncoder().encode(customerDtoIn.getPassword()),
                "CUSTOMER", customerDtoIn.getFullName(), customerDtoIn.getAge(), customerDtoIn.getEmail(), customerDtoIn.getPhoneNumber()
                , null, null, null);

        // Save the user
        userRepository.save(user);

        // Create the customer and save it.
        Customer customer = new Customer(null, customerDtoIn.getLevel(), user, null, null, null);
        customerRepository.save(customer);
        sendWelcomeEmail(user);
        // Send WhatsApp welcome message
        String whatsappMessage = "أهلاً بك " + user.getFullName() + " في منصة صهيل!\n"
                + "نتمنى لك تجربة ممتعة ومليئة بالإنجازات في عالم الفروسية 🐎\n\n"
                + "Dear " + user.getFullName() + ",\n"
                + "Welcome to Saheel Platform!\n"
                + "We hope you enjoy a great experience full of achievements in the world of horsemanship 🐎";

        whatsAppNotifications.sendHelloMessage(user.getPhoneNumber(), whatsappMessage);        // Set the user and save it.
        user.setCustomer(customer);
        userRepository.save(user);
    }

    public void updateCustomer(Integer userId, CustomerDTO customerDtoIn) {
        if (userRepository.existsByUsername(customerDtoIn.getUsername())) {
            throw new ApiException("This Username is already in use");
        }

        if (userRepository.existsByEmail(customerDtoIn.getEmail())) {
            throw new ApiException("This Email is already in use");
        }

        if (userRepository.existsByPhoneNumber(customerDtoIn.getPhoneNumber())) {
            throw new ApiException("This Phone Number is already in use");
        }
        // Get the user and check if it's in the database.
        User user = helperService.getCustomerOrThrow(userId);

        // Update
        user.setPassword(new BCryptPasswordEncoder().encode(customerDtoIn.getPassword()));


        if (userRepository.existsByUsername(customerDtoIn.getUsername())) {
            throw new ApiException("This Username is already in use");
        }
        if (userRepository.existsByEmail(customerDtoIn.getEmail())) {
            throw new ApiException("This email is already in use");
        }
        if (userRepository.existsByPhoneNumber(customerDtoIn.getPhoneNumber())) {
            throw new ApiException("This PhoneNumber is already in use");
        }

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

    private void sendWelcomeEmail(User user) {
        String to = user.getEmail();
        String subject = "Welcome to Saheel / مرحبًا بك في منصة صهيل\"";
        String body = "أهلاً بك " + user.getFullName() + " في منصة صهيل!\n"
                + "نتمنى لك تجربة ممتعة ومليئة بالإنجازات في عالم الفروسية 🐎\n\n"

                + "Dear " + user.getFullName() + ",\n"
                + "Welcome to Saheel Platform!\n"
                + "We hope you enjoy a great experience full of achievements in the world of horsemanship 🐎";

        emailService.sendEmail(to, subject, body);
    }
}