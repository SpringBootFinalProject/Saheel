package com.example.saheel.Controller;


import com.example.saheel.Api.ApiResponse;
import com.example.saheel.DTO.HorseOwnerDTO;
import com.example.saheel.Model.HorseOwner;
import com.example.saheel.Model.User;
import com.example.saheel.Service.HorseOwnerService;
import com.example.saheel.Service.HorseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/saheel/horse-owner")
@RequiredArgsConstructor
public class HorseOwnerController {
    private final HorseOwnerService horseOwnerService;


    @PostMapping("/register")
    public ResponseEntity<?> registerHorseOwner(@Valid @RequestBody HorseOwnerDTO horseOwnerDTO) {
        horseOwnerService.registerHorseOwner(horseOwnerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Horse owner registered successfully"));
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateHorseOwner(@AuthenticationPrincipal User user, @Valid @RequestBody HorseOwnerDTO horseOwnerDTO) {
        horseOwnerService.updateHorseOwner(user.getId(), horseOwnerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Horse owner updated"));
    }

    // delete HorseOwner
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteHorseOwner(@AuthenticationPrincipal User user) {
        horseOwnerService.deleteHorseOwner(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Horse owner deleted"));
    }

    // ( #18 of 50 endpoints )
    // This method finds the horse owner who owns the most horses.
    // It can return more than one owner if they have the same number of horses.
    @GetMapping("/most-horses")
    public ResponseEntity<List<HorseOwner>> getHorseOwnersWithMostHorses() {
        List<HorseOwner> result = horseOwnerService.getOwnersWithMostHorses();
        return ResponseEntity.ok(result);
    }

}
