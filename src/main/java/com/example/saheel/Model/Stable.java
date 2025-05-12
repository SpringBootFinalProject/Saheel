package com.example.saheel.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Stable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The name can not be empty.")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String name;

    @NotEmpty(message = "The description can not be empty.")
    @Column(columnDefinition = "varchar(500) not null")
    private String description;

    @NotNull(message = "The capacity can not be empty.")//can not be zero
    @Column(columnDefinition = "int not null")
    private int capacity;

    @NotEmpty(message = "The location can not be empty.")
    @Column(columnDefinition = "varchar(50) not null")
    private String location; //What is the format of the location?

    @Column(columnDefinition = "double")
    private Double totalRating;
    private Double totalNumberOfRatings;


    @ManyToOne
    @JsonIgnore
    private StableOwner stableOwner;

//    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
//    private List<Review> reviews;

    @OneToMany(mappedBy = "stable", cascade = CascadeType.ALL)
    private List<Breeder> breeders;

    @OneToMany(mappedBy = "stable", cascade = CascadeType.ALL)
    private List<Trainer> trainers;

    @OneToMany(mappedBy = "stable", cascade = CascadeType.ALL)
    private List<Veterinary> veterinaries;

    @OneToMany(mappedBy = "stable", cascade = CascadeType.ALL)
    private List<Course> courses;

    @OneToMany(mappedBy = "stable", cascade = CascadeType.ALL)
    private List<Membership> memberships = new ArrayList<>();

    @OneToMany(mappedBy = "stable", cascade = CascadeType.ALL)
    private List<StableReview> stableReviews;
}
