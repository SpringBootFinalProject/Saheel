package com.example.saheel.Controller;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.Breeder;
import com.example.saheel.Model.User;
import com.example.saheel.Model.Veterinary;
import com.example.saheel.Service.BreederService;
import com.example.saheel.Service.StaffManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/saheel/breeder")
@RequiredArgsConstructor
public class BreederController {

    private final BreederService breederService;
    private final StaffManagerService staffManagerService;

    // Get breeder by ID - Abeer
    @GetMapping("/get/{breeder_Id}")
    public ResponseEntity<Breeder> getBreederById(@AuthenticationPrincipal User user, @PathVariable Integer breeder_Id) {
        Breeder breeder = breederService.getBreederById(user.getId(), breeder_Id);
        return ResponseEntity.ok(breeder);
    }


    // ( #11 of 50 endpoints ) Abeer
   // search name of Breeder By stable owner
    @GetMapping("/search-by-name/{fullName}")
    public ResponseEntity<Breeder> searchBreederByName(@AuthenticationPrincipal User user, @PathVariable String fullName) {

        Breeder breeder = breederService.searchBreederByName(user.getId(),fullName);
        return ResponseEntity.ok(breeder);
    }

    // Add new breeder - Abeer
    @PostMapping("/add/{stable_Id}")
    public ResponseEntity<ApiResponse> addBreeder(@AuthenticationPrincipal User user, @PathVariable Integer stable_Id, @RequestBody Breeder breeder) {
        breederService.addBreeder(user.getId(), stable_Id, breeder);
        return ResponseEntity.ok(new ApiResponse("Breeder added successfully"));
    }

    // Update breeder - Abeer
    @PutMapping("/update-breeder/{breeder_Id}/by-stable/{stable_Id}")
    public ResponseEntity<ApiResponse> updateBreeder(@AuthenticationPrincipal User user, @PathVariable Integer stable_Id, @PathVariable Integer breeder_Id, @RequestBody Breeder breeder) {
        breederService.updateBreeder(user.getId(), stable_Id, breeder_Id, breeder);
        return ResponseEntity.ok(new ApiResponse("Breeder updated successfully"));
    }


    // Delete breeder - Abeer
    @DeleteMapping("/delete/{breeder_Id}")
    public ResponseEntity<ApiResponse> deleteBreeder(@AuthenticationPrincipal User user, @PathVariable Integer breeder_Id) {
        breederService.deleteBreeder(user.getId(), breeder_Id);
        return ResponseEntity.ok(new  ApiResponse("Breeder deleted successfully"));
    }


}
