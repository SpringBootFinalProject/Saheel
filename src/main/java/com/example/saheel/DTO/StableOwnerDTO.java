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
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{7,}$", message = "Password must contain at least one letter and one number")
    private String password;

    @NotEmpty(message = "The name can not be empty.")
    private String fullName;

    @NotNull(message = "The password can not be empty.")
    private int age;

    @Email
    @NotEmpty(message = "The email can not be empty.")
    private String email;

    @NotEmpty(message = "The phone number can not be empty.")
    @Pattern(regexp = "\\+\\d{12}", message = "Phone number must start with the country code followed by 9 digits.")
    private String phoneNumber;

    private Boolean isApproved = false;




}
