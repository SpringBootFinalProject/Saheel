//package com.example.saheel;
//
//import com.example.saheel.Model.*;
//import com.example.saheel.Repository.*;
//import com.example.saheel.Service.MembershipService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import com.example.saheel.Advise.AdviseController;
//import com.example.saheel.Api.ApiException;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class MembershipServiceTest {
//
//    @Mock
//    private MembershipRepository membershipRepository;
//
//    @Mock
//    private HorseOwnerRepository horseOwnerRepository;
//
//    @Mock
//    private StableRepository stableRepository;
//
//    @Mock
//    private HorseRepository horseRepository;
//
//    @Mock
//    private MembershipInvoiceRepository membershipInvoiceRepository;
//
//    @InjectMocks
//    private MembershipService membershipService;
//
//    private User user;
//    private HorseOwner horseOwner;
//    private Stable stable;
//    private Membership membership;
//    private Horse horse;
//    private Horse horse2;
//    private List<Horse> horses;
//
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
//        stable = new Stable();
//        stable.setId(1);
//
//        membership = new Membership();
//        membership.setId(1);
//        membership.setStartDate(LocalDate.now());
//        membership.setEndDate(LocalDate.now().plusMonths(1));
//        membership.setIsActive(true);
//        membership.setMembershipType("monthly");
//        membership.setHorseOwner(horseOwner);
//        membership.setStable(stable);
//        stable.setCapacity(30);
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
//    void requestMembership() {
//        when(horseOwnerRepository.findHorseOwnerById(user.getId())).thenReturn(horseOwner);
//        when(stableRepository.findStableById(stable.getId())).thenReturn(stable);
//
//        membershipService.requestMembership(membership, user.getId(), stable.getId());
//
//        verify(membershipRepository, times(3)).save(membership);
//        assertEquals(horseOwner, membership.getHorseOwner());
//        assertEquals(stable, membership.getStable());
//    }
//
//    @Test
//    void cancelMembership() {
//        when(membershipRepository.findMembershipById(membership.getId())).thenReturn(membership);
//        when(horseOwnerRepository.findHorseOwnerById(horseOwner.getId())).thenReturn(horseOwner);
//        when(stableRepository.findStableById(stable.getId())).thenReturn(stable);
//        when(horseRepository.findAllByMembership(membership)).thenReturn(horses);
//
//        membershipService.cancelMembership(user.getId(), 1, 1);
//
//        verify(membershipRepository, times(1)).save(membership);
//    }
//}