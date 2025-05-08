package com.example.saheel.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HorseOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "horseOwner", cascade = CascadeType.ALL)
    private List<Membership> memberships;

    @OneToMany(mappedBy = "horseOwner", cascade = CascadeType.ALL)
    private List<Horse> horses;

    @OneToMany(mappedBy = "horseOwner", cascade = CascadeType.ALL)
    private List<StableReview> reviews;
}
