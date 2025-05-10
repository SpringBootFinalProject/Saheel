package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.*;
import com.example.saheel.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffManagerService {

    private final TrainerRepository trainerRepository;
    private final BreederRepository breederRepository;
    private final VeterinaryRepository veterinaryRepository;
    private final StableRepository stableRepository;
    private final HorseRepository horseRepository;
    private final MembershipRepository membershipRepository;


    //( #30 of 50 endpoints)
    //move breeder To Another Stable -Abeer
    public void moveBreederToAnotherStable(Integer stableOwner_Id, Integer breeder_Id, Integer stable_Id) {

        Breeder breeder = breederRepository.findBreederById(breeder_Id);
        if (breeder == null) {
            throw new ApiException("Error Breeder is not found");
        }

        Stable newStable = stableRepository.findStableById(stable_Id);
        if (newStable == null) {
            throw new ApiException("Target stable not found");
        }

        // Check that this stable belongs to the registered owner.
        if (!newStable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Error : You are not the owner of this stable");
        }

        breeder.setStable(newStable);
        breederRepository.save(breeder);
    }

    //( #31 of 50 endpoints)
//to assignBreederToHorse -Abeer
    public void assignBreederToHorse(Integer breeder_Id, Integer horse_Id) {

        Membership membership = membershipRepository.findByHorsesIdAndIsActiveTrue(horse_Id);
        if (membership == null) {
            throw new ApiException("Error: This horse does not have an active membership");
        }

        Horse horse = horseRepository.findHorseById(horse_Id);
        if (horse == null) {
            throw new ApiException("Error: Horse is not found");
        }

        Breeder breeder = breederRepository.findBreederById(breeder_Id);
        if (breeder == null) {
            throw new ApiException("Error: Breeder not found");
        }

        if (!membership.getStable().getId().equals(breeder.getStable().getId())) {
            throw new ApiException("Error: Breeder and horse must belong to the same stable");
        }

        horse.setBreeder(breeder);
        breeder.setIsActive(true);
        horseRepository.save(horse);
    }


    //( #32 of 50 endpoints)
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

    //( #33 of 50 endpoints)
    //move veterinary To Another Stable -Abeer
    public void moveVeterinaryToAnotherStable(Integer stableOwner_Id, Integer veterinary_Id, Integer stable_Id) {

        Veterinary veterinary = veterinaryRepository.findVeterinaryById(veterinary_Id);
        if (veterinary == null) {
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

        veterinary.setStable(newStable);
        veterinaryRepository.save(veterinary);
    }

    //( #34 of 50 endpoints)
//assign veterinary it horse-Abeer
    public void assignVeterinaryToHorse(Integer veterinary_Id, Integer horse_Id) {

        Membership membership = membershipRepository.findByHorsesIdAndIsActiveTrue(horse_Id);
        if (membership == null) {
            throw new ApiException("Error: This horse does not have an active membership");
        }

        Horse horse = horseRepository.findHorseById(horse_Id);
        if (horse == null) {
            throw new ApiException("Error: Horse is not found");
        }

        Veterinary veterinary = veterinaryRepository.findVeterinaryById(veterinary_Id);
        if (veterinary == null) {
            throw new ApiException("Error: veterinary is not found");
        }

        if (!membership.getStable().getId().equals(veterinary.getStable().getId())) {
            throw new ApiException("Error: Veterinary and horse must belong to the same stable");
        }

        horse.setVeterinary(veterinary);
        horseRepository.save(horse);
    }

    //( #35 of 50 endpoints)

    //get list of trainer is active
    public List<Trainer> getAvailableTrainer() {
        List<Trainer> trainers = trainerRepository.findTrainersByIsActiveTrue();

        if (trainers.isEmpty()) {
            throw new ApiException("Error: No active trainers found");
        }
        return trainers;
    }

    //( #36 of 50 endpoints)
//get list of hours by veterinary
    public List<Horse> getHorsesByVeterinary(Integer veterinary_Id){
        List<Horse> horses = horseRepository.findHorsesByVeterinaryId(veterinary_Id);
        if (horses.isEmpty()) {
            throw new ApiException("Error: no horses to thes veterinary");
        }

        return horses;
    }
}
