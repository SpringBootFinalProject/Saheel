package com.example.saheel.Repository;

import com.example.saheel.Model.Stable;
import com.example.saheel.Model.StableOwner;
import com.example.saheel.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StableOwnerRepository extends JpaRepository<StableOwner , Integer> {

    StableOwner findStableOwnerById(Integer StableOwner_Id);
    StableOwner findStableOwnerByUser(User user);


}
