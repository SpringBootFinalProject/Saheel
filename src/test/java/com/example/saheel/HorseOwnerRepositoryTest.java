package com.example.saheel;

import com.example.saheel.Model.HorseOwner;
import com.example.saheel.Model.User;
import com.example.saheel.Repository.HorseOwnerRepository;
import com.example.saheel.Repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HorseOwnerRepositoryTest {

    @Autowired
    private HorseOwnerRepository horseOwnerRepository;

    @Autowired
    private UserRepository userRepository;

    private HorseOwner owner;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("ownerTest");
        user.setPassword("Pass1234");
        user.setRole("HORSEOWNER");
        user.setFullName("Test Owner");
        user.setAge(30);
        user.setEmail("owner@example.com");
        user.setPhoneNumber("+966500000000");
        userRepository.save(user);

        owner = new HorseOwner();
        owner.setUser(user);

        horseOwnerRepository.save(owner);
    }

    @Test
    public void testFindHorseOwnerById() {
        HorseOwner found = horseOwnerRepository.findHorseOwnerById(owner.getId());
        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.getUser().getUsername()).isEqualTo("ownerTest");
    }
}
