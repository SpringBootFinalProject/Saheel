package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.DTO.StableOwnerDTO;
import com.example.saheel.Model.Stable;
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


        public void addStableOwner (StableOwnerDTO stableOwnerDTO) {
                stableOwnerRepository.save(stableOwnerDTO);
        }






}
