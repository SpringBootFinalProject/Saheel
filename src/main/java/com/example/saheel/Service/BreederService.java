package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.*;
import com.example.saheel.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class BreederService {

    private final BreederRepository breederRepository;
    private final StableOwnerRepository stableOwnerRepository;
    private final StableRepository stableRepository;

    private final UserRepository userRepository;


    //get Breeder by ID - Abeer
    public Breeder getBreederById(Integer stableOwner_Id, Integer breeder_Id){
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (stableOwner == null) {
            throw new ApiException("Error : stable owner is not found");
        }

        Breeder breeder = breederRepository.findBreederById(breeder_Id);
        if (breeder == null){
            throw new ApiException("Error : breeder is not found");
        }
        return breeder;
    }


    // ( #29 of 50 endpoints )
    //search Breeder By Name
    public Breeder searchBreederByName(Integer stableOwner_Id , String fullName){

        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (stableOwner == null) {
            throw new ApiException("Error : stable owner is not found");
        }
        if (!Boolean.TRUE.equals(stableOwner.getIsApproved())) {
            throw new ApiException("Your account is not approved. Please wait for admin approval.");
        }
        Breeder breeder = breederRepository.findBreederByFullName(fullName);
        if (breeder == null){
            throw new ApiException("Error : breeder is not found");
        }
        return breeder;
    }


    //add Breeder - Abeer
    public void addBreeder(Integer stableOwner_Id ,Integer stable_Id , Breeder breeder){
       StableOwner stableOwner= stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (!Boolean.TRUE.equals(stableOwner.getIsApproved())) {
            throw new ApiException("Your account is not approved. Please wait for admin approval.");
        }

        if (userRepository.existsByEmail(breeder.getEmail())) {
            throw new ApiException("This Email is already in use");
        }
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null){
            throw new ApiException("Error : stable is not fond");
        }
        // Check that this stable belongs to the registered owner.
        if (!stable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Unauthorized error: This stable does not belong to the logged-in stable owner");
        }

        if (userRepository.existsByEmail(breeder.getEmail())) {
            throw new ApiException("This email is already in use");
        }

        breeder.setStable(stable);
        breederRepository.save(breeder);
    }

    //update Breeder - Abeer
    public void updateBreeder( Integer stableOwner_Id ,Integer stable_Id ,Integer breeder_Id, Breeder breeder ) {
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null){
            throw new ApiException("Error : stable is not fond");
        }

        Breeder oldBreeder = breederRepository.findBreederById(breeder_Id);
        if (oldBreeder == null) {
            throw new ApiException("Error: Breeder not found");
        }

        // Check that this stable belongs to the registered owner.
        if (!stable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Unauthorized error: This stable does not belong to the logged-in stable owner");
        }

        oldBreeder.setFullName(breeder.getFullName());
        oldBreeder.setAge(breeder.getAge());
        if (userRepository.existsByEmail(oldBreeder.getEmail())) {
            throw new ApiException("This email is already in use");
        }
        oldBreeder.setEmail(breeder.getEmail());
        oldBreeder.setYearsOfExperience(breeder.getYearsOfExperience());

        breederRepository.save(oldBreeder);
    }

    //delete Breeder - Abeer
    public void deleteBreeder(Integer stableOwner_Id ,Integer breeder_Id) {
        Breeder breeder = breederRepository.findBreederById(breeder_Id);
        if (breeder == null) {
            throw new ApiException("Error: Breeder not found");
        }
        Stable stable = breeder.getStable();
        if (stable == null || !stable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Error: You do not have permission to delete this veterinary");
        }
        breeder.setIsActive(false);
        breederRepository.delete(breeder);
    }

}
