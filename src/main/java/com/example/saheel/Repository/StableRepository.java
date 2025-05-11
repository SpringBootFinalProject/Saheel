package com.example.saheel.Repository;

import com.example.saheel.Model.Stable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StableRepository extends JpaRepository<Stable,Integer> {

    Stable findStableByStableOwnerId (Integer stableOwner_Id );
    Stable findStableById(Integer stable_Id);
}
