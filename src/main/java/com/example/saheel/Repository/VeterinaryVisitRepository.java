package com.example.saheel.Repository;

import com.example.saheel.Model.VeterinaryVisit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinaryVisitRepository extends JpaRepository<VeterinaryVisit ,Integer> {

    VeterinaryVisit findVeterinaryVisitById(Integer veterinaryVisit_Id);

}
