package com.example.saheel.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Pattern(regexp = "amateur|beginner|professional")
    private String level;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<CourseEnrollment> courseEnrollments;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<CourseReview> courseReviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<EnrollmentInvoice> invoices;


}
