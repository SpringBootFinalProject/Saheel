//package com.example.saheel;
//
//import com.example.saheel.Model.Horse;
//import com.example.saheel.Model.HorseOwner;
//import com.example.saheel.Model.User;
//import com.example.saheel.Repository.HorseRepository;
//import com.example.saheel.Repository.HorseOwnerRepository;
//import com.example.saheel.Service.HorseService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class HorseServiceTest {
//
//    @Mock
//    private HorseRepository horseRepository;
//
//    @Mock
//    private HorseOwnerRepository horseOwnerRepository;
//
//    @InjectMocks
//    private HorseService horseService;
//
//    private User user;
//    private HorseOwner horseOwner;
//    private Horse horse;
//    private Horse horse2;
//    private List<Horse> horses;
//
//    @BeforeEach
//    void setUp() {
//        user = new User();
//        user.setId(1);
//        user.setRole("HORSEOWNER");
//
//        horseOwner = new HorseOwner();
//        horseOwner.setId(1);
//        horseOwner.setUser(user);
//
//        horse = new Horse();
//        horse.setId(1);
//        horse.setName("cloud");
//        horse.setGender("female");
//        horse.setWightInKG(500);
//        horse.setHeightInCM(160);
//        horse.setAge(5);
//        horse.setPassportNumber("P123456");
//        horse.setHorseOwner(horseOwner);
//
//        horse2 = new Horse();
//        horse.setId(1);
//        horse.setName("Thunder");
//        horse.setGender("male");
//        horse.setWightInKG(500);
//        horse.setHeightInCM(160);
//        horse.setAge(5);
//        horse.setPassportNumber("P123456");
//        horse.setHorseOwner(horseOwner);
//        horses = new ArrayList<>();
//        horses.add(horse);
//        horses.add(horse2);
//    }
//
//    @Test
//    void getOwnerHorses() {
//        when(horseOwnerRepository.findHorseOwnerById(user.getId())).thenReturn(horseOwner);
//        when(horseRepository.findHorsesByHorseOwner(horseOwnerRepository.findHorseOwnerById(user.getId()))).thenReturn(Collections.singletonList(horse));
//
//        List<Horse> result = horseService.getOwnerHorses(user.getId());
//
//        assertEquals(1, result.size());
//        assertEquals("Thunder", result.get(0).getName());
//    }
//
//    @Test
//    void addHorseByOwner() {
//        when(horseOwnerRepository.findHorseOwnerById(user.getId())).thenReturn(horseOwner);
//
//        horseService.addHorseByOwner(user.getId(), horse);
//
//        verify(horseRepository, times(1)).save(horse);
//        assertEquals(horseOwner, horse.getHorseOwner());
//    }
//
//    @Test
//    void getHorsesWithoutMembershipByOwner() {
//        when(horseRepository.findByHorseOwnerIdAndMembershipIsNull(horseOwner.getId()))
//                .thenReturn(horses);
//
//        List<Horse> result = horseService.getHorsesWithoutMembershipByOwner(user.getId());
//
//        assertEquals(2, result.size());
//        assertNull(result.get(0).getMembership());
//    }
//}