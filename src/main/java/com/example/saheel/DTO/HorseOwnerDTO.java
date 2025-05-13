package com.example.saheel.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HorseOwnerDTO {

    @NotEmpty
    private String username;

    @NotEmpty(message = "The password can not be empty.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{7,}$", message = "Password must contain at least one letter and one number")
    private String password;

    @NotEmpty
    private String fullName;

    @Email
    private String email;

    @NotNull
    private Integer age;
    @NotEmpty(message = "The phone number can not be empty.")
    @Pattern(regexp = "\\+\\d{12}", message = "Phone number must start with the country code followed by 9 digits.")
    private String phoneNumber;
}
