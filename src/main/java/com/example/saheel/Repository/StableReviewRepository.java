package com.example.saheel.Repository;


import com.example.saheel.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StableReviewRepository extends JpaRepository<StableReview,Integer> {
    StableReview findStableReviewById(Integer id);
    List<StableReview> findAllByStable(Stable stable);
    List<StableReview> findCourseReviewsByStableAndHorseOwner(Stable stable, HorseOwner horseOwner);
    List<StableReview> findAllByOrderByRatingDesc();



}
