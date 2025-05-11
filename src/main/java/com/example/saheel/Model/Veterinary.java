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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Veterinary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition = "varchar(20) not null")
    private String fullName;

    @Column(nullable = false)
    private int age;

    @Column(columnDefinition = "varchar(50) not null unique")
    private String email;

    @Column(nullable = false)
    private int yearsOfExperience;

    @Column
    private double rating;
    private Boolean isActive = false;

    @ManyToOne
    @JsonIgnore
    private Stable stable;

    @OneToMany(mappedBy = "veterinary", cascade = CascadeType.ALL)
    private List<Horse> horses;

    @OneToMany(mappedBy = "veterinary", cascade = CascadeType.ALL)
    private List<VeterinaryVisit> veterinaryVisits;
}
