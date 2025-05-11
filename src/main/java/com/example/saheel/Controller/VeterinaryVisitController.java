package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.VeterinaryVisit;
import com.example.saheel.Service.VeterinaryVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/saheel/veterinary-visit")
@RequiredArgsConstructor
public class VeterinaryVisitController {
    private final VeterinaryVisitService veterinaryVisitService;

    // Get VeterinaryVisit by ID - Abeer
    @GetMapping("/get/{veterinaryVisit_Id}")
    public ResponseEntity<VeterinaryVisit> getVeterinaryVisitById(@PathVariable Integer veterinaryVisit_Id) {
        VeterinaryVisit visit = veterinaryVisitService.getVeterinaryVisitById(veterinaryVisit_Id);
        return ResponseEntity.ok(visit);
    }

    // Delete VeterinaryVisit - Abeer
    @DeleteMapping("/delete/{veterinaryVisit_Id}")
    public ResponseEntity<ApiResponse> deleteVeterinaryVisit(@PathVariable Integer veterinaryVisit_Id) {
        veterinaryVisitService.deleteVeterinaryVisit(veterinaryVisit_Id);
        return ResponseEntity.ok(new ApiResponse("Veterinary visit deleted successfully"));
    }

}
