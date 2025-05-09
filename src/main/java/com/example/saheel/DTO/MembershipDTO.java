package com.example.saheel.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MembershipDTO {
    private Integer id;
    private String membershipType;
    private double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private int horseCount;

}
