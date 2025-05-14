//package com.example.saheel;
//
//
//import com.example.saheel.Model.User;
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
//public class UserRepositoryTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        user = new User();
//        user.setUsername("abrar123");
//        user.setPassword("Abcd1234");
//        user.setRole("CUSTOMER");
//        user.setFullName("Abrar Saud");
//        user.setAge(27);
//        user.setEmail("abrar@example.com");
//        user.setPhoneNumber("+966512345678");
//
//        userRepository.save(user);
//    }
//
//    @Test
//    public void testFindUserByUsername() {
//        User found = userRepository.findUserByUsername("abrar123");
//        Assertions.assertThat(found).isNotNull();
//        Assertions.assertThat(found.getEmail()).isEqualTo("abrar@example.com");
//    }
//
//
//}
