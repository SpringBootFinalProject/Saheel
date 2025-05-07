package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.DtoIn.CustomerDtoIn;
import com.example.saheel.Model.Customer;
import com.example.saheel.Model.User;
import com.example.saheel.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/saheel/Customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register-customer")
    public ResponseEntity<ApiResponse> registerCustomer(@RequestBody @Valid CustomerDtoIn customerDtoIn) {
        customerService.registerCustomer(customerDtoIn);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Customer registered successfully."));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Customer>> getAllCustomer(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomers(user.getUsername()));
    }

    @PutMapping("/update-customer")
    public ResponseEntity<ApiResponse> updateCustomer(@AuthenticationPrincipal User user, @RequestBody @Valid CustomerDtoIn customerDtoIn) {
        customerService.updateCustomer(user.getId(), customerDtoIn);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Customer updated successfully."));
    }

    @DeleteMapping("/delete-customer")
    public ResponseEntity<ApiResponse> deleteCustomer(@AuthenticationPrincipal User user) {
        customerService.deleteCustomer(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Customer deleted successfully."));
    }
}
