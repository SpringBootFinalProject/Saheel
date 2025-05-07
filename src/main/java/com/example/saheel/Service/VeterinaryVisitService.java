package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Veterinary;
import com.example.saheel.Model.VeterinaryVisit;
import com.example.saheel.Repository.VeterinaryRepository;
import com.example.saheel.Repository.VeterinaryVisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class VeterinaryVisitService {

    private final VeterinaryVisitRepository veterinaryVisitRepository;

    private final VeterinaryRepository veterinaryRepository;

    // get by ID - Abeer
    public VeterinaryVisit getVeterinaryVisitById(Integer veterinaryVisit_id) {
        VeterinaryVisit recycleItem = veterinaryVisitRepository.findVeterinaryVisitById(veterinaryVisit_id);
        if (recycleItem == null) {
            throw new ApiException(" Error : Veterinary Visit not found");
        }
        return recycleItem;
    }

    // add VeterinaryVisit - Abeer
    public void addVeterinaryVisit(Integer veterinary_Id, VeterinaryVisit veterinaryVisit){
        Veterinary veterinary = veterinaryRepository.findVeterinaryById(veterinary_Id);
        if (veterinary == null){
            throw new ApiException(" Error : Veterinary is not found");
        }
        veterinaryVisitRepository.save(veterinary);

    }


}
