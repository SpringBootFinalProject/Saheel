package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.Stable;
import com.example.saheel.Model.User;
import com.example.saheel.Service.StableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/stable")
@RequiredArgsConstructor
public class StableController {

    private final StableService stableService;

    // Get All stable - Abeer
    @GetMapping("/get-all-stable")
    public ResponseEntity<List<Stable>> getAllStable() {
        return ResponseEntity.ok().body(stableService.getOwnerHorses());
    }

    // Get stable by ID - Abeer
    @GetMapping("/get-my-stable/{stable_Id}")
    public ResponseEntity<Stable> getStableById(@PathVariable Integer stable_Id) {
        Stable stable = stableService.getStableById(stable_Id);
        return ResponseEntity.ok(stable);
    }

    // Add new stable - Abeer
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addStable(@AuthenticationPrincipal User user , @RequestBody Stable stable) {
        stableService.addStable(user, stable);
        return ResponseEntity.ok(new ApiResponse("Stable added successfully"));
    }

    // Update stable - Abeer
    @PutMapping("/update/{stable_Id}")
    public ResponseEntity<ApiResponse> updateStable(@AuthenticationPrincipal User user, @PathVariable Integer stable_Id, @RequestBody Stable stable) {
        stableService.updateStable(user.getId(), stable_Id, stable);
        return ResponseEntity.ok(new ApiResponse("Stable updated successfully"));
    }

    // Delete stable - Abeer
    @DeleteMapping("/delete/{stable_Id}")
    public ResponseEntity<ApiResponse> deleteStable( @AuthenticationPrincipal User user , @PathVariable Integer stable_Id) {
        stableService.deleteStable(user.getId(), stable_Id);
        return ResponseEntity.ok(new ApiResponse("Stable deleted successfully"));
    }

}
