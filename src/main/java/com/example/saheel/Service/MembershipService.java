package com.example.saheel.Service;

import com.example.saheel.Model.HorseOwner;
import com.example.saheel.Model.Membership;
import com.example.saheel.Repository.HorseOwnerRepository;
import com.example.saheel.Repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    // TODO: Enable @AuthenticationPrincipal User user
    public void addMembership(Membership membership, Integer ownerId) {
        HorseOwner owner = horseOwnerRepository.findHorseOwnerById(ownerId);
        if (owner == null) {
            throw new RuntimeException("Horse Owner not found");
        }
        membership.setHorseOwner(owner);
        membershipRepository.save(membership);
    }

    // update Membership
    public void updateMembership(Integer id, Membership newMembership) {
        Membership oldMembership = membershipRepository.findMembershipById(id);
        if (oldMembership == null) {
            throw new RuntimeException("Membership  not found");
        }
        oldMembership.setPrice(newMembership.getPrice());
        oldMembership.setStable(newMembership.getStable());
        membershipRepository.save(oldMembership);
    }

    // delete Membership
    public void deleteMembership(Integer id) {
        Membership membership = membershipRepository.findMembershipById(id);
        if (membership == null) {
            throw new RuntimeException("Membership  not found");
        }
        membershipRepository.delete(membership);
    }


}
