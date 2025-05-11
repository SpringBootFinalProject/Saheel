package com.example.saheel.Repository;

import com.example.saheel.Model.HorseOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorseOwnerRepository extends JpaRepository<HorseOwner, Integer> {
    HorseOwner findHorseOwnerById(Integer horseOwnerId);
    @Query("SELECT h FROM HorseOwner h WHERE SIZE(h.horses) = (SELECT MAX(SIZE(h2.horses)) FROM HorseOwner h2)")
    List<HorseOwner> findHorseOwnersWithMostHorses();

}
