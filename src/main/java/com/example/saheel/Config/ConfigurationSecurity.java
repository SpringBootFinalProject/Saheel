package com.example.saheel.Config;

import com.example.saheel.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigurationSecurity {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()

                //  ADMIN-only endpoints
                .requestMatchers("/api/v1/saheel/admin/**").hasAuthority("ADMIN")

                //  HORSEOWNER endpoints
                .requestMatchers(
                        "/api/v1/saheel/horse/get-owner-horses",
                        "/api/v1/saheel/horse/add-horse-by-owner",
                        "/api/v1/saheel/horse/assign/**",
                        "/api/v1/saheel/horse/update-horse/**",
                        "/api/v1/saheel/horse/delete-horse/**",
                        "/api/v1/saheel/horse/owner/horses-without-membership",
                        "/api/v1/saheel/horse/gift/**",
                        "/api/v1/saheel/horse-owner/update",
                        "/api/v1/saheel/horse-owner/delete",
                        "/api/v1/saheel/membership/request-membership/**",
                        "/api/v1/saheel/membership/renew-membership/**",
                        "/api/v1/saheel/membership/delete/**",
                        "/api/v1/saheel/stable-review/add/**",
                        "/api/v1/saheel/stable-review/update/**",
                        "/api/v1/saheel/stable-review/delete/**",
                        "/api/v1/saheel/staff-manager/veterinary/visit/**"
                ).hasAuthority("HORSEOWNER")

                //  CUSTOMER endpoints
                .requestMatchers(
                        "/api/v1/saheel/course-enrollment/enroll-to-course/**",
                        "/api/v1/saheel/course-enrollment/cancel-enrollment/**",
                        "/api/v1/saheel/course-review/review-course-by-customer/**",
                        "/api/v1/saheel/course-review/update-course-review/**",
                        "/api/v1/saheel/payments/card/**",
                        "/api/v1/saheel/payments/get-status"
                ).hasAuthority("CUSTOMER")

                //  STABLEOWNER endpoints
                .requestMatchers(
                        "/api/v1/saheel/breeder/get/**",
                        "/api/v1/saheel/breeder/search-by-name/**",
                        "/api/v1/saheel/breeder/delete/**",
                        "/api/v1/saheel/breeder/add/**",
                        "/api/v1/saheel/breeder/update-breeder/**",
                        "/api/v1/saheel/course/add-course-by-stable-owner/**",
                        "/api/v1/saheel/course/update-course/**",
                        "/api/v1/saheel/course/delete-course/**",
                        "/api/v1/saheel/course-enrollment/get-course-enrollments/**",
                        "/api/v1/saheel/membership/get",
                        "/api/v1/saheel/membership/get-expired-memberships",
                        "/api/v1/saheel/stable/get-my-stable/**",
                        "/api/v1/saheel/stable/add",
                        "/api/v1/saheel/stable/update/**",
                        "/api/v1/saheel/stable/delete/**",
                        "/api/v1/saheel/stable-owner/get-my-stable",
                        "/api/v1/saheel/staff-manager/move-breeder/**",
                        "/api/v1/saheel/staff-manager/move-trainer/**",
                        "/api/v1/saheel/staff-manager/move-veterinary/**",
                        "/api/v1/saheel/staff-manager/assign-veterinary/**",
                        "/api/v1/saheel/staff-manager/assign-breeder/**",
                        "/api/v1/saheel/staff-manager/get-available-trainer",
                        "/api/v1/saheel/staff-manager/all-horse-to-breeder/**",
                        "/api/v1/saheel/staff-manager/all-horse-to-veterinary/**",
                        "/api/v1/saheel/staff-manager/veterinary/visit/**",
                        "/api/v1/saheel/staff-manager/veterinary/visit/fit/**",
                        "/api/v1/saheel/veterinary/get/**",
                        "/api/v1/saheel/veterinary/search-by-name/**",
                        "/api/v1/saheel/veterinary/add/**",
                        "/api/v1/saheel/veterinary/update/**",
                        "/api/v1/saheel/veterinary/delete/**",
                        "/api/v1/saheel/veterinary-visit/delete/**"
                ).hasAuthority("STABLEOWNER")

                //  Invoice download for STABLEOWNER & HORSEOWNER
                .requestMatchers("/api/v1/saheel/customer/get-invoice-as-pdf/**")
                .hasAnyAuthority("CUSTOMER", "STABLEOWNER")

                //  Veterinary visit access for both STABLEOWNER and HORSEOWNER
                .requestMatchers("/api/v1/saheel/veterinary-visit/**")
                .hasAnyAuthority("STABLEOWNER", "HORSEOWNER")

                //  Public access endpoints
                .requestMatchers(
                        "/api/v1/saheel/course/get-stable-courses/**",
                        "/api/v1/saheel/course/get-available-courses",
                        "/api/v1/saheel/course/get-top-rated-course",
                        "/api/v1/saheel/course/get-courses-by-trainer/**",
                        "/api/v1/saheel/course/get-courses-by-date",
                        "/api/v1/saheel/course-review/get-stable-course-reviews/**",
                        "/api/v1/saheel/stable/get-all-stable",
                        "/api/v1/saheel/stable/get-my-stable/**",
                        "/api/v1/saheel/stable/get-available-stables",
                        "/api/v1/saheel/stableReview/stables/*/reviews",
                        "/api/v1/saheel/stableReview/reviews/sorted",
                        "/api/v1/saheel/stable-owner/register",
                        "/api/v1/saheel/customer/register-customer",
                        "/api/v1/saheel/horse-owner/register",
                        "/notifications/send-hello",  //
                        "/api/v1/saheel/horse-owner/register"

                ).permitAll()


                .anyRequest().authenticated()

                .and()
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

