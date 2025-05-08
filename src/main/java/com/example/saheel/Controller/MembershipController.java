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


    // get All Memberships
    @GetMapping("/get")
    public ResponseEntity<MembershipDTO> getAllMemberships(@AuthenticationPrincipal User user) {
        MembershipDTO dto = membershipService.getOwnerActiveMembership(user.getId());
        return ResponseEntity.ok(dto);
    }

    //  add Membership
    @PostMapping("/add{stableId}")
    public ResponseEntity<?> addMembership(@AuthenticationPrincipal User user, @Valid @RequestBody Membership membership, @PathVariable Integer stableId) {
        membershipService.requestMembership(membership, user.getId(), stableId);
        return ResponseEntity.status(200).body(new ApiResponse("Membership added"));
    }

    // update Membership
    @PutMapping("/renew/{id}")
    public ResponseEntity<?> renewMembership(@AuthenticationPrincipal User user, @PathVariable Integer id, @RequestBody Membership membership) {
        membershipService.renewMembership(user.getId(), membership, id);
        return ResponseEntity.ok(new ApiResponse("Membership renewed successfully"));
    }

    // delete Membership
    @DeleteMapping("/delete/{membershipId}/{stableId}")
    public ResponseEntity<?> deleteMembership(@AuthenticationPrincipal User user, @PathVariable Integer membershipId, @PathVariable Integer stableId) {
        membershipService.deleteMembership(user.getId(), stableId, membershipId);
        return ResponseEntity.status(200).body(new ApiResponse("Membership deleted"));
    }

}
