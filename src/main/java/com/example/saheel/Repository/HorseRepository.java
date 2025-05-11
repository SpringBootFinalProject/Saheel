package com.example.saheel.Repository;

import com.example.saheel.Model.Horse;
import com.example.saheel.Model.HorseOwner;
import com.example.saheel.Model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorseRepository extends JpaRepository<Horse, Integer> {
    Horse findHorseById(Integer horseId);

    int countByMembership(Membership membership);
    List<Horse> findAllByMembership(Membership membership);
    List<Horse> findByHorseOwnerIdAndMembershipIsNull(Integer ownerId);

    List<Horse>findHorsesByVeterinaryId(Integer veterinary_Id);
    List<Horse>findHorsesByBreederId(Integer breeder_Id);

}
