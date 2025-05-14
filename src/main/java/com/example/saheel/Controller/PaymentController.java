package com.example.saheel.Controller;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.PaymentRequest;
import com.example.saheel.Model.User;
import com.example.saheel.Service.MembershipPaymentService;
import com.example.saheel.Service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/saheel/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final MembershipPaymentService membershipPaymentService;

    // ( #36 of 50 endpoints.) Ayman
    @PostMapping("/course-enrollment-card/{courseEnrollmentId}")
    public ResponseEntity<ApiResponse> processPaymentOfEnrollment(@AuthenticationPrincipal User user, @PathVariable Integer courseEnrollmentId, @RequestBody PaymentRequest paymentRequest){
        try {
            return paymentService.processPayment(paymentRequest, user.getId(), courseEnrollmentId);
        } catch (ApiException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error processing payment response"));
        }
    }

    // ( #37 of 50 endpoints.) Ayman
    @GetMapping("/get-status")
    public ResponseEntity<ApiResponse> getPaymentStatus(@AuthenticationPrincipal String id){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(paymentService.getPaymentStatus(id)));
    }


    // ( #38 of 50 endpoints.) Abrar
    @PostMapping("/membership/{membershipId}")
    public ResponseEntity<ApiResponse> processPaymentOfMembership(
            @AuthenticationPrincipal User user,
            @PathVariable Integer membershipId,
            @RequestBody PaymentRequest paymentRequest) {

        try {
            return membershipPaymentService.processMembershipPayment(paymentRequest, user.getId(), membershipId);
        } catch (ApiException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error processing membership payment"));
        }
    }
}


