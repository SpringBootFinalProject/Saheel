package com.example.saheel.DtoIn;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDtoIn {
    @NotEmpty(message = "The username can not be empty.")
    @Column(columnDefinition = "varchar(20) unique not null")
    private String username;
    @NotEmpty(message = "The password can not be empty.")
    @Column(columnDefinition = "varchar(50) not null")
    private String password;
    @Pattern(regexp = "admin|customer|horseowner|satbleowner")
    @Column(columnDefinition = "varchar(15)")
    private String role;
    @NotEmpty(message = "The name can not be empty.")
    @Column(columnDefinition = "varchar(20) not null")
    private String fullName;
    @NotNull(message = "The password can not be empty.")
    @Column(columnDefinition = "int not null")
    private int age;
    @Email
    @NotEmpty(message = "The email can not be empty.")
    @Column(columnDefinition = "varchar(50) unique not null")
    private String email;
    @Pattern(regexp = "amateur|beginner|professional")
    private String level;
    @NotEmpty(message = "The phone number can not be empty.")
    @Pattern(regexp = "\\+\\d{12}", message = "Phone number must start with the country code followed by 9 digits.")
    @Column(columnDefinition = "varchar(13) unique not null")
    private String phoneNumber;

}
