package com.example.saheel.Repository;

import com.example.saheel.Model.Stable;
import com.example.saheel.Model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer  ,Integer> {

    Trainer findTrainerByFullName(String fullName);

    List<Trainer> findTrainersByIsActiveTrue();

    Trainer findTrainerById(Integer trainer_Id);

    List<Trainer> findTrainerByStable(Stable stable);
}
