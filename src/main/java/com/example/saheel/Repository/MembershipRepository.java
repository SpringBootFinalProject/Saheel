package com.example.saheel.Repository;

import com.example.saheel.Model.HorseOwner;
import com.example.saheel.Model.Membership;
import com.example.saheel.Model.Stable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<Membership,Integer> {
    Membership findMembershipById(Integer id);
    Membership findByHorseOwnerAndIsActive(HorseOwner horseOwner, boolean isActive);
    List<Membership> findByEndDateBefore(LocalDate date);
    List<Membership> findByStartDate(LocalDate startDate);

    boolean existsByHorseOwnerAndStable(HorseOwner horseOwner, Stable stable);

    Membership findByHorsesIdAndIsActiveTrue(Integer horse_Id);

}
