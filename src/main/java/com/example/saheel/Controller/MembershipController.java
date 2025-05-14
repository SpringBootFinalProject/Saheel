package com.example.saheel.Controller;


import com.example.saheel.Api.ApiResponse;
import com.example.saheel.DTO.MembershipDTO;
import com.example.saheel.Model.Membership;
import com.example.saheel.Model.User;
import com.example.saheel.Service.MembershipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/saheel/membership")
@RequiredArgsConstructor
public class MembershipController {
    private final MembershipService membershipService;

    // ( #31 of 50 endpoints ) Abrar
    // get All Memberships
    @GetMapping("/get")
    public ResponseEntity<MembershipDTO> getAllMemberships(@AuthenticationPrincipal User user) {
        MembershipDTO dto = membershipService.getOwnerActiveMembership(user.getId());
        return ResponseEntity.ok(dto);
    }
    // ( #33 of 50 endpoints ) Abrar -Ayman -Abeer
    //  add Membership
    @PostMapping("/request-membership/{stableId}")
    public ResponseEntity<?> requestMembership(@AuthenticationPrincipal User user, @Valid @RequestBody Membership membership, @PathVariable Integer stableId) {
        membershipService.requestMembership(membership, user.getId(), stableId);
        return ResponseEntity.status(200).body(new ApiResponse("Membership added"));
    }



    // ( #34 of 50 endpoints ) Abrar -Ayman -Abeer
    @PutMapping("/renew-membership/{id}")
    public ResponseEntity<?> renewMembership(@AuthenticationPrincipal User user, @PathVariable Integer id, @RequestBody Membership membership) {
        membershipService.renewMembership(user.getId(), membership, id);
        return ResponseEntity.ok(new ApiResponse("Membership renewed successfully"));
    }

    // delete Membership
//    @DeleteMapping("/delete/{membershipId}/{stableId}")
//    public ResponseEntity<?> cancelMembership(@AuthenticationPrincipal User user, @PathVariable Integer membershipId, @PathVariable Integer stableId) {
//        membershipService.cancelMembership(user.getId(), stableId, membershipId);
//        return ResponseEntity.status(200).body(new ApiResponse("Membership deleted"));
//    }

    // ( #35 of 50 endpoints.) Abrar
    // This method gets all memberships that are expired.
    // A membership is expired if its end date is before today.
    @GetMapping("/get-expired-memberships")
    public ResponseEntity<List<Membership>> getExpiredMemberships() {
        List<Membership> expired = membershipService.getExpiredMemberships();
        return ResponseEntity.ok(expired);
    }

}
