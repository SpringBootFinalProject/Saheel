package com.example.saheel.Controller;

import com.example.saheel.Model.Breeder;
import com.example.saheel.Service.BreederService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/breeder")
@RequiredArgsConstructor
public class BreederController {

    private final BreederService breederService;

    // Get breeder by ID - Abeer
    @GetMapping("/get/{id}")
    public ResponseEntity<Breeder> getBreederById(@PathVariable Integer id) {
        Breeder breeder = breederService.getBreederById(id);
        return ResponseEntity.ok(breeder);
    }

    // Add new breeder - Abeer
    @PostMapping("/add/{stable_Id}")
    public ResponseEntity<String> addBreeder(@PathVariable Integer stable_Id, @RequestBody Breeder breeder) {
        breederService.addBreeder(stable_Id, breeder);
        return ResponseEntity.ok("Breeder added successfully");
    }

    // Update breeder - Abeer
    @PutMapping("/update/{stable_Id}/{breeder_Id}")
    public ResponseEntity<String> updateBreeder(@PathVariable Integer stable_Id, @PathVariable Integer breeder_Id, @RequestBody Breeder breeder) {
        breederService.updateBreeder(stable_Id, breeder_Id, breeder);
        return ResponseEntity.ok("Breeder updated successfully");
    }

    // Delete breeder - Abeer
    @DeleteMapping("/delete/{breeder_Id}")
    public ResponseEntity<String> deleteBreeder(@PathVariable Integer breeder_Id) {
        breederService.deleteBreeder(breeder_Id);
        return ResponseEntity.ok("Breeder deleted successfully");
    }


}
