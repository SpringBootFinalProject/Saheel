package com.example.saheel.Repository;

import com.example.saheel.Model.Veterinary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinaryRepository extends JpaRepository<Veterinary , Integer> {

    Veterinary findVeterinaryById(Integer veterinary_Id);

}
