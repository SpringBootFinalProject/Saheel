package com.example.saheel.Repository;

import com.example.saheel.Model.Stable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StableRepository extends JpaRepository<Stable,Integer> {
    Stable findStableById(Integer id);
}
