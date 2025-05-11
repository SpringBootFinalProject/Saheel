package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.HorseOwner;
import com.example.saheel.Model.StableOwner;
import com.example.saheel.Model.User;
import com.example.saheel.Service.AdminService;
import com.example.saheel.Service.StableOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/saheel/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final StableOwnerService stableOwnerService;



    // ( #1 of 50 endpoints )
    // This method finds the horse owner who owns the most horses.
    // It can return more than one owner if they have the same number of horses.
    @GetMapping("/most-horses")
    public ResponseEntity<List<HorseOwner>> getHorseOwnersWithMostHorses(@AuthenticationPrincipal User user) {
        List<HorseOwner> result = adminService.getOwnersWithMostHorses(user.getId());
        return ResponseEntity.ok(result);
    }


    // ( #2 of 50 endpoints )
    // This method sends a welcome email to all customers in the system.
    @PostMapping("/send-welcome-to-all-customer")
    public ResponseEntity<?> sendWelcomeToAllCustomer(@AuthenticationPrincipal User user) {
        adminService.sendWelcomeEmailsToAllCustomer(user.getId());
        return ResponseEntity.ok(new ApiResponse("Welcome messages sent to customers."));
    }

    // ( #3 of 50 endpoints )
    // This method sends a welcome email to all horse owners in the system.
    @PostMapping("/send-welcome-to-all-horseOwner")
    public ResponseEntity<?> sendWelcomeEmailsToAllHorseOwner(@AuthenticationPrincipal User user) {
        adminService.sendWelcomeEmailsToAllHorseOwner(user.getId());
        return ResponseEntity.ok(new ApiResponse("Welcome messages sent to HorseOwner."));
    }

    // ( #4 of 50 endpoints )
    // This method allows an admin to approve a stable owner account.
    @PutMapping("/approve-stable-owner/{stableId}")
    public ResponseEntity<?> approveStableOwner(@AuthenticationPrincipal User user, @PathVariable Integer stableId) {
        adminService.approveStableOwner(user.getId(), stableId);
        return ResponseEntity.ok(new ApiResponse("Stable owner account has been approved."));
    }

    // ( #5 of 50 endpoints )
    // This method sends a welcome email to all new members who joined today.
    @PostMapping("/send-membership-welcome")
    public ResponseEntity<?> sendWelcomeToNewMembers(@AuthenticationPrincipal User user) {
        adminService.sendWelcomeEmailsToNewMembers(user.getId());
        return ResponseEntity.ok(new ApiResponse("Welcome emails have been sent to new members."));
    }

    @GetMapping("/get-unapproved-stable-owners")
    public ResponseEntity<List<StableOwner>> getUnapprovedStableOwners(@AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(stableOwnerService.getUnapprovedStableOwners(user.getId()));
    }


}
