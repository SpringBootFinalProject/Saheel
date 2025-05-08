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

    //update VeterinaryVisit - Abeer
    public void updateVeterinaryVisit(Integer veterinary_Id,Integer veterinaryVisit_Id, VeterinaryVisit veterinaryVisit) {
        Veterinary oldVeterinary = veterinaryRepository.findVeterinaryById(veterinary_Id);
        if (oldVeterinary == null) {
            throw new ApiException("Error: Veterinary not found");
        }
        VeterinaryVisit oldVeterinaryVisit = veterinaryVisitRepository.findVeterinaryVisitById(veterinaryVisit_Id);
        if (oldVeterinaryVisit == null ){
            throw new ApiException("Error : VeterinaryVisit is not found");
        }
        oldVeterinaryVisit.setDate(veterinaryVisit.getDate());
        oldVeterinaryVisit.setPrice(veterinaryVisit.getPrice());
        oldVeterinaryVisit.setDurationInMinute(veterinaryVisit.getDurationInMinute());
        veterinaryVisitRepository.save(veterinaryVisit);
    }

    //delete  VeterinaryVisit - Abeer
    public void deleteVeterinaryVisit(Integer veterinaryVisit_Id) {
        VeterinaryVisit veterinaryVisit = veterinaryVisitRepository.findVeterinaryVisitById(veterinaryVisit_Id);
        if (veterinaryVisit == null) {
            throw new ApiException(" Error : VeterinaryVisit is not found");
        }
        veterinaryVisitRepository.delete(veterinaryVisit);
    }



}
