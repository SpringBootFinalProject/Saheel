//package com.example.saheel;
//
//import com.example.saheel.Model.Customer;
//import com.example.saheel.Model.User;
//import com.example.saheel.Repository.CustomerRepository;
//import com.example.saheel.Repository.UserRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class CustomerRepositoryTest {
//
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private Customer customer;
//
//    @BeforeEach
//    void setUp() {
//        User user = new User();
//        user.setUsername("abrar123");
//        user.setPassword("Abcd1234");
//        user.setRole("CUSTOMER");
//        user.setFullName("Abrar Saud");
//        user.setAge(27);
//        user.setEmail("abrar@example.com");
//        user.setPhoneNumber("+966512345678");
//
//        userRepository.save(user);
//
//        customer = new Customer();
//        customer.setLevel("beginner");
//        customer.setUser(user);
//        user.setCustomer(customer);
//
//        customerRepository.save(customer);
//    }
//
//    @Test
//    public void testFindCustomerById() {
//        Customer found = customerRepository.findCustomerById(customer.getId());
//        Assertions.assertThat(found).isNotNull();
//        Assertions.assertThat(found.getUser().getUsername()).isEqualTo("abrar123");
//    }
//}
