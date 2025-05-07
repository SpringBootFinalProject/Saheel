package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.HorseOwner;
import com.example.saheel.Model.Membership;
import com.example.saheel.Repository.HorseOwnerRepository;
import com.example.saheel.Repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final HorseOwnerRepository horseOwnerRepository;

    // get All Memberships
    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    // add Membership
    public void addMembership(Membership membership, Integer ownerId) {
        HorseOwner owner = horseOwnerRepository.findHorseOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Horse Owner not found");
        }

        if (membership.getMembershipType() == null) {
            throw new ApiException("Membership type is required");
        }

        LocalDate startDate = LocalDate.now();
        LocalDate endDate;
        double price;

        switch (membership.getMembershipType().toLowerCase()) {
            case "monthly":
                endDate = startDate.plusMonths(6);
                price = 500;
                break;
            case "yearly":
                endDate = startDate.plusYears(1);
                price = 1000;
                break;
            default:
                throw new ApiException("must be 'monthly' or 'yearly'");
        }

        membership.setHorseOwner(owner);
        membership.setStartDate(startDate);
        membership.setEndDate(endDate);
        membership.setPrice(price);
        membership.setIsActive(true);
        membershipRepository.save(membership);
    }


    // update Membership
    public void updateMembership(Integer id, Membership newMembership) {
        // Get existing membership
        Membership oldMembership = membershipRepository.findMembershipById(id);
        if (oldMembership == null) {
            throw new ApiException("Membership not found");
        }

        // Validate type
        String type = newMembership.getMembershipType();
        if (type == null) {
            throw new ApiException("Membership type is required");
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

        // Apply updates
        oldMembership.setMembershipType(type);
        oldMembership.setStartDate(startDate);
        oldMembership.setEndDate(endDate);
        oldMembership.setPrice(price);
        oldMembership.setIsActive(true);

        membershipRepository.save(oldMembership);
    }


    // delete Membership
    public void deleteMembership(Integer id) {
        // Get the membership and check
        Membership membership = membershipRepository.findMembershipById(id);
        if (membership == null) {
            throw new RuntimeException("Membership  not found");
        }
        // Delete the membership.
        membershipRepository.delete(membership);
    }


}
