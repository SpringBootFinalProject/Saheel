package com.example.saheel.Repository;


import com.example.saheel.Model.StableReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StableReviewRepository extends JpaRepository<StableReview,Integer> {
    StableReview findStableReviewById(Integer id);
}
