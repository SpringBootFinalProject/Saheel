package com.example.saheel.Controller;

import com.example.saheel.Model.Stable;
import com.example.saheel.Service.StableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/stable")
@RequiredArgsConstructor
public class StableController {

    private final StableService stableService;

    // Get stable by ID - Abeer
    @GetMapping("/get/{stable_Id}")
    public ResponseEntity<Stable> getStableById(@PathVariable Integer stable_Id) {
        Stable stable = stableService.getStableById(stable_Id);
        return ResponseEntity.ok(stable);
    }

    // Add new stable - Abeer
    @PostMapping("/add/{stableOwner_Id}")
    public ResponseEntity<String> addStable(@PathVariable Integer stableOwner_Id, @RequestBody Stable stable) {
        stableService.addStable(stableOwner_Id, stable);
        return ResponseEntity.ok("Stable added successfully");
    }

    // Update stable - Abeer
    @PutMapping("/update/{stableOwner_Id}/{stable_Id}")
    public ResponseEntity<String> updateStable(@PathVariable Integer stableOwner_Id, @PathVariable Integer stable_Id, @RequestBody Stable stable) {
        stableService.updateStable(stableOwner_Id, stable_Id, stable);
        return ResponseEntity.ok("Stable updated successfully");
    }

    // Delete stable - Abeer
    @DeleteMapping("/delete/{stable_Id}")
    public ResponseEntity<String> deleteStable(@PathVariable Integer stable_Id) {
        stableService.deleteStable(stable_Id);
        return ResponseEntity.ok("Stable deleted successfully");
    }

}
