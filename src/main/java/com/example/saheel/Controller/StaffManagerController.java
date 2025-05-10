package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.Horse;
import com.example.saheel.Model.Trainer;
import com.example.saheel.Model.User;
import com.example.saheel.Repository.HorseRepository;
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
    private final HorseRepository horseRepository;


    //move Trainer To Stable by stable owner - Abeer
    @PostMapping("/moveTrainer/{trainer_Id}/ToStable/{stable_Id}")
    public ResponseEntity<ApiResponse> moveTrainerToAnotherStable(@AuthenticationPrincipal User user , @PathVariable Integer stable_Id, @PathVariable Integer trainer_Id) {
        staffManagerService.moveTrainerToAnotherStable(user.getId(), stable_Id,trainer_Id);
        return ResponseEntity.ok(new ApiResponse("Trainer assign successfully"));
    }

    //move breeder To Stable by stable owner - Abeer
    @PostMapping("/moveBreeder/{breeder_Id}/ToStable/{stable_Id}")
    public ResponseEntity<ApiResponse> moveBreederToAnotherStable(@AuthenticationPrincipal User user , @PathVariable Integer breeder_Id, @PathVariable Integer stable_Id) {
        staffManagerService.moveBreederToAnotherStable(user.getId(),breeder_Id, stable_Id);
        return ResponseEntity.ok(new ApiResponse("Breeder assign successfully"));
    }

    // TODO صاحب الاسطبل الل يدخل @AuthenticationPrincipal User user
    //assignBreederToHorse - abeer
    @PutMapping("/assignBreeder/{breeder_Id}/ToHorse/{horse_Id}")
    public ResponseEntity<ApiResponse> assignBreederToHorse(@PathVariable Integer breeder_Id,@PathVariable Integer horse_Id) {
        staffManagerService.assignBreederToHorse(breeder_Id, horse_Id);
        return ResponseEntity.ok(new ApiResponse("Breeder Assign to horse successfully"));
    }
    //move veterinary To Stable by stable owner - Abeer
    @PostMapping("/moveVeterinary/{veterinary_Id}/ToStable/{stable_Id}")
    public ResponseEntity<ApiResponse> moveVeterinaryToAnotherStable(@AuthenticationPrincipal User user , @PathVariable Integer stable_Id, @PathVariable Integer veterinary_Id) {
        staffManagerService.moveVeterinaryToAnotherStable(user.getId(), stable_Id,veterinary_Id);
        return ResponseEntity.ok(new ApiResponse("Trainer assign successfully"));
    }

    //assignVeterinaryToHorse - abeer
    @PutMapping("/assignVeterinary/{veterinary_Id}/ToHorse/{horse_Id}")
    public ResponseEntity<ApiResponse> assignVeterinaryToHorse(@PathVariable Integer veterinary_Id,@PathVariable Integer horse_Id) {
        staffManagerService.assignVeterinaryToHorse(veterinary_Id, horse_Id);
        return ResponseEntity.ok(new ApiResponse("veterinary Assign to horse successfully"));
    }

    // Get Available Trainer - Abeer
    @GetMapping("/getAvailableTrainer")
    public ResponseEntity<List<Trainer>> getTrainerById() {
        List<Trainer> trainers = staffManagerService.getAvailableTrainer();
        return ResponseEntity.ok(trainers);
    }

    // Get all horses to veterinary
    @GetMapping("/allHorseToVeterinary/{veterinary_Id}")
    public ResponseEntity<List<Horse>> getAllHorsesByVeterinary(@PathVariable Integer veterinary_Id) {
        List<Horse> horses = staffManagerService.getHorsesByVeterinary(veterinary_Id);
        return ResponseEntity.ok(horses);
    }

    // Get all horses to breeder
    @GetMapping("/allHorseToBreeder/{breeder_Id}")
    public ResponseEntity<List<Horse>> getAllHorsesByBreeder(@PathVariable Integer breeder_Id) {
        List<Horse> horses = staffManagerService.getHorsesByBreeder(breeder_Id);
        return ResponseEntity.ok(horses);
    }


    @PostMapping("/veterinary/visit/{horse_Id}")
    public ResponseEntity<ApiResponse> createVetVisit(@AuthenticationPrincipal User user,
                                                      @PathVariable Integer horse_Id) {
        String message = staffManagerService.visitToVeterinary(user.getId(), horse_Id);
        return ResponseEntity.ok(new ApiResponse(message));
    }
    //( #42 of 50 endpoints)
    //mark Visit AsCompleted
    @PutMapping("/veterinary/visit/fit/{visit_Id}")
    public ResponseEntity<ApiResponse> markHorseAsFit(@PathVariable Integer visit_Id, @RequestBody String medicalReport) {
        staffManagerService.markVisitAsCompleted(visit_Id, true, medicalReport);
        return ResponseEntity.ok(new ApiResponse("The horse's status has been updated to medically fit," +
                " and the report has been sent to the owner."));
    }
    //( #43 of 50 endpoints)
    //mark Visit AsCompleted
    @PutMapping("/veterinary/visit/unfit/{visit_Id}")
    public ResponseEntity<ApiResponse> markHorseAsUnfit(@PathVariable Integer visit_Id, @RequestBody String medicalReport) {
        staffManagerService.markVisitAsCompleted(visit_Id, false, medicalReport);
        return ResponseEntity.ok(new ApiResponse("The horse's status has been updated to medically unfit," +
                " and the report has been sent to the owner."));
    }





}
