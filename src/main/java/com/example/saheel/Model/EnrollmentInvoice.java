package com.example.saheel.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EnrollmentInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String paymentId;
    @Pattern(regexp = "pending|intiated|failed|paid")
    private String status;
    private Double totalPrice;
    private LocalDateTime dateTime;

    @OneToOne
    @MapsId
    @JsonIgnore
    private CourseEnrollment courseEnrollment;

    @ManyToOne
    @JsonIgnore
    private Customer customer;
}
