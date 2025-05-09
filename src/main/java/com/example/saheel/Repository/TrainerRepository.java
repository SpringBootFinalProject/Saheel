package com.example.saheel.Repository;

import com.example.saheel.Model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer  ,Integer> {

    Trainer findTrainerByFullName(String fullName);

    List<Trainer> findTrainersByIsActiveTrue();

    Trainer findTrainerById(Integer trainer_Id);

}
