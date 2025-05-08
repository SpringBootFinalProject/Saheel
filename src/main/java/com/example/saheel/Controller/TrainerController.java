package com.example.saheel.Controller;

import com.example.saheel.Model.Trainer;
import com.example.saheel.Service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/add/{stable_Id}")
    public ResponseEntity<String> addTrainer(@PathVariable Integer stable_Id, @RequestBody Trainer trainer) {
        trainerService.addTrainer(stable_Id, trainer);
        return ResponseEntity.ok("Trainer added successfully");
    }

    // Update trainer - Abeer
    @PutMapping("/update/{stable_Id}/{trainer_Id}")
    public ResponseEntity<String> updateTrainer(@PathVariable Integer stable_Id, @PathVariable Integer trainer_Id, @RequestBody Trainer trainer) {
        trainerService.updateTrainer(stable_Id, trainer_Id, trainer);
        return ResponseEntity.ok("Trainer updated successfully");
    }

    // Delete trainer - Abeer
    @DeleteMapping("/delete/{trainer_Id}")
    public ResponseEntity<String> deleteTrainer(@PathVariable Integer trainer_Id) {
        trainerService.deleteTrainer(trainer_Id);
        return ResponseEntity.ok("Trainer deleted successfully");
    }
}
