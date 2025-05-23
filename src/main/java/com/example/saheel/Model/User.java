package com.example.saheel.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The username can not be empty.")
    @Column(name = "username", nullable = false, unique = true)
    private String username;



    @NotEmpty(message = "The password can not be empty.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{7,}$", message = "Password must contain at least one letter and one number")
    @Column(columnDefinition = "varchar(255) not null")
    private String password;
    @Pattern(regexp = "ADMIN|CUSTOMER|HORSEOWNER|STABLEOWNER")
    @Column(columnDefinition = "varchar(15)")
    private String role;

    @NotEmpty(message = "The name can not be empty.")
    @Column(columnDefinition = "varchar(20) not null")
    private String fullName;

    @NotNull(message = "The password can not be empty.")
    @Column(columnDefinition = "int not null")
    private int age;
    @Email
    @NotEmpty(message = "The email can not be empty.")
    @Column(columnDefinition = "varchar(50) unique not null")
    private String email;

    @NotEmpty(message = "The phone number can not be empty.")
    @Pattern(regexp = "\\+\\d{12}", message = "Phone number must start with the country code followed by 9 digits.")
    @Column(columnDefinition = "varchar(20) unique not null")
    private String phoneNumber;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Customer horseOwner;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Customer customer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Customer stableOwner;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
