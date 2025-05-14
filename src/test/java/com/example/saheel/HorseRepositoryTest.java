//package com.example.saheel;
//
//import com.example.saheel.Model.Horse;
//import com.example.saheel.Model.HorseOwner;
//import com.example.saheel.Model.User;
//import com.example.saheel.Repository.HorseOwnerRepository;
//import com.example.saheel.Repository.HorseRepository;
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
//public class HorseRepositoryTest {
//
//    @Autowired
//    private HorseRepository horseRepository;
//
//    @Autowired
//    private HorseOwnerRepository horseOwnerRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private Horse horse;
//
//    @BeforeEach
//    void setUp() {
//        User user = new User();
//        user.setUsername("horseUser");
//        user.setPassword("Horse123");
//        user.setRole("HORSEOWNER");
//        user.setFullName("Horse Owner");
//        user.setEmail("horse@example.com");
//        user.setPhoneNumber("+966500000099");
//        user.setAge(28);
//        userRepository.save(user);
//
//        HorseOwner owner = new HorseOwner();
//        owner.setUser(user);
////        user.setHorseOwner(owner);
//        horseOwnerRepository.save(owner);
//
//        horse = new Horse();
//        horse.setName("Thunder");
//        horse.setGender("male");
//        horse.setWightInKG(450);
//        horse.setHeightInCM(160);
//        horse.setAge(6);
//        horse.setPassportNumber("P987654");
//        horse.setHorseOwner(owner);
//
//        horseRepository.save(horse);
//    }
////
//    @Test
//    public void testFindHorseById() {
//        Horse found = horseRepository.findHorseById(horse.getId());
//        Assertions.assertThat(found).isNotNull();
//        Assertions.assertThat(found.getName()).isEqualTo("Thunder");
//        Assertions.assertThat(found.getHorseOwner().getUser().getUsername()).isEqualTo("horseUser");
//    }
//}
