package com.example.saheel.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

//We need to discuss the attribute
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "double not null")
    private double price;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isActive = false;

    @Column(length = 20)
    @Pattern(regexp = "monthly|yearly")
    private String membershipType; // "MONTHLY", "YEARLY"

//    @Column(length = 20)
//    private String paymentStatus = "PENDING"; // e.g., "PAID", "PENDING", "FAILED"


    @ManyToOne
    @JsonIgnore
    private Stable stable;

    @ManyToOne
    @JsonIgnore
    private HorseOwner horseOwner;


}
