package com.example.saheel.Repository;

import com.example.saheel.Model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
@Repository
=======
import java.util.List;

>>>>>>> origin/abeerDev
public interface TrainerRepository extends JpaRepository<Trainer  ,Integer> {

    Trainer findTrainerByFullName(String fullName);

    List<Trainer> findTrainersByIsActiveTrue();

    Trainer findTrainerById(Integer trainer_Id);

}
