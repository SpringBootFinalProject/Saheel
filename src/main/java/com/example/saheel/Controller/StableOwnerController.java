package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.DTO.StableOwnerDTO;
import com.example.saheel.Model.EnrollmentInvoice;
import com.example.saheel.Model.StableOwner;
import com.example.saheel.Model.User;
import com.example.saheel.Service.InvoiceService;
import com.example.saheel.Service.StableOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//test
@RestController
@RequestMapping("/api/v1/saheel/stable-owner")
@RequiredArgsConstructor
public class StableOwnerController {

    private final StableOwnerService stableOwnerService;
    private final InvoiceService invoiceService;

    // ( #41 of 50 endpoints.)
    // Get stableOwner by ID - Abeer
    @GetMapping("/get-stable-owner")
    public ResponseEntity<StableOwner> getStableOwnerById(@AuthenticationPrincipal User user ) {
        StableOwner stableOwner = stableOwnerService.getStableOwnerById(user.getId());
        return ResponseEntity.ok(stableOwner);
    }

    // ( #42 of 50 endpoints.)
    // Add new stable owner
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerStableOwner(@RequestBody StableOwnerDTO stableOwnerDTO) {
        stableOwnerService.registerStableOwner(stableOwnerDTO);
        return ResponseEntity.ok(new ApiResponse("Stable owner added successfully"));
    }

    //Update stable owner - Abeer
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateStableOwner(@AuthenticationPrincipal User user, @RequestBody StableOwnerDTO stableOwnerDTO) {
        stableOwnerService.updateStableOwner(user.getId(),stableOwnerDTO);
        return ResponseEntity.ok(new ApiResponse("Stable owner updated successfully"));
    }

    // Delete stable owner - Abeer
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteStableOwner(@AuthenticationPrincipal User user) {
        stableOwnerService.deleteStableOwner(user.getId());
        return ResponseEntity.ok(new ApiResponse("Stable owner deleted successfully"));
    }

    // ( #41 of 50 endpoints.) Ayman
    @GetMapping("/get-pending-invoices{stableId}")
    public ResponseEntity<List<EnrollmentInvoice>> getPendingInvoices(@AuthenticationPrincipal User user, @PathVariable Integer stableId){
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getPendingEnrollmentInvoices(user.getId(), stableId));
    }


}