package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.Horse;
import com.example.saheel.Model.User;
import com.example.saheel.Service.HorseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/saheel/horse")
public class HorseController {
    private final HorseService horseService;

    @GetMapping("/get-owner-horses")
    public ResponseEntity<List<Horse>> getOwnerHorses(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(horseService.getOwnerHorses(user.getId()));
    }

    @PostMapping("/add-horse-by-owner")
    public ResponseEntity<ApiResponse> addHorseByOwner(@AuthenticationPrincipal User user, @RequestBody Horse horse) {
        horseService.addHorseByOwner(user.getId(), horse);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Horse added successfully."));
    }

    @PutMapping("/update-horse/{horseId}")
    public ResponseEntity<ApiResponse> updateHorse(@AuthenticationPrincipal User user, @RequestBody Horse horse, @PathVariable Integer horseId) {
        horseService.updateHorse(user.getId(), horseId, horse);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Horse updated successfully."));
    }

    @DeleteMapping("/delete-horse/{horseId}")
    public ResponseEntity<ApiResponse> removeHorse(@AuthenticationPrincipal User user, @PathVariable Integer horseId) {
        horseService.deleteHorse(user.getId(), horseId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Horse deleted successfully."));
    }
}

