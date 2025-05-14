package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.Trainer;
import com.example.saheel.Model.User;
import com.example.saheel.Service.StaffManagerService;
import com.example.saheel.Service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/saheel/trainer")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;
    private final StaffManagerService staffManagerService;



    // Get trainer by ID - Abeer
    @GetMapping("/get/{trainer_Id}")
    public ResponseEntity<Trainer> getTrainerById(@AuthenticationPrincipal User user, @PathVariable Integer trainer_Id) {
        Trainer trainer = trainerService.getTrainerById(user.getId(), trainer_Id);
        return ResponseEntity.ok(trainer);
    }

    //( #53 of 50 endpoints) Abeer
    @GetMapping("/search-by-name/{fullName}")
    public ResponseEntity<Trainer> searchTrainerByName(@AuthenticationPrincipal User user, @PathVariable String fullName) {
        Trainer trainer = trainerService.searchByTrainerName(user.getId(),fullName);
        return ResponseEntity.ok(trainer);
    }

    // Add trainer - Abeer
    @PostMapping("/add-trainer-to-stable/{stable_Id}")
    public ResponseEntity<ApiResponse> addTrainer(@AuthenticationPrincipal User user , @PathVariable Integer stable_Id, @RequestBody Trainer trainer) {
        trainerService.addTrainer(user.getId(), stable_Id, trainer);
        return ResponseEntity.ok(new ApiResponse("Trainer added successfully"));
    }



    // Update trainer - Abeer
    @PutMapping("/update-trainer/{trainer_Id}/by-stable/{stable_Id}")
    public ResponseEntity<ApiResponse> updateTrainer( @AuthenticationPrincipal User user , @PathVariable Integer stable_Id, @PathVariable Integer trainer_Id, @RequestBody Trainer trainer) {
        trainerService.updateTrainer(user.getId(), stable_Id, trainer_Id, trainer);
        return ResponseEntity.ok(new ApiResponse("Trainer updated successfully"));
    }

    // Delete trainer - Abeer
    @DeleteMapping("/delete-trainer/{trainer_Id}")
    public ResponseEntity<ApiResponse> deleteTrainer(@AuthenticationPrincipal User user, @PathVariable Integer trainer_Id) {
        trainerService.deleteTrainer(user.getId(), trainer_Id);
        return ResponseEntity.ok(new ApiResponse("Trainer deleted successfully"));
    }

    //( #55 of 50 endpoints) Abrar
    @GetMapping("/get-top-rated-trainer")
    public ResponseEntity<?> getTopRatedTrainer(){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(trainerService.getTopRatedTrainer()));
    }

    //( #56 of 50 endpoints) Ayman
    @GetMapping("/get-top-rated-trainer-of-stable/{stableId}")
    public ResponseEntity<String> getTopRatedTrainerOfStable(@PathVariable Integer stableId){
        return ResponseEntity.status(HttpStatus.OK).body(trainerService.getTopRatedTrainerOfStable(stableId));
    }
}
