package com.example.saheel.Controller;

import com.example.saheel.DTO.StableOwnerDTO;
import com.example.saheel.Model.StableOwner;
import com.example.saheel.Model.User;
import com.example.saheel.Service.StableOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

//test
@RestController
@RequestMapping("/api/v1/stable-owner")
@RequiredArgsConstructor
public class StableOwnerController {

    private final StableOwnerService stableOwnerService;

    // Get stableOwner by ID - Abeer
    @GetMapping("get/{stableOwner_Id}")
    public ResponseEntity<StableOwner> getStableOwnerById(@PathVariable Integer stableOwner_Id) {
        StableOwner stableOwner = stableOwnerService.getStableOwnerById(stableOwner_Id);
        return ResponseEntity.ok(stableOwner);
    }

    // Add new stable owner
    @PostMapping("/add")
    public ResponseEntity<String> addStableOwner(@RequestBody StableOwnerDTO stableOwnerDTO) {
        stableOwnerService.registerStableOwner(stableOwnerDTO);
        return ResponseEntity.ok("Stable owner added successfully");
    }

    //Update stable owner - Abeer
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStableOwner(@AuthenticationPrincipal User user, @RequestBody StableOwnerDTO stableOwnerDTO) {
        //stableOwnerService.updateStableOwner(stableOwner_Id,stableOwnerDTO);
        return ResponseEntity.ok("Stable owner updated successfully");
    }

    // Delete stable owner - Abeer
    @DeleteMapping("/delete/{stableOwner_Id}")
    public ResponseEntity<String> deleteStableOwner(@PathVariable Integer stableOwner_Id) {
        stableOwnerService.deleteStableOwner(stableOwner_Id);
        return ResponseEntity.ok("Stable owner deleted successfully");
    }


}