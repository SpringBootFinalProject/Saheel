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

    // TODO: Enable @AuthenticationPrincipal User user
    @GetMapping("/get-owner-horses")
    public ResponseEntity<List<Horse>> getOwnerHorses(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(horseService.getOwnerHorses(user.getId()));
    }

    // TODO: Enable @AuthenticationPrincipal User user
    @PostMapping("/add-horse-by-owner/{horseOwnerId}")
    public ResponseEntity<ApiResponse> addHorseByOwner(@PathVariable Integer horseOwnerId, @RequestBody Horse horse) {
//        horseService.addHorseByOwner(user.getId(), horse);
        horseService.addHorseByOwner(horseOwnerId, horse);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Horse added successfully."));
    }

    // TODO: Enable @AuthenticationPrincipal User user
    @PutMapping("/update-horse/{horseOwnerId}/{horseId}")
    public ResponseEntity<ApiResponse> updateHorse(@PathVariable Integer horseOwnerId, @RequestBody Horse horse, @PathVariable Integer horseId) {
//        horseService.updateHorse(user.getId(), horseId, horse);
        horseService.updateHorse(horseOwnerId, horseId, horse);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Horse updated successfully."));
    }

    // TODO: Enable @AuthenticationPrincipal User user
    @DeleteMapping("/delete-horse/{horseOwnerId}/{horseId}")
    public ResponseEntity<ApiResponse> removeHorse(@PathVariable Integer horseOwnerId, @PathVariable Integer horseId) {
//        horseService.deleteHorse(user.getId(), horseId);
        horseService.deleteHorse(horseOwnerId, horseId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Horse deleted successfully."));
    }
}

