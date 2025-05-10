package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Veterinary;
import com.example.saheel.Model.VeterinaryVisit;
import com.example.saheel.Repository.VeterinaryRepository;
import com.example.saheel.Repository.VeterinaryVisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class VeterinaryVisitService {

    private final VeterinaryVisitRepository veterinaryVisitRepository;

    private final VeterinaryRepository veterinaryRepository;

    // get VeterinaryVisit by ID - Abeer
    public VeterinaryVisit getVeterinaryVisitById(Integer veterinaryVisit_id) {
        VeterinaryVisit recycleItem = veterinaryVisitRepository.findVeterinaryVisitById(veterinaryVisit_id);
        if (recycleItem == null) {
            throw new ApiException("Error : Veterinary Visit not found");
        }
        return recycleItem;
    }

    //delete  VeterinaryVisit - Abeer
    public void deleteVeterinaryVisit(Integer veterinaryVisit_Id) {
        VeterinaryVisit veterinaryVisit = veterinaryVisitRepository.findVeterinaryVisitById(veterinaryVisit_Id);
        if (veterinaryVisit == null) {
            throw new ApiException("Error : VeterinaryVisit is not found");
        }
        veterinaryVisitRepository.delete(veterinaryVisit);
    }



}
