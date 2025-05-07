package com.example.saheel.Repository;

import com.example.saheel.Model.Horse;
import com.example.saheel.Model.HorseOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorseRepository extends JpaRepository<Horse, Integer> {
    Horse findHorseById(Integer horseId);
    List<Horse> findHorseByHorseOwner(HorseOwner horseOwner);
}
