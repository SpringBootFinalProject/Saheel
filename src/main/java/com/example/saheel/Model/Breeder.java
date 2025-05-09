package com.example.saheel.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Breeder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The username can not be empty.")
    @Column(columnDefinition = "varchar(20) unique not null")
    private String username;

    @NotEmpty(message = "The password can not be empty.")
    @Column(columnDefinition = "varchar(50) not null ")
    private String password;

    @NotEmpty(message = "The name can not be empty.")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String fullName;

    @NotNull(message = "The password can not be empty.")
    @Column(columnDefinition = "int not null")
    private int age;

    @Email
    @NotEmpty(message = "The email can not be empty.")
    @Column(columnDefinition = "varchar(50) unique not null")
    private String email;

    @NotNull(message = "The years of experience can not be empty.")
    @Column(columnDefinition = "int not null")
    private int yearsOfExperience;

    @Column(columnDefinition = "double")
    private double rating;

    @ManyToOne
    @JsonIgnore
    private Stable stable;
}
