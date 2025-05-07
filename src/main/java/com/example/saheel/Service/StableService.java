package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.*;
import com.example.saheel.Repository.StableOwnerRepository;
import com.example.saheel.Repository.StableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StableService {

    private final StableRepository stableRepository;

    private final StableOwnerRepository stableOwnerRepository;


    //get by ID - Abeer
    public Stable getStableById(Integer stable_Id) {
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null) {
            throw new ApiException("Error : stable not found");
        }
        return stable;
    }

    //add stable - Abeer
    public void addStable(Integer stableOwner_Id , Stable stable){
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (stableOwner == null ){
            throw new ApiException("Error : Stable owner is not fond");
        }
        stableRepository.save(stable);
    }




    public void updateStable(Integer stableOwner_Id,Integer stable_Id, Stable stable) {
        StableOwner oldStableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (oldStableOwner == null) {
            throw new ApiException("Error: StableOwner not found");
        }

        Stable oldStable = stableRepository.findStableById(stable_Id);
        if (oldStable == null ){
            throw new ApiException("Error : stable is not found");
        }
        oldStable.setName(stable.getName());





        stableRepository.save(stable);
    }

    public void deleteContainer(Integer stable_Id) {
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null) {
            throw new ApiException(" Error : Stable is not found");
        }

        stableRepository.delete(stable);
    }

}
