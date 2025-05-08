package com.example.saheel.Controller;

import com.example.saheel.DTO.StableOwnerDTO;
import com.example.saheel.Model.StableOwner;
import com.example.saheel.Service.StableOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        stableOwnerService.addStableOwner(stableOwnerDTO);
        return ResponseEntity.ok("Stable owner added successfully");
    }

//    //Update stable owner - Abeer
//    @PutMapping("/update/{id}")
//    public ResponseEntity<String> updateStableOwner(@PathVariable Integer id, @RequestBody StableOwnerDTO stableOwnerDTO) {
//        stableOwnerService.updateStableOwner(id, stableOwnerDTO);
//        return ResponseEntity.ok("Stable owner updated successfully");
//    }

    // Delete stable owner -Abeer
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStableOwner(@PathVariable Integer id) {
        stableOwnerService.deleteStableOwner(id);
        return ResponseEntity.ok("Stable owner deleted successfully");
    }


}