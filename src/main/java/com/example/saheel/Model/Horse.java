package com.example.saheel.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Horse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "The name can not be empty.")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;
    @NotEmpty(message = "The gender can not be empty.")
    @Pattern(regexp = "male|female")
    @Column(columnDefinition = "varchar(6) not null")
    private String gender;
    @NotNull(message = "The wight can not be empty.")
    @Column(columnDefinition = "int not null")
    private double wightInKG;
    @NotNull(message = "The height can not be empty.")
    @Column(columnDefinition = "int not null")
    private int heightInCM;
    @NotNull(message = "The age can not be empty.")
    @Column(columnDefinition = "int not null")
    private int age;
    @NotEmpty(message = "The passport number can not be empty.")
    @Column(columnDefinition = "varchar(50) unique not null")
    private String passportNumber;

    @ManyToOne
    @JsonIgnore
    private Membership membership;


    @ManyToOne
    @JsonIgnore
    private HorseOwner horseOwner;

//    @OneToOne
//    @JsonIgnore
//    private VeterinaryVisit veterinaryVisit; // One to one?
}
// StableReviewRepository
// StableReviewController
// StableReviewService