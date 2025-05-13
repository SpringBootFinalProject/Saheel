package com.example.saheel.Repository;

import com.example.saheel.Model.Veterinary;
import com.example.saheel.Model.VeterinaryVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface VeterinaryVisitRepository extends JpaRepository<VeterinaryVisit ,Integer> {
    VeterinaryVisit findVeterinaryVisitByVeterinaryAndVisitDateTime(Veterinary veterinary, LocalDateTime dateTime);
    VeterinaryVisit findVeterinaryVisitById(Integer veterinaryVisit_Id);

}
