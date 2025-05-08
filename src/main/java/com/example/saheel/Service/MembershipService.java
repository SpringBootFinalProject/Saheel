package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.DTO.MembershipDTO;
import com.example.saheel.Model.Horse;
import com.example.saheel.Model.HorseOwner;
import com.example.saheel.Model.Membership;
import com.example.saheel.Model.Stable;
import com.example.saheel.Repository.HorseOwnerRepository;
import com.example.saheel.Repository.HorseRepository;
import com.example.saheel.Repository.MembershipRepository;
import com.example.saheel.Repository.StableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final HorseOwnerRepository horseOwnerRepository;
    private final StableRepository stableRepository;
    private final HorseRepository horseRepository;

    //#10
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

    //#11
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
        if (stable.getMemberships().size() >= stable.getCapacity()) {
            throw new ApiException("Stable capacity exceeded");
        }
        // Set values by membership type
        String type = membership.getMembershipType();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate;
        double price;
        int maxHorses;
        switch (type) {
            case "monthly":
                endDate = startDate.plusMonths(6);
                price = 500;
                maxHorses = 3;
                break;
            case "yearly":
                endDate = startDate.plusYears(1);
                price = 1000;
                maxHorses = 6;
                break;
            default:
                throw new ApiException("Membership type must be 'monthly' or 'yearly'");
        }
        // Count how many horses the owner has
        int currentHorseCount = horseRepository.countByHorseOwner(owner);
        if (currentHorseCount > maxHorses) {
            throw new ApiException("Owner already has " + currentHorseCount + " horses, which exceeds the allowed limit for a " + type + " membership.");
        }
        // Set membership data
        membership.setHorseOwner(owner);
        membership.setStable(stable);
        membership.setStartDate(startDate);
        membership.setEndDate(endDate);
        membership.setPrice(price);
        membership.setIsActive(true);

        membershipRepository.save(membership);

    }


    //#12//shen name
    // update Membership
    public void renewMembership( Integer ownerId,Membership updatedMembership, Integer membershipId) {
        HorseOwner owner = horseOwnerRepository.findHorseOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Horse Owner not found");
        }
        Membership membership = membershipRepository.findMembershipById(membershipId);
        if (membership == null) {
            throw new ApiException("Membership not found");
        }
        String type = updatedMembership.getMembershipType();
        if (type == null || (!type.equalsIgnoreCase("monthly") && !type.equalsIgnoreCase("yearly"))) {
            throw new ApiException("Invalid membership type");
        }

        LocalDate startDate = LocalDate.now();
        LocalDate endDate;
        double price;

        switch (type.toLowerCase()) {
            case "monthly":
                endDate = startDate.plusMonths(6);
                price = 500;
                break;
            case "yearly":
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



    //#13
    // delete Membership
    public void deleteMembership(Integer ownerId, Integer id, Integer stableId) {
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
        List<Horse> horses = horseRepository.findAllByMembership(membership);
        for (Horse horse : horses) {
            horse.setMembership(null);
            horseRepository.save(horse);
        }

        // Deactivate the membership
        membership.setIsActive(false);
        membershipRepository.save(membership);
//        membershipRepository.delete(membership);
    }


}
