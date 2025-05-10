package com.example.saheel.Controller;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Breeder;
import com.example.saheel.Model.User;
import com.example.saheel.Model.Veterinary;
import com.example.saheel.Service.BreederService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/breeder")
@RequiredArgsConstructor
public class BreederController {

    private final BreederService breederService;

    // Get breeder by ID - Abeer
    @GetMapping("/get/{breeder_Id}")
    public ResponseEntity<Breeder> getBreederById(@PathVariable Integer breeder_Id) {
        Breeder breeder = breederService.getBreederById(breeder_Id);
        return ResponseEntity.ok(breeder);
    }

    //searchVeterinaryByName
    @GetMapping("/search-byName/{fullName}")
    public ResponseEntity<Breeder> searchVeterinaryByName(@AuthenticationPrincipal User user, @PathVariable String fullName) {
        Breeder breeder = breederService.searchBreederByName(user.getId(),fullName);
        return ResponseEntity.ok(breeder);
    }

    // Add new breeder - Abeer
    @PostMapping("/add/{stable_Id}")
    public ResponseEntity<ApiException> addBreeder(@AuthenticationPrincipal User user, @PathVariable Integer stable_Id, @RequestBody Breeder breeder) {
        breederService.addBreeder(user.getId(), stable_Id, breeder);
        return ResponseEntity.ok(new ApiException("Breeder added successfully"));
    }

    //move breeder To Stable by stable owner - Abeer
    @PostMapping("/moveBreeder/{breeder_Id}/ToStable/{stable_Id}")
    public ResponseEntity<ApiException> moveBreederToAnotherStable(@AuthenticationPrincipal User user , @PathVariable Integer breeder_Id, @PathVariable Integer stable_Id) {
        breederService.moveBreederToAnotherStable(user.getId(),breeder_Id, stable_Id);
        return ResponseEntity.ok(new ApiException("Breeder assign successfully"));
    }

    // TODO لازم نحط     @AuthenticationPrincipal لان اللي بيسويها صاحب الاسطبل
    //assignBreederToHorse - abeer
    @PutMapping("/assignBreeder/{breeder_Id}/ToHorse/{horse_Id}")
    public ResponseEntity<ApiException> assignBreederToHorse(@PathVariable Integer breeder_Id,@PathVariable Integer horse_Id) {
        breederService.assignBreederToHorse(breeder_Id, horse_Id);
        return ResponseEntity.ok(new ApiException("Breeder Assign to horse successfully"));
    }


    // Update breeder - Abeer
    @PutMapping("/update-breeder/{breeder_Id}/by-stable/{stable_Id}")
    public ResponseEntity<ApiException> updateBreeder(@AuthenticationPrincipal User user, @PathVariable Integer stable_Id, @PathVariable Integer breeder_Id, @RequestBody Breeder breeder) {
        breederService.updateBreeder(user.getId(), stable_Id, breeder_Id, breeder);
        return ResponseEntity.ok(new ApiException("Breeder updated successfully"));
    }


    // Delete breeder - Abeer
    @DeleteMapping("/delete/{breeder_Id}")
    public ResponseEntity<ApiException> deleteBreeder(@AuthenticationPrincipal User user, @PathVariable Integer breeder_Id) {
        breederService.deleteBreeder(user.getId(), breeder_Id);
        return ResponseEntity.ok(new  ApiException("Breeder deleted successfully"));
    }


}
