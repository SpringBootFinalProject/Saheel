package com.example.saheel.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VeterinaryVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "The reason can not be empty.")
    private String reason;


    private String MedicalReport;

    @NotNull(message = "The visit date must be set")
    private LocalDateTime visitDateTime;

    @Column
    private Boolean isCompleted = false;



    @ManyToOne
    @JsonIgnore
    private Stable stable;

    @ManyToOne
    @JsonIgnore
    private Veterinary veterinary;


    @ManyToOne
    @JsonIgnore
    private Horse horse;
}
