package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Breeder;
import com.example.saheel.Model.Stable;
import com.example.saheel.Repository.BreederRepository;
import com.example.saheel.Repository.StableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class BreederService {

    private final BreederRepository breederRepository;

    private final StableRepository stableRepository;

    //get Breeder by ID - Abeer
    public Breeder getBreederById(Integer breeder_Id){
        Breeder breeder = breederRepository.findBreederById(breeder_Id);
        if (breeder == null){
            throw new ApiException("Error : breeder is not found");
        }
        return breeder;
    }

    //add Breeder - Abeer
    public void addBreeder(Integer stable_Id , Breeder breeder){
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null){
            throw new ApiException("Error : stable is not fond");
        }
        breeder.setStable(stable);
        breederRepository.save(breeder);
    }
    //update Breeder - Abeer
    public void updateBreeder( Integer stable_Id ,Integer breeder_Id, Breeder breeder ) {
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null){
            throw new ApiException(" Error : stable is not fond");
        }

        Breeder oldBreeder = breederRepository.findBreederById(breeder_Id);
        if (oldBreeder == null) {
            throw new ApiException("Error: Breeder not found");
        }

        oldBreeder.setUsername(breeder.getUsername());
        oldBreeder.setFullName(breeder.getFullName());
        oldBreeder.setAge(breeder.getAge());
        oldBreeder.setEmail(breeder.getEmail());
        oldBreeder.setYearsOfExperience(breeder.getYearsOfExperience());
        oldBreeder.setRating(breeder.getRating());

        breederRepository.save(oldBreeder);
    }

    //delete Breeder - Abeer
    public void deleteBreeder(Integer breeder_Id) {
        Breeder breeder = breederRepository.findBreederById(breeder_Id);
        if (breeder == null) {
            throw new ApiException("Error: Breeder not found");
        }
        breederRepository.delete(breeder);
    }

}
