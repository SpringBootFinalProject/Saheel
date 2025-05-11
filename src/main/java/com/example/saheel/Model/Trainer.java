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

    @NotEmpty(message = "The name can not be empty.")
    @Column(columnDefinition = "varchar(20) not null")
    private String fullName;

    @NotNull(message = "The age can not be empty.")
    @Column(columnDefinition = "int not null")
    private int age;

    @Email
    @NotEmpty(message = "The email can not be empty.")
    @Column(columnDefinition = "varchar(50) unique not null")
    private String email;

    @NotEmpty(message = "The membership number can not be empty.")
    @Column(columnDefinition = "varchar(20) not null")
    private String membershipNumber;


    @NotNull(message = "The years of experience can not be empty.")
    @Column(columnDefinition = "int not null")
    private int yearsOfExperience;

    @Column(columnDefinition = "double")
    private Double totalRating;
    private Double totalNumberOfRatings;

    private Boolean isActive = false;

    @ManyToOne
    @JsonIgnore
    private Stable stable;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private Set<Course> courses;
}
