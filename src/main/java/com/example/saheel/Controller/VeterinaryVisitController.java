package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.User;
import com.example.saheel.Model.VeterinaryVisit;
import com.example.saheel.Service.VeterinaryVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/veterinary-visit")
@RequiredArgsConstructor
public class VeterinaryVisitController {
    private final VeterinaryVisitService veterinaryVisitService;

    // Get VeterinaryVisit by ID - Abeer
    @GetMapping("/get/{veterinaryVisit_Id}")
    public ResponseEntity<VeterinaryVisit> getVeterinaryVisitById(@AuthenticationPrincipal User user, @PathVariable Integer veterinaryVisit_Id) {
        VeterinaryVisit visit = veterinaryVisitService.getVeterinaryVisitById(user.getId(), veterinaryVisit_Id);
        return ResponseEntity.ok(visit);
    }


    // Delete VeterinaryVisit - Abeer
    @DeleteMapping("/delete/{veterinaryVisit_Id}")
    public ResponseEntity<ApiResponse> deleteVeterinaryVisit(@AuthenticationPrincipal User user, @PathVariable Integer veterinaryVisit_Id) {
        veterinaryVisitService.deleteVeterinaryVisit(user.getId(), veterinaryVisit_Id);
        return ResponseEntity.ok(new ApiResponse("Veterinary visit deleted successfully"));
    }

}
