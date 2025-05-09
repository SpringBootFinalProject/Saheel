package com.example.saheel.Repository;

import com.example.saheel.Model.Breeder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreederRepository extends JpaRepository<Breeder, Integer> {

    Breeder findBreederByFullName (String fullName);

    Breeder findBreederById(Integer breeder_Id);


}
