package com.example.saheel.Repository;

import com.example.saheel.Model.Veterinary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeterinaryRepository extends JpaRepository<Veterinary , Integer> {

   Veterinary findVeterinaryByFullName(String fullName);

    Veterinary findVeterinaryById(Integer veterinary_Id);

}
