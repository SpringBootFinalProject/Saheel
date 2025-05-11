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
    private final HorseRepository horseRepository;
    private final MembershipRepository membershipRepository;

    //get Breeder by ID - Abeer
    public Breeder getBreederById(Integer breeder_Id){
        Breeder breeder = breederRepository.findBreederById(breeder_Id);
        if (breeder == null){
            throw new ApiException("Error : breeder is not found");
        }
        return breeder;
    }


    // ( #29 of 50 endpoints )

    //( #39 of 50 endpoints)
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
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null){
            throw new ApiException("Error : stable is not fond");
        }
        // Check that this stable belongs to the registered owner.
        if (!stable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Unauthorized error: This stable does not belong to the logged-in stable owner");
        }
        breeder.setStable(stable);
        breederRepository.save(breeder);
    }


    // TODO تعديل
    // ( #29 of 50 endpoints )
    //move breeder To Another Stable -Abeer
    public void moveBreederToAnotherStable(Integer stableOwner_Id, Integer breeder_Id, Integer stable_Id) {

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
        oldBreeder.setEmail(breeder.getEmail());
        oldBreeder.setYearsOfExperience(breeder.getYearsOfExperience());
        oldBreeder.setRating(breeder.getRating());

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
