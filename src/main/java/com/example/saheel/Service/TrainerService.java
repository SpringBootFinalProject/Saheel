package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Trainer;
import com.example.saheel.Model.Stable;
import com.example.saheel.Repository.StableRepository;
import com.example.saheel.Repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
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
            throw new ApiException(" Error : Trainer is not found");
        }
        return trainer;
    }

    // add Trainer - Abeer
    public void addTrainer(Integer stable_Id , Trainer trainer){
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null){
            throw new ApiException(" Error : stable is not fond");
        }
        trainerRepository.save(trainer);
    }
}
