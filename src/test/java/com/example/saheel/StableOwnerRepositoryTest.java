//package com.example.saheel;
//
//import com.example.saheel.Model.StableOwner;
//import com.example.saheel.Model.User;
//import com.example.saheel.Repository.StableOwnerRepository;
//import com.example.saheel.Repository.UserRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class StableOwnerRepositoryTest {
//
//    @Autowired
//    private StableOwnerRepository stableOwnerRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private StableOwner stableOwner;
//
//    @BeforeEach
//    void setUp() {
//        User user = new User();
//        user.setUsername("stableUser");
//        user.setPassword("Stable123");
//        user.setRole("STABLEOWNER");
//        user.setFullName("Stable Owner");
//        user.setEmail("stable@example.com");
//        user.setPhoneNumber("+966500000011");
//        user.setAge(30);
//        userRepository.save(user);
//
//        stableOwner = new StableOwner();
//        stableOwner.setUser(user);
//        stableOwnerRepository.save(stableOwner);
//    }
//
//    @Test
//    public void testFindStableOwnerById() {
//        StableOwner found = stableOwnerRepository.findStableOwnerById(stableOwner.getId());
//        Assertions.assertThat(found).isNotNull();
//        Assertions.assertThat(found.getUser().getUsername()).isEqualTo("stableUser");
//    }
//
//
//}
