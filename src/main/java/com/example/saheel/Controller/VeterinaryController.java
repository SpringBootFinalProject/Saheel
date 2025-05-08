package com.example.saheel.Controller;

import com.example.saheel.Model.Veterinary;
import com.example.saheel.Service.VeterinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/veterinary")
@RequiredArgsConstructor
public class VeterinaryController {

    private final VeterinaryService veterinaryService;

    // Get veterinary by ID - Abeer
    @GetMapping("/get/{veterinary_Id}")
    public ResponseEntity<Veterinary> getVeterinaryById(@PathVariable Integer veterinary_Id) {
        Veterinary veterinary = veterinaryService.getVeterinaryById(veterinary_Id);
        return ResponseEntity.ok(veterinary);
    }

    // Add veterinary - Abeer
    @PostMapping("/add/{stable_Id}")
    public ResponseEntity<String> addVeterinary(@PathVariable Integer stable_Id, @RequestBody Veterinary veterinary) {
        veterinaryService.addVeterinary(stable_Id, veterinary);
        return ResponseEntity.ok("Veterinary added successfully");
    }

    // Update veterinary - Abeer
    @PutMapping("/update/{stable_Id}/{veterinary_Id}")
    public ResponseEntity<String> updateVeterinary(@PathVariable Integer stable_Id, @PathVariable Integer veterinary_Id, @RequestBody Veterinary veterinary) {
        veterinaryService.updateVeterinary(stable_Id, veterinary_Id, veterinary);
        return ResponseEntity.ok("Veterinary updated successfully");
    }

    // Delete veterinary - Abeer
    @DeleteMapping("/delete/{veterinary_Id}")
    public ResponseEntity<String> deleteVeterinary(@PathVariable Integer veterinary_Id) {
        veterinaryService.deleteVeterinary(veterinary_Id);
        return ResponseEntity.ok("Veterinary deleted successfully");
    }
}
