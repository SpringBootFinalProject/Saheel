package com.example.saheel.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
public class MembershipInvoice {
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
    private Membership membership;

    @ManyToOne
    @JsonIgnore
    private HorseOwner horseOwner;
}
