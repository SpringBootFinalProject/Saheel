package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.DTO.CustomerDTO;
import com.example.saheel.Model.Customer;
import com.example.saheel.Model.User;
import com.example.saheel.Service.CustomerService;
import com.example.saheel.Service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/saheel/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final InvoiceService invoiceService;

    // ( #22 of 50 endpoints ) Ayman
    @PostMapping("/register-customer")
    public ResponseEntity<ApiResponse> registerCustomer(@RequestBody @Valid CustomerDTO customerDtoIn) {
        customerService.registerCustomer(customerDtoIn);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Customer registered successfully."));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Customer>> getAllCustomer(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomers(user.getUsername()));
    }

    @PutMapping("/update-customer")
    public ResponseEntity<ApiResponse> updateCustomer(@AuthenticationPrincipal User user, @RequestBody @Valid CustomerDTO customerDtoIn) {
        customerService.updateCustomer(user.getId(), customerDtoIn);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Customer updated successfully."));
    }

    @DeleteMapping("/delete-customer")
    public ResponseEntity<ApiResponse> deleteCustomer(@AuthenticationPrincipal User user) {
        customerService.deleteCustomer(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Customer deleted successfully."));
    }

    // ( #23 of 50 endpoints ) Ayman
    @GetMapping("/get-invoice-as-pdf/{invoiceId}")
    public ResponseEntity<byte[]> getInvoiceAsPdf(@AuthenticationPrincipal User user, @PathVariable Integer invoiceId) {
        byte[] pdfBytes = invoiceService.getEnrollmentInvoiceAsPdf(user.getId(), invoiceId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contract-" + invoiceId + ".pdf") // Forces download
                .contentType(MediaType.APPLICATION_PDF) // Sets MIME type
                .body(pdfBytes);
    }
}
