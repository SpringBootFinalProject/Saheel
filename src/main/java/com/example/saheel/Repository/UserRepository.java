package com.example.saheel.Repository;

import com.example.saheel.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
    boolean existsByEmail(String email);

    User findUserById(Integer userId);

    List<User> findAllByRole(String role);

    User findUserByIdAndRole(Integer userId, String role);
}
