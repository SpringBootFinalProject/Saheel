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
public class CourseEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "The date can not be empty.")
    @Future
    private LocalDateTime date;
    @NotNull(message = "The price can not be empty.")
    @Column(columnDefinition = "double not null")
    private double price;
    @NotNull(message = "The duration can not be empty.")
    @Column(columnDefinition = "int not null")
    private int durationInMinute;
    private LocalDateTime lastCancellationDate;
    private Boolean courseCanceled = false;
    private Boolean enrollmentCanceled = false;
    private Boolean paid = false;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JsonIgnore
    private Course course;

    @OneToOne(mappedBy = "courseEnrollment", cascade = CascadeType.ALL)
    private EnrollmentInvoice invoice;
}
