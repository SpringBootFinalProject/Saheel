package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.*;
import com.example.saheel.Repository.StableOwnerRepository;
import com.example.saheel.Repository.StableRepository;
import com.example.saheel.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StableService {

    private final StableRepository stableRepository;

    private final UserRepository userRepository;
    private final StableOwnerRepository stableOwnerRepository;

    public List<Stable> getOwnerHorses() {
        return stableRepository.findAll();
    }

    //get stable by ID - Abeer
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
        stable.setStableOwner(stableOwner);
        stableRepository.save(stable);
    }

    //update stable - Abeer
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
        oldStable.setCapacity(stable.getCapacity());
        stableRepository.save(stable);
    }

    //delete stable
    public void deleteStable(Integer stableOwner_Id, Integer stable_Id) {
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (stableOwner == null) {
            throw new ApiException("Error: StableOwner not found");
        }

        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null) {
            throw new ApiException("Error : Stable is not found");
        }
        User user = stableOwner.getUser();

        userRepository.delete(user);
        stableRepository.delete(stable);
    }

}
