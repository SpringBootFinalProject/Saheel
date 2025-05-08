package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Trainer;
import com.example.saheel.Model.Stable;
import com.example.saheel.Repository.StableRepository;
import com.example.saheel.Repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private TrainerRepository trainerRepository;

    private StableRepository stableRepository;

    //get Trainer by ID - Abeer
    public Trainer getTrainerById(Integer trainer_Id){
        Trainer trainer = trainerRepository.findTrainerById(trainer_Id);
        if (trainer == null){
            throw new ApiException("Error : Trainer is not found");
        }
        return trainer;
    }

    // add Trainer - Abeer
    public void addTrainer(Integer stable_Id , Trainer trainer){
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null){
            throw new ApiException("Error : stable is not fond");
        }
        trainerRepository.save(trainer);
    }
    //update Trainer - Abeer
    public void updateTrainer( Integer stable_Id ,Integer trainer_Id, Trainer trainer ) {
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null){
            throw new ApiException("Error : stable is not fond");
        }

        Trainer oldTrainer = trainerRepository.findTrainerById(trainer_Id);
        if (oldTrainer == null) {
            throw new ApiException("Error: Trainer not found");
        }

        oldTrainer.setAge(trainer.getAge());
        oldTrainer.setEmail(trainer.getEmail());
        //...
        trainerRepository.save(trainer);
    }

    //delete Trainer - Abeer
    public void deleteTrainer(Integer trainer_Id) {
        Trainer trainer = trainerRepository.findTrainerById(trainer_Id);
        if (trainer == null) {
            throw new ApiException("Error: Trainer not found");
        }
        trainerRepository.delete(trainer);
    }
}
