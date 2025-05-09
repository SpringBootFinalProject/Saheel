package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.*;
import com.example.saheel.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VeterinaryService {

    private final VeterinaryRepository veterinaryRepository;

    private final StableOwnerRepository stableOwnerRepository;
    private final MembershipRepository membershipRepository;
    private final HorseRepository horseRepository;
    private final StableRepository stableRepository;

    // get Veterinary by ID - Abeer
    public Veterinary getVeterinaryById(Integer veterinary_Id){
        Veterinary veterinary = veterinaryRepository.findVeterinaryById(veterinary_Id);
        if (veterinary == null){
            throw new ApiException("Error : Veterinary is not found");
        }

        return veterinary;
    }

    public Veterinary searchVeterinaryByName(Integer stableOwner_Id ,String fullName){

        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (stableOwner == null) {
            throw new ApiException("Error : stable owner is not found");
        }
        Veterinary veterinary = veterinaryRepository.findVeterinaryByFullName(fullName);
        if (veterinary == null){
            throw new ApiException("Error : veterinary is not found");
        }
        return veterinary;
    }

    //add veterinary - Abeer
    public void addVeterinary(Integer stableOwner_Id, Integer stable_Id , Veterinary veterinary){
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null){
            throw new ApiException("Error : stable is not fond");
        }
        // Check that this stable belongs to the registered owner.
        if (!stable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Unauthorized error: This stable does not belong to the logged-in stable owner");
        }
        veterinary.setStable(stable);
        veterinaryRepository.save(veterinary);
    }

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

    public void assignVeterinaryToHorse(Integer veterinary_Id ,Integer horse_Id) {

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

    //update Veterinary - Abeer
    public void updateVeterinary( Integer stableOwner_Id, Integer stable_Id , Integer veterinary_Id, Veterinary veterinary ) {
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null){
            throw new ApiException("Error : stable is not fond");
        }

        Veterinary oldVeterinary = veterinaryRepository.findVeterinaryById(veterinary_Id);
        if (oldVeterinary == null) {
            throw new ApiException("Error: Veterinary not found");
        }
        // Check that this stable belongs to the registered owner.
        if (!stable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Error : You are not the owner of this stable");
        }


        oldVeterinary.setUsername(veterinary.getUsername());
        oldVeterinary.setFullName(veterinary.getFullName());
        oldVeterinary.setAge(veterinary.getAge());
        oldVeterinary.setEmail(veterinary.getEmail());
        oldVeterinary.setYearsOfExperience(veterinary.getYearsOfExperience());
        oldVeterinary.setRating(veterinary.getRating());

        veterinaryRepository.save(veterinary);
    }

    //delete  Veterinary - Abeer
    public void deleteVeterinary(Integer stableOwner_Id, Integer veterinary_Id) {
        Veterinary veterinary = veterinaryRepository.findVeterinaryById(veterinary_Id);
        if (veterinary == null) {
            throw new ApiException("Error: Veterinary not found");
        }
        Stable stable = veterinary.getStable();
        if (stable == null || !stable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Error: You do not have permission to delete this veterinary");
        }

        veterinaryRepository.delete(veterinary);
    }


}
