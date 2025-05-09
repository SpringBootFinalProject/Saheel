package com.example.saheel.Controller;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Trainer;
import com.example.saheel.Model.User;
import com.example.saheel.Service.TrainerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trainer")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;

    // Get trainer by ID - Abeer
    @GetMapping("/get/{trainer_Id}")
    public ResponseEntity<Trainer> getTrainerById(@PathVariable Integer trainer_Id) {
        Trainer trainer = trainerService.getTrainerById(trainer_Id);
        return ResponseEntity.ok(trainer);
    }

    // Add trainer - Abeer
    @PostMapping("/add-trainer-to-stable/{stable_Id}")
    public ResponseEntity<ApiException> addTrainer(@AuthenticationPrincipal User user , @PathVariable Integer stable_Id, @RequestBody Trainer trainer) {
        trainerService.addTrainer(user.getId(), stable_Id, trainer);
        return ResponseEntity.ok(new ApiException("Trainer added successfully"));
    }

    //assign Trainer To Stable by stable owner - Abeer
    @PostMapping("/assignTrainer/{trainer_Id}/ToStable/{stable_Id}")
    public ResponseEntity<ApiException> assignTrainerToStable(@AuthenticationPrincipal User user , @PathVariable Integer stable_Id, @PathVariable Integer trainer_Id) {
        trainerService.assignTrainerToStable(user.getId(), stable_Id,trainer_Id);
        return ResponseEntity.ok(new ApiException("Trainer assign successfully"));
    }

    // Update trainer - Abeer
    @PutMapping("/update-trainer/{trainer_Id}/by-stable/{stable_Id}")
    public ResponseEntity<ApiException> updateTrainer( @AuthenticationPrincipal User user , @PathVariable Integer stable_Id, @PathVariable Integer trainer_Id, @RequestBody Trainer trainer) {
        trainerService.updateTrainer(user.getId(), stable_Id, trainer_Id, trainer);
        return ResponseEntity.ok(new ApiException("Trainer updated successfully"));
    }

    // Delete trainer - Abeer
    @DeleteMapping("/delete-trainer/{trainer_Id}")
    public ResponseEntity<ApiException> deleteTrainer(@AuthenticationPrincipal User user, @PathVariable Integer trainer_Id) {
        trainerService.deleteTrainer(user.getId(), trainer_Id);
        return ResponseEntity.ok(new ApiException("Trainer deleted successfully"));
    }
}
