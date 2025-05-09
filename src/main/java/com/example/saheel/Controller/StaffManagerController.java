package com.example.saheel.Controller;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Trainer;
import com.example.saheel.Model.User;
import com.example.saheel.Service.StaffManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/staff-manager")
@RequiredArgsConstructor
public class StaffManagerController {
    private final StaffManagerService staffManagerService;


    //move Trainer To Stable by stable owner - Abeer
    @PostMapping("/moveTrainer/{trainer_Id}/ToStable/{stable_Id}")
    public ResponseEntity<ApiException> moveTrainerToAnotherStable(@AuthenticationPrincipal User user , @PathVariable Integer stable_Id, @PathVariable Integer trainer_Id) {
        staffManagerService.moveTrainerToAnotherStable(user.getId(), stable_Id,trainer_Id);
        return ResponseEntity.ok(new ApiException("Trainer assign successfully"));
    }

    //move breeder To Stable by stable owner - Abeer
    @PostMapping("/moveBreeder/{breeder_Id}/ToStable/{stable_Id}")
    public ResponseEntity<ApiException> moveBreederToAnotherStable(@AuthenticationPrincipal User user , @PathVariable Integer breeder_Id, @PathVariable Integer stable_Id) {
        staffManagerService.moveBreederToAnotherStable(user.getId(),breeder_Id, stable_Id);
        return ResponseEntity.ok(new ApiException("Breeder assign successfully"));
    }

    //assignBreederToHorse - abeer
    @PutMapping("/assignBreeder/{breeder_Id}/ToHorse/{horse_Id}")
    public ResponseEntity<ApiException> assignBreederToHorse(@PathVariable Integer breeder_Id,@PathVariable Integer horse_Id) {
        staffManagerService.assignBreederToHorse(breeder_Id, horse_Id);
        return ResponseEntity.ok(new ApiException("Breeder Assign to horse successfully"));
    }
    //move veterinary To Stable by stable owner - Abeer
    @PostMapping("/moveVeterinary/{veterinary_Id}/ToStable/{stable_Id}")
    public ResponseEntity<ApiException> moveVeterinaryToAnotherStable(@AuthenticationPrincipal User user , @PathVariable Integer stable_Id, @PathVariable Integer veterinary_Id) {
        staffManagerService.moveVeterinaryToAnotherStable(user.getId(), stable_Id,veterinary_Id);
        return ResponseEntity.ok(new ApiException("Trainer assign successfully"));
    }

    //assignVeterinaryToHorse - abeer
    @PutMapping("/assignVeterinary/{veterinary_Id}/ToHorse/{horse_Id}")
    public ResponseEntity<ApiException> assignVeterinaryToHorse(@PathVariable Integer veterinary_Id,@PathVariable Integer horse_Id) {
        staffManagerService.assignVeterinaryToHorse(veterinary_Id, horse_Id);
        return ResponseEntity.ok(new ApiException("veterinary Assign to horse successfully"));
    }

    // Get Available Trainer - Abeer
    @GetMapping("/getAvailableTrainer")
    public ResponseEntity<List<Trainer>> getTrainerById() {
        List<Trainer> trainers = staffManagerService.getAvailableTrainer();
        return ResponseEntity.ok(trainers);
    }

}
