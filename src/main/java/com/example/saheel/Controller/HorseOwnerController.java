package com.example.saheel.Controller;


import com.example.saheel.Api.ApiResponse;
import com.example.saheel.DTO.HorseOwnerDTO;
import com.example.saheel.Model.HorseOwner;
import com.example.saheel.Model.User;
import com.example.saheel.Service.HorseOwnerService;
import com.example.saheel.Service.MembershipInvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;


import java.util.List;

@RestController
@RequestMapping("/api/v1/saheel/horse-owner")
@RequiredArgsConstructor
public class HorseOwnerController {
    private final HorseOwnerService horseOwnerService;
    private final MembershipInvoiceService membershipInvoiceService;


    // ( #28 of 50 endpoints ) Abrar
    @PostMapping("/register")
    public ResponseEntity<?> registerHorseOwner(@Valid @RequestBody HorseOwnerDTO horseOwnerDTO) {
        horseOwnerService.registerHorseOwner(horseOwnerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Horse owner registered successfully"));
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateHorseOwner(@AuthenticationPrincipal User user, @Valid @RequestBody HorseOwnerDTO horseOwnerDTO) {
        horseOwnerService.updateHorseOwner(user.getId(), horseOwnerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Horse owner updated"));
    }

    // delete HorseOwner
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteHorseOwner(@AuthenticationPrincipal User user) {
        horseOwnerService.deleteHorseOwner(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Horse owner deleted"));
    }

    // ( #30 of 50 endpoints ) Abrar
    @GetMapping("/membership-invoice/pdf/{invoiceId}")
    public ResponseEntity<byte[]> getMembershipInvoiceAsPdf(@AuthenticationPrincipal User user, @PathVariable Integer invoiceId) {
        byte[] pdfBytes = membershipInvoiceService.getMembershipInvoicePdf(user.getId(), invoiceId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=membership-invoice-" + invoiceId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }



}
