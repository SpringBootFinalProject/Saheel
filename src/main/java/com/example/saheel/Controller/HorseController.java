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

    // ( #24 of 50 endpoints ) Ayman
    @GetMapping("/get-owner-horses")
    public ResponseEntity<List<Horse>> getOwnerHorses(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(horseService.getOwnerHorses(user.getId()));
    }

    @PostMapping("/add-horse-by-owner")
    public ResponseEntity<?> addHorseByOwner(@AuthenticationPrincipal User user, @RequestBody Horse horse) {
        horseService.addHorseByOwner(user.getId(), horse);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Horse added successfully."));
    }

    // ( #25 of 50 endpoints ) Abrar
    @PostMapping("/assign/{horseId}")
    public ResponseEntity<?> assignHorseToMembership(@AuthenticationPrincipal User user, @PathVariable Integer horseId) {
        horseService.assignHorseToMembership(horseId, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Horse assigned successfully"));
    }


    @PutMapping("/update-horse/{horseId}")
    public ResponseEntity<?> updateHorse(@AuthenticationPrincipal User user, @RequestBody Horse horse, @PathVariable Integer horseId) {
        horseService.updateHorse(user.getId(), horseId, horse);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Horse updated successfully."));
    }

    @DeleteMapping("/delete-horse/{horseId}")
    public ResponseEntity<?> removeHorse(@AuthenticationPrincipal User user, @PathVariable Integer horseId) {
        horseService.deleteHorse(user.getId(), horseId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Horse deleted successfully."));
    }

    // ( #26 of 50 endpoints ) Abrar
    // This method gets all horses for one specific owner
    // that do not have a membership.
    @GetMapping("/owner/horses-without-membership")
    public ResponseEntity<List<Horse>> getHorsesWithoutMembership(@AuthenticationPrincipal User user) {
        List<Horse> horses = horseService.getHorsesWithoutMembershipByOwner(user.getId());
        return ResponseEntity.ok(horses);
    }

    // ( #27 of 50 endpoints ) Abrar
    // This method sends (gifts) a horse to a new owner.
    // The horse must have an active membership to be gifted.
    @PutMapping("/gift/{horseId}/to/{newOwnerId}")
    public ResponseEntity<?> giftHorse(@AuthenticationPrincipal User user,
                                       @PathVariable Integer horseId,
                                       @PathVariable Integer newOwnerId) {
        horseService.giftHorseToOwner(user.getId(), horseId, newOwnerId);
        return ResponseEntity.ok(new ApiResponse("Horse gifted successfully."));
    }

}

