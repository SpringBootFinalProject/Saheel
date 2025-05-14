package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.DTO.MembershipDTO;
import com.example.saheel.Model.*;
import com.example.saheel.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final HorseOwnerRepository horseOwnerRepository;
    private final StableRepository stableRepository;
    private final HorseRepository horseRepository;
    private final MembershipInvoiceRepository membershipInvoiceRepository;
    private final VeterinaryRepository veterinaryRepository;
    private final EmailService emailService;

    // ( #10 of 50 endpoints )
    // get All Memberships
    public MembershipDTO getOwnerActiveMembership(Integer ownerId) {
        HorseOwner owner = horseOwnerRepository.findHorseOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Horse owner not found");
        }

        Membership membership = membershipRepository.findByHorseOwnerAndIsActive(owner, true);
        if (membership == null) {
            throw new ApiException("No active membership found");
        }

        int horseCount = horseRepository.countByMembership(membership);

        return new MembershipDTO(
                membership.getId(),
                membership.getMembershipType(),
                membership.getPrice(),
                membership.getStartDate(),
                membership.getEndDate(),
                horseCount
        );
    }

    // ( #11 of 50 endpoints )
    // add Membership
    public void requestMembership(Membership membership, Integer ownerId, Integer stableId) {
        HorseOwner owner = horseOwnerRepository.findHorseOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Horse Owner not found");
        }
        Stable stable = stableRepository.findStableById(stableId);
        if (stable == null) {
            throw new ApiException("Stable not found");
        }

        // Check if the stable is full
        if (stable.getMemberships() != null && stable.getMemberships().size() >= stable.getCapacity()) {
            throw new ApiException("Stable capacity exceeded");
        }

        // Set values by membership type
        String type = membership.getMembershipType();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate;
        double price;
        int maxHorses;
        switch (type) {
            case "saheel":
                // Check if the stable is full
                if (stable.getTotalNumberOfHorses() + 3 > stable.getCapacity()) {
                    throw new ApiException("Stable capacity exceeded");
                }
                endDate = startDate.plusMonths(6);
                price = 500;
                stable.setTotalNumberOfHorses(stable.getTotalNumberOfHorses() + 3);
                break;
            case "saheel++":
                // Check if the stable is full
                if (stable.getTotalNumberOfHorses() + 6 > stable.getCapacity()) {
                    throw new ApiException("Stable capacity exceeded");
                }
                endDate = startDate.plusYears(1);
                price = 1000;
                stable.setTotalNumberOfHorses(stable.getTotalNumberOfHorses() + 6);

                break;
            default:
                throw new ApiException("Membership type must be 'saheel' or 'saheel++'");
        }

        // Set membership data
        membership.setHorseOwner(owner);
        membership.setStable(stable);
        membership.setStartDate(startDate);
        membership.setEndDate(endDate);
        membership.setPrice(price);
        membership.setIsActive(true);

        membershipRepository.save(membership);

        membershipRepository.save(membership);
        createMembershipInvoice(owner, membership);

    }


    // ( #12 of 50 endpoints )
    // update Membership
    public void renewMembership(Integer ownerId, Membership updatedMembership, Integer membershipId) {
        HorseOwner owner = horseOwnerRepository.findHorseOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Horse Owner not found");
        }


        Membership membership = membershipRepository.findMembershipById(membershipId);
        if (membership == null) {
            throw new ApiException("Membership not found");
        }
        Stable stable = membership.getStable();
        String type = updatedMembership.getMembershipType();
        if (type == null || (!type.equalsIgnoreCase("saheel") && !type.equalsIgnoreCase("saheel++"))) {
            throw new ApiException("Invalid membership type");
        }
        LocalDate startDate = membership.getEndDate();
        LocalDate endDate;
        double price;

        switch (type.toLowerCase()) {
            case "saheel":
                if (membership.getMembershipType().equalsIgnoreCase("saheel"))
                    stable.setCapacity(stable.getCapacity() - 3);
                endDate = startDate.plusMonths(6);
                price = 1;
                break;
            case "saheel++":
                if (membership.getMembershipType().equalsIgnoreCase("saheel++"))
                    stable.setCapacity(stable.getCapacity() + 3);

                endDate = startDate.plusYears(1);
                price = 1000;
                break;
            default:
                throw new ApiException("Invalid membership type");
        }

        membership.setMembershipType(type);
        membership.setStartDate(startDate);
        membership.setEndDate(endDate);
        membership.setPrice(price);
        membership.setIsActive(true);

        membershipRepository.save(membership);


    }


    // ( #13 of 50 endpoints )
    // Cancel Membership
    public void cancelMembership(Integer ownerId, Integer id, Integer stableId) {
        HorseOwner owner = horseOwnerRepository.findHorseOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Horse Owner not found");
        }
        Stable stable = stableRepository.findStableById(stableId);
        if (stable == null) {
            throw new RuntimeException("Stable  not found");
        }

        Membership membership = membershipRepository.findMembershipById(id);
        if (membership == null) {
            throw new RuntimeException("Membership  not found");
        }

        //  Unlink all horses from this membership
//        List<Horse> horses = horseRepository.findAllByMembership(membership);
//        for (Horse horse : horses) {
//            horse.setMembership(null);
//            horse.setVeterinary(null);
//            horse.setBreeder(null);
//            horseRepository.save(horse);
//        }

        if (membership.getMembershipType().equalsIgnoreCase("saheel++"))
            stable.setCapacity(stable.getCapacity() - 6);
        else stable.setCapacity(stable.getCapacity() - 3);

        // Deactivate the membership
        membership.setIsActive(false);
        membershipRepository.save(membership);

        User user = owner.getUser();
        if (user != null && user.getEmail() != null) {
            String subject = "تم إلغاء اشتراكك في صهيل / Membership Cancelled";
            String body = "مرحبًا " + user.getFullName() + ",\n\n"
                    + "تم إلغاء اشتراكك في منصة صهيل. ستظل خدمات الاشتراك فعّالة حتى تاريخ: " + membership.getEndDate() + ".\n\n"
                    + "Dear " + user.getFullName() + ",\n"
                    + "Your membership has been cancelled. Services remain active until: " + membership.getEndDate() + ".\n\n"
                    + "شكراً لاستخدامك صهيل.\nThank you for using Saheel.";

            emailService.sendEmail(user.getEmail(), subject, body);
        }
    }


    // ( #21 of 50 endpoints)
    // This method gets all memberships that are expired.
    // A membership is expired if its end date is before today.
    public List<Membership> getExpiredMemberships() {
        LocalDate today = LocalDate.now();
        return membershipRepository.findByEndDateBefore(today);
    }

    public void createMembershipInvoice(HorseOwner owner, Membership membership) {
        double price = membership.getPrice();
        MembershipInvoice invoice = new MembershipInvoice();
        invoice.setHorseOwner(owner);
        invoice.setMembership(membership);
        invoice.setTotalPrice(price);
        invoice.setPaymentId("No Payment From The Owner");
        invoice.setStatus("pending");
        invoice.setDateTime(LocalDateTime.now());

        membershipInvoiceRepository.save(invoice);

        membership.setInvoice(invoice);
        membershipRepository.save(membership);
    }


}
