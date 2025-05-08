package com.example.saheel.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The username can not be empty.")
    @Column(columnDefinition = "varchar(20) unique not null")
    private String username;

    @NotEmpty(message = "The password can not be empty.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must be at least 8 characters and include uppercase, lowercase, and a number")
    @Column(columnDefinition = "varchar(50) not null")
    private String password;

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
    @NotEmpty(message = "The membership number can not be empty.")
    @Column(columnDefinition = "varchar(20) not null")
    private String membershipNumber;
    @NotEmpty(message = "The specialty can not be empty.")
    @Pattern(regexp = "XXXX|XXXXXXX") // What are the specialties.
    @Column(columnDefinition = "varchar(20) not null")
    private String specialty;
    @NotNull(message = "The years of experience can not be empty.")
    @Column(columnDefinition = "int not null")
    private int yearsOfExperience;
    @Column(columnDefinition = "double")
    private double rating;

    @ManyToOne
    @JsonIgnore
    private Stable stable;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private Set<Course> courses;
}
