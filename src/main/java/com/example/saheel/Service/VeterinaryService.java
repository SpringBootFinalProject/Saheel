package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Stable;
import com.example.saheel.Model.Veterinary;
import com.example.saheel.Repository.StableRepository;
import com.example.saheel.Repository.VeterinaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VeterinaryService {

    private final VeterinaryRepository veterinaryRepository;

    private final StableRepository stableRepository;

    // get Veterinary by ID - Abeer
    public Veterinary getVeterinaryById(Integer veterinary_Id){
        Veterinary veterinary = veterinaryRepository.findVeterinaryById(veterinary_Id);
        if (veterinary == null){
            throw new ApiException("Error : Veterinary is not found");
        }

        return veterinary;
    }

    //add veterinary - Abeer
    public void addVeterinary(Integer stable_Id , Veterinary veterinary){
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null){
            throw new ApiException("Error : stable is not fond");
        }
        veterinary.setStable(stable);
        veterinaryRepository.save(veterinary);
    }

    //update Veterinary - Abeer
    public void updateVeterinary(Integer stable_Id ,Integer veterinary_Id, Veterinary veterinary ) {
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null){
            throw new ApiException("Error : stable is not fond");
        }

        Veterinary oldVeterinary = veterinaryRepository.findVeterinaryById(veterinary_Id);
        if (oldVeterinary == null) {
            throw new ApiException("Error: Veterinary not found");
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
    public void deleteVeterinary(Integer veterinary_Id) {
        Veterinary veterinary = veterinaryRepository.findVeterinaryById(veterinary_Id);
        if (veterinary == null) {
            throw new ApiException("Error: Veterinary not found");
        }
        veterinaryRepository.delete(veterinary);
    }


}
