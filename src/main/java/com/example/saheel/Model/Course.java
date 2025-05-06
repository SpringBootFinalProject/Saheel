package com.example.saheel.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

// Should we add an attribute to differentiate if the course is for the horses or for the customers?
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "The name can not be empty.")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;
    @NotEmpty(message = "The description can not be empty.")
    @Column(columnDefinition = "varchar(255) not null")
    private String description;
    @NotNull(message = "The capacity can not be empty.")
    @Column(columnDefinition = "int not null")
    private int capacity;
    @Column(columnDefinition = "int")
    private int numberOfEnrolled = 0;
    @NotNull(message = "The date can not be empty.")
    @Future
    private LocalDateTime date;
    @NotNull(message = "The price can not be empty.")
    @Column(columnDefinition = "double not null")
    private double price;
    @NotNull(message = "The duration can not be empty.")
    @Column(columnDefinition = "int not null")
    private int durationInMinute;

    @ManyToOne
    @JsonIgnore
    private Stable stable; // Should this be removed, since there is relationship through the trainer.

    @ManyToOne
    @JsonIgnore
    private Stable trainer;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseEnrollment> courseEnrollments;


}
