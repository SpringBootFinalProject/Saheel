package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.DTO.StableOwnerDTO;
import com.example.saheel.Model.StableOwner;
import com.example.saheel.Repository.StableOwnerRepository;
import com.example.saheel.Repository.StableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StableOwnerService {

        private final StableOwnerRepository stableOwnerRepository;
        private final StableRepository stableRepository;

        // get StableOwner by ID - Abeer
        public StableOwner getStableOwnerById(Integer stableOwner_Id){
                StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
                if (stableOwner == null){
                        throw new ApiException("Error : StableOwner is not found");
                }

                return stableOwner;
        }

        //add stableOwner
        public void addStableOwner (StableOwnerDTO stableOwnerDTO) {
//                stableOwnerRepository.save(stableOwnerDTO);
        }

        //update StableOwner - Abeer
//        public void updateStableOwner(Integer stableOwner_Id, StableOwnerDTO stableOwnerDTO) {
//                StableOwner oldStableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
//                if (oldStableOwner == null) {
//                        throw new ApiException("Error: StableOwner not found");
//                }
//                oldStableOwner.s(stable.getName());
//               ;
//                stableOwnerRepository.save(stable);
//        }

        //delete stable
        public void deleteStableOwner(Integer stableOwner_Id) {
                StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
                if (stableOwner == null) {
                        throw new ApiException(" Error : Stable is not found");
                }
                stableOwnerRepository.delete(stableOwner);
        }







}
