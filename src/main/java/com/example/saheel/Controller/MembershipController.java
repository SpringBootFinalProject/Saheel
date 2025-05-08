package com.example.saheel.Controller;


import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.Membership;
import com.example.saheel.Service.MembershipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/saheel/membership")
@RequiredArgsConstructor
public class MembershipController {
    private final MembershipService membershipService;


    // get All Memberships
    // TODO:  @AuthenticationPrincipal User user
    @GetMapping("/get")
    public ResponseEntity<List<Membership>> getAllMemberships() {
        return ResponseEntity.status(200).body(membershipService.getAllMemberships());
    }

    //  add Membership
    // TODO:  @AuthenticationPrincipal User user
    @PostMapping("/add/{ownerId}")
    public ResponseEntity<?> addMembership(@PathVariable Integer ownerId, @Valid @RequestBody Membership membership) {
        membershipService.addMembership(membership, ownerId);
        //    TODO:     membershipService.addMembership(membership, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Membership added"));
    }

    // update Membership
    // TODO:  @AuthenticationPrincipal User user
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMembership(@PathVariable Integer id, @Valid @RequestBody Membership membership) {
        membershipService.updateMembership(id, membership);
        // TODO   membershipService.updateMembership(user.getId(), membership);
        return ResponseEntity.status(200).body(new ApiResponse("Membership updated"));
    }

    // delete Membership
    // TODO:  @AuthenticationPrincipal User user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMembership(@PathVariable Integer id) {
        membershipService.deleteMembership(id);
        //TODO    membershipService.deleteMembership(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Membership deleted"));
    }

}
