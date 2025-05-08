package com.example.saheel.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class StableOwnerDTO {

    @NotEmpty(message = "The username can not be empty.")
    private String username;

    @NotEmpty(message = "The password can not be empty.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must be at least 8 characters and include uppercase, lowercase, and a number")
    private String password;

    @Pattern(regexp = "admin|customer|horseowner|satbleowner")
    private String role;

    @NotEmpty(message = "The name can not be empty.")
    private String fullName;

    @NotNull(message = "The password can not be empty.")
    private int age;

    @Email
    @NotEmpty(message = "The email can not be empty.")
    private String email;

}
