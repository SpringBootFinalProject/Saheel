package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.VeterinaryVisit;
import com.example.saheel.Service.VeterinaryVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/veterinary-visit")
@RequiredArgsConstructor
public class VeterinaryVisitController {
    private final VeterinaryVisitService veterinaryVisitService;

    // Get VeterinaryVisit by ID - Abeer
    @GetMapping("/get/{veterinaryVisit_Id}")
    public ResponseEntity<VeterinaryVisit> getVeterinaryVisitById(@PathVariable Integer veterinaryVisit_Id) {
        VeterinaryVisit visit = veterinaryVisitService.getVeterinaryVisitById(veterinaryVisit_Id);
        return ResponseEntity.ok(visit);
    }

//    // Add VeterinaryVisit - Abeer
//    @PostMapping("/add/{veterinary_Id}")
//    public ResponseEntity<ApiResponse> addVeterinaryVisit(@PathVariable Integer veterinary_Id, @RequestBody VeterinaryVisit veterinaryVisit) {
//        veterinaryVisitService.addVeterinaryVisit(veterinary_Id, veterinaryVisit);
//        return ResponseEntity.ok(new ApiResponse("Veterinary visit added successfully"));
//    }

    // Update VeterinaryVisit - Abeer
    @PutMapping("/update/{veterinary_Id}/{veterinaryVisit_Id}")
    public ResponseEntity<ApiResponse> updateVeterinaryVisit(@PathVariable Integer veterinary_Id, @PathVariable Integer veterinaryVisit_Id, @RequestBody VeterinaryVisit veterinaryVisit) {
        veterinaryVisitService.updateVeterinaryVisit(veterinary_Id, veterinaryVisit_Id, veterinaryVisit);
        return ResponseEntity.ok(new ApiResponse("Veterinary visit updated successfully"));
    }

    // Delete VeterinaryVisit - Abeer
    @DeleteMapping("/delete/{veterinaryVisit_Id}")
    public ResponseEntity<ApiResponse> deleteVeterinaryVisit(@PathVariable Integer veterinaryVisit_Id) {
        veterinaryVisitService.deleteVeterinaryVisit(veterinaryVisit_Id);
        return ResponseEntity.ok(new ApiResponse("Veterinary visit deleted successfully"));
    }

}
