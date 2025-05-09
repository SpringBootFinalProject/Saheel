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

import java.time.LocalDate;
import java.util.List;

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
    @NotEmpty(message = "The membership Type can not be empty.")
    private String membershipType; // "MONTHLY", "YEARLY"

    @OneToMany(mappedBy = "membership", cascade = CascadeType.ALL)
    private List<Horse> horses;

//    @Column(length = 20)
//    private String paymentStatus = "PENDING"; // "PAID", "PENDING", "FAILED"


    @ManyToOne
    @JsonIgnore
    private Stable stable;

    @ManyToOne
    @JsonIgnore
    private HorseOwner horseOwner;


}
