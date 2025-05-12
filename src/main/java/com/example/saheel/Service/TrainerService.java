package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.StableOwner;
import com.example.saheel.Model.Trainer;
import com.example.saheel.Model.Stable;
import com.example.saheel.Repository.StableOwnerRepository;
import com.example.saheel.Repository.StableRepository;
import com.example.saheel.Repository.TrainerRepository;
import com.example.saheel.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService {


    private final TrainerRepository trainerRepository;
    private final StableOwnerRepository stableOwnerRepository;
    private final StableRepository stableRepository;
    private final UserRepository userRepository;

    //get Trainer by ID - Abeer
    public Trainer getTrainerById(Integer stableOwner_Id, Integer trainer_Id) {
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (stableOwner == null) {
            throw new ApiException("Error : stable owner is not found");
        }

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
        if (userRepository.existsByEmail(trainer.getEmail())) {
            throw new ApiException("This email is already in use");
        }
        // Check that this stable belongs to the registered owner.
        if (!stable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Unauthorized error: This stable does not belong to the logged-in stable owner");
        }
        trainerRepository.save(trainer);
    }


    // ( #33 of 50 endpoints )
    //assign trainer to stable by stable owner - Abeer
    public void assignTrainerToStable(Integer stableOwner_Id, Integer stable_Id, Integer trainer_Id) {

        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null) {
            throw new ApiException("Error : Stable is not found");
        }

        // Check that this stable belongs to the registered owner.
        if (!stable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Error : You are not the owner of this stable");
        }

        Trainer trainer = trainerRepository.findTrainerById(trainer_Id);
        if (trainer == null) {
            throw new ApiException("Error : Trainer is not found");
        }

        trainer.setStable(stable);
        trainer.setIsActive(true);
        trainerRepository.save(trainer);
    }


    // ( #24 of 50 endpoints )
    //move Trainer To Another Stable -Abeer
    public void moveTrainerToAnotherStable(Integer stableOwner_Id, Integer trainer_Id, Integer stable_Id) {

        Trainer trainer = trainerRepository.findTrainerById(trainer_Id);
        if (trainer == null) {
            throw new ApiException("Error Trainer is not found");
        }

        Stable newStable = stableRepository.findStableById(stable_Id);
        if (newStable == null) {
            throw new ApiException("Target stable not found");
        }


        // Check that this stable belongs to the registered owner.
        if (!newStable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Error : You are not the owner of this stable");
        }

        trainer.setStable(newStable);
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
        if (userRepository.existsByEmail(oldTrainer.getEmail())) {
            throw new ApiException("This email is already in use");
        }

        if (!stable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Unauthorized: This stable does not belong to the logged-in stable owner");
        }

        oldTrainer.setFullName(trainer.getFullName());
        oldTrainer.setAge(trainer.getAge());
        oldTrainer.setEmail(trainer.getEmail());
        oldTrainer.setMembershipNumber(trainer.getMembershipNumber());
        oldTrainer.setYearsOfExperience(trainer.getYearsOfExperience());

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

    public void rateTrainer(Integer userId, Integer trainerId) {
        // Get the trainer and check if it's in the system.
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null) throw new ApiException("Trainer not found.");

        /// TODO : Get the Customer/HorseOwner and do the (تشييك).


    }

    public String getTopRatedTrainer() {
        // Get all the trainers on the system.
        List<Trainer> trainers = trainerRepository.findAll();

        // Check if there is trainers on the system.
        if (trainers.isEmpty()) return "There are no trainers";

        // Get the top-rated trainer.
        Trainer topRatedTrainer = findTopRatedTrainer(trainers);

        // Check if the all the trainers don't have ratings.
        if (topRatedTrainer.getTotalNumberOfRatings() == 0) return "All the trainers do not have a rating.";

        // Return the top-rated trainer.
        return "The top rated trainer is: " + topRatedTrainer.getFullName() + " with a rating of: "
                + (topRatedTrainer.getTotalRating() / topRatedTrainer.getTotalNumberOfRatings()) + ".";
    }

    public String getTopRatedTrainerOfStable(Integer stableId) {
        // Get the stable and check if it's in the database.
        Stable stable = stableRepository.findStableById(stableId);
        if (stable == null) throw new ApiException("Stable not found.");

        // Get all the trainers of the stable and check.
        List<Trainer> trainers = trainerRepository.findTrainerByStable(stable);
        if (trainers.isEmpty()) return "There are no trainers";

        // Get the top-rated trainer.
        Trainer topRatedTrainer = findTopRatedTrainer(trainers);

        // Check if the all the trainers don't have ratings.
        if (topRatedTrainer.getTotalNumberOfRatings() == 0) return "All the trainers do not have a rating.";

        // Return the top-rated trainer.
        return "The top rated trainer at " + stable.getName() + " is: " + topRatedTrainer.getFullName()
                + " with a rating of: " + (topRatedTrainer.getTotalRating() / topRatedTrainer.getTotalNumberOfRatings()) + ".";
    }

    public Trainer findTopRatedTrainer(List<Trainer> trainers) {
        Trainer topRatedTrainer = trainers.get(0);
        boolean flag = false;
        for (Trainer trainer : trainers) {
            // Handle division by 0.
            if (trainer.getTotalNumberOfRatings() == 0) continue;
            if (!flag) {
                topRatedTrainer = trainer;
                flag = true;
            }
            if ((trainer.getTotalRating() / trainer.getTotalNumberOfRatings()) > (topRatedTrainer.getTotalRating() / topRatedTrainer.getTotalNumberOfRatings()))
                topRatedTrainer = trainer;
        }
        return topRatedTrainer;
    }
}
