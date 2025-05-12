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
    private final UserRepository userRepository;

    // get Veterinary by ID - Abeer
    public Veterinary getVeterinaryById(Integer stableOwner_Id, Integer veterinary_Id){
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (stableOwner == null) {
            throw new ApiException("Error : stable owner is not found");
        }

        Veterinary veterinary = veterinaryRepository.findVeterinaryById(veterinary_Id);
        if (veterinary == null){
            throw new ApiException("Error : Veterinary is not found");
        }

        return veterinary;
    }



    //( #38 of 50 endpoints)
    //search Veterinary By Name
    public Veterinary searchVeterinaryByName(Integer stableOwner_Id ,String fullName){

        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (stableOwner == null) {
            throw new ApiException("Error : stable owner is not found");
        }
        if (!Boolean.TRUE.equals(stableOwner.getIsApproved())) {
            throw new ApiException("Your account is not approved. Please wait for admin approval.");
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
        if (userRepository.existsByEmail(veterinary.getEmail())) {
            throw new ApiException("This email is already in use");
        }


        veterinary.setStable(stable);
        veterinaryRepository.save(veterinary);
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


        oldVeterinary.setFullName(veterinary.getFullName());
        oldVeterinary.setAge(veterinary.getAge());
        if (userRepository.existsByEmail(oldVeterinary.getEmail())) {
            throw new ApiException("This email is already in use");
        }
        oldVeterinary.setEmail(veterinary.getEmail());
        oldVeterinary.setYearsOfExperience(veterinary.getYearsOfExperience());

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
