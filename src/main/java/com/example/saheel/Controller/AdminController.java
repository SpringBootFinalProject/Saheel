
package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.Customer;
import com.example.saheel.Model.HorseOwner;
import com.example.saheel.Model.StableOwner;
import com.example.saheel.Model.User;
import com.example.saheel.Service.AdminService;
import com.example.saheel.Service.MembershipService;
import com.example.saheel.Service.StableOwnerService;
import com.example.saheel.Service.WhatsAppNotifications;
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
    private final WhatsAppNotifications whatsAppNotifications;


    // ( #1 of 50 endpoints ) Abrar
    // This method finds the horse owner who owns the most horses.
    // It can return more than one owner if they have the same number of horses.
    @GetMapping("/most-horses")
    public ResponseEntity<List<HorseOwner>> getHorseOwnersWithMostHorses(@AuthenticationPrincipal User user) {
        List<HorseOwner> result = adminService.getOwnersWithMostHorses(user.getId());
        return ResponseEntity.ok(result);
    }


    // ( #2 of 50 endpoints ) Abrar
    // This method sends a welcome email to all customers in the system.
    @PostMapping("/send-welcome-to-all-customer")
    public ResponseEntity<?> sendWelcomeToAllCustomer(@AuthenticationPrincipal User user) {
        adminService.sendWelcomeEmailsToAllCustomer(user.getId());
        return ResponseEntity.ok(new ApiResponse("Welcome messages sent to customers."));
    }

    // ( #3 of 50 endpoints ) Abrar
    // This method sends a welcome email to all horse owners in the system.
    @PostMapping("/send-welcome-to-all-horseOwner")
    public ResponseEntity<?> sendWelcomeEmailsToAllHorseOwner(@AuthenticationPrincipal User user) {
        adminService.sendWelcomeEmailsToAllHorseOwner(user.getId());
        return ResponseEntity.ok(new ApiResponse("Welcome messages sent to HorseOwner."));
    }

    // ( #4 of 50 endpoints ) Abrar
    // This method allows an admin to approve a stable owner account.
    @PutMapping("/approve-stable-owner/{stableOwnerId}")
    public ResponseEntity<?> approveStableOwner(@AuthenticationPrincipal User user, @PathVariable Integer stableOwnerId) {
        adminService.approveStableOwner(user.getId(), stableOwnerId);
        return ResponseEntity.ok(new ApiResponse("Stable owner account has been approved."));
    }

    // ( #5 of 50 endpoints ) Abrar
    // This method sends a welcome email to all new members who joined today.
    @PostMapping("/send-membership-welcome")
    public ResponseEntity<?> sendWelcomeToNewMembers(@AuthenticationPrincipal User user) {
        adminService.sendWelcomeEmailsToNewMembers(user.getId());
        return ResponseEntity.ok(new ApiResponse("Welcome emails have been sent to new members."));
    }

    // ( #6 of 50 endpoints )Abrar
    @GetMapping("/get-unapproved-stable-owners")
    public ResponseEntity<List<StableOwner>> getUnapprovedStableOwners(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(stableOwnerService.getUnapprovedStableOwners(user.getId()));
    }


    // ( #7 of 50 endpoints ) Abrar
    @GetMapping("/get-all-stable-owners")
    public ResponseEntity<List<StableOwner>> getAllStableOwners(@AuthenticationPrincipal User user) {
        List<StableOwner> stableOwner = adminService.getAllStableOwners(user.getId());
        return ResponseEntity.ok(stableOwner);
    }

    // ( #8 of 50 endpoints ) Abrar
    @GetMapping("/get-all-customers")
    public ResponseEntity<List<Customer>> getAllCustomers(@AuthenticationPrincipal User user) {
        List<Customer> customer = adminService.getAllCustomer(user.getId());
        return ResponseEntity.ok(customer);
    }

    // ( #9 of 50 endpoints ) Abrar
    @GetMapping("/get-all-horse-owner")
    public ResponseEntity<List<HorseOwner>> getAllHorseOwners(@AuthenticationPrincipal User user) {
        List<HorseOwner> horseOwner = adminService.getAllHorseOwner(user.getId());
        return ResponseEntity.ok(horseOwner);
    }

    // ( #10 of 50 endpoints ) Abeer
    @PostMapping("/send-expiring-membership-notifications")
    public ResponseEntity<ApiResponse> sendExpiringMembershipNotifications(@AuthenticationPrincipal User user) {
        adminService.notifyMembershipExpiringSoon(user.getId());
        return ResponseEntity.ok(new ApiResponse("Expiring membership notifications sent successfully."));
    }

    @PutMapping("/change-membership-status/{membershipId}")
    public ResponseEntity<ApiResponse> changeMembershipStatus(@AuthenticationPrincipal User user,@PathVariable Integer membershipId){
        adminService.changeMembershipStatus(user.getId(), membershipId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Membership status changed successfully."));
    }

}

