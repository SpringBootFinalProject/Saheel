package com.example.saheel.Repository;

import com.example.saheel.Model.HorseOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorseOwnerRepository extends JpaRepository<HorseOwner, Integer> {
    HorseOwner findHorseOwnerById(Integer horseOwnerId);
}
