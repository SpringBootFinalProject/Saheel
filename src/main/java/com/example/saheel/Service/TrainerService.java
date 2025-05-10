package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.StableOwner;
import com.example.saheel.Model.Trainer;
import com.example.saheel.Model.Stable;
import com.example.saheel.Repository.StableOwnerRepository;
import com.example.saheel.Repository.StableRepository;
import com.example.saheel.Repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;

    private final StableOwnerRepository stableOwnerRepository;
    private final StableRepository stableRepository;

    //get Trainer by ID - Abeer
    public Trainer getTrainerById(Integer trainer_Id) {
        Trainer trainer = trainerRepository.findTrainerById(trainer_Id);
        if (trainer == null) {
            throw new ApiException("Error : Trainer is not found");
        }
        return trainer;
    }

    //( #37 of 50 endpoints)
// to search By Trainer Name by stable owner
    public Trainer searchByTrainerName(Integer stableOwner_Id, String fullName) {

        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (stableOwner == null) {
            throw new ApiException("Error : stable owner is not found");
        }

        if (!Boolean.TRUE.equals(stableOwner.getIsApproved())) {
            throw new ApiException("Your account is not approved. Please wait for admin approval.");
        }

        Trainer trainer = trainerRepository.findTrainerByFullName(fullName);
        if (trainer == null) {
            throw new ApiException("Error : Trainer is not found");
        }

        return trainer;
    }

    // add Trainer - Abeer
    public void addTrainer(Integer stableOwner_Id, Integer stable_Id, Trainer trainer) {
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null) {
            throw new ApiException("Error : Stable is not found");
        }

        // Check that this stable belongs to the registered owner.
        if (!stable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Unauthorized error: This stable does not belong to the logged-in stable owner");
        }


        trainerRepository.save(trainer);
    }

    //( # of 50 endpoints)####
    //assign trainer to stable by stable owner - Abeer
    public void assignTrainerToStable(Integer stableOwner_Id, Integer stable_Id, Integer trainer_Id) {

        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null) {
            throw new ApiException ("Error : Stable is not found");
        }

        // Check that this stable belongs to the registered owner.
        if (!stable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Error : You are not the owner of this stable");
        }

        Trainer trainer = trainerRepository.findTrainerById(trainer_Id);
        if (trainer == null) {
            throw  new ApiException("Error : Trainer is not found");
        }

        trainer.setStable(stable);
        trainer.setIsActive(true);
        trainerRepository.save(trainer);
    }


    //update Trainer - Abeer
    public void updateTrainer(Integer stableOwner_Id, Integer stable_Id, Integer trainer_Id, Trainer trainer) {
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null) {
            throw new ApiException("Error : stable is not fond");
        }

        Trainer oldTrainer = trainerRepository.findTrainerById(trainer_Id);
        if (oldTrainer == null) {
            throw new ApiException("Error: Trainer not found");
        }

        if (!stable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Unauthorized: This stable does not belong to the logged-in stable owner");
        }

        oldTrainer.setUsername(trainer.getUsername());
        oldTrainer.setFullName(trainer.getFullName());
        oldTrainer.setAge(trainer.getAge());
        oldTrainer.setEmail(trainer.getEmail());
        oldTrainer.setMembershipNumber(trainer.getMembershipNumber());
        oldTrainer.setSpecialty(trainer.getSpecialty());
        oldTrainer.setYearsOfExperience(trainer.getYearsOfExperience());
        oldTrainer.setRating(trainer.getRating());

        trainerRepository.save(oldTrainer);
    }

    //delete Trainer - Abeer
    public void deleteTrainer(Integer stableOwner_Id, Integer trainer_Id) {
        Trainer trainer = trainerRepository.findTrainerById(trainer_Id);
        if (trainer == null) {
            throw new ApiException("Error: Trainer not found");
        }

        Stable stable = trainer.getStable();
        if (stable == null || !stable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Error: You do not have permission to delete this trainer");
        }

        trainerRepository.delete(trainer);
    }
}
