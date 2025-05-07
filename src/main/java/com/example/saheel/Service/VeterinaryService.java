package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Stable;
import com.example.saheel.Model.Veterinary;
import com.example.saheel.Repository.StableRepository;
import com.example.saheel.Repository.VeterinaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VeterinaryService {

    private final VeterinaryRepository veterinaryRepository;

    private final StableRepository stableRepository;

    // get Veterinary by ID - Abeer
    public Veterinary getVeterinaryById( Integer veterinary_Id){
        Veterinary veterinary = veterinaryRepository.findVeterinaryById(veterinary_Id);
        if (veterinary == null){
            throw new ApiException(" Error : Veterinary is not found");
        }

        return veterinary;
    }

    //add veterinary - Abeer
    public void addVeterinary(Integer stable_Id , Veterinary veterinary){
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null){
            throw new ApiException(" Error : stable is not fond");
        }
        veterinaryRepository.save(veterinary);
    }


}
