package com.example.saheel.Repository;

import com.example.saheel.Model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer  ,Integer> {

    Trainer findTrainerByFullName(String fullName);

    Trainer findTrainerById(Integer trainer_Id);

}
