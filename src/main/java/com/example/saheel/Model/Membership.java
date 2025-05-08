package com.example.saheel.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @NotNull(message = "The price can not be null.")
    @Column(columnDefinition = "double not null")
    private double price;

    @ManyToOne
    @JsonIgnore
    private Stable stable;

    @ManyToOne
    @JsonIgnore
    private HorseOwner horseOwner;


}
