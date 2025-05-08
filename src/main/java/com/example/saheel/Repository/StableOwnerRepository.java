package com.example.saheel.Repository;

import com.example.saheel.Model.StableOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StableOwnerRepository extends JpaRepository<StableOwner , Integer> {

    StableOwner findStableOwnerById(Integer StableOwner_Id);


}
