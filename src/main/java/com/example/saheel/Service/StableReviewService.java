package com.example.saheel.Service;

import com.example.saheel.Model.HorseOwner;
import com.example.saheel.Model.Stable;
import com.example.saheel.Model.StableReview;
import com.example.saheel.Repository.HorseOwnerRepository;
import com.example.saheel.Repository.StableRepository;
import com.example.saheel.Repository.StableReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StableReviewService {
    private final StableReviewRepository stableReviewRepository;
    private final HorseOwnerRepository horseOwnerRepository;
    private final StableRepository stableRepository;

    // get All StableReviews
    public List<StableReview> getAllStableReviews() {
        return stableReviewRepository.findAll();
    }

    // add Review
    public void addReview(StableReview review, Integer horseOwnerId, Integer stableId) {
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(horseOwnerId);
        if (horseOwner == null) {
            throw new RuntimeException("Horse owner not found");
        }
        Stable stable = stableRepository.findStableById(stableId);
        if (stable == null) {
            throw new RuntimeException("Stable not found");
        }

        review.setHorseOwner(horseOwner);
        review.setStable(stable);

        stableReviewRepository.save(review);
    }

    //  update Review
    public void updateReview(Integer reviewId, StableReview updatedReview , Integer horseOwnerId) {
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(horseOwnerId);
        if (horseOwner == null) {
            throw new RuntimeException("Horse owner not found");
        }
        StableReview existingReview = stableReviewRepository.findStableReviewById(reviewId);
        if (existingReview == null) {
            throw new RuntimeException("Review not found");
        }
        existingReview.setRating(updatedReview.getRating());
        existingReview.setHorseOwner(horseOwner);

        stableReviewRepository.save(existingReview);
    }

    //delete Review
    public void deleteReview(Integer reviewId , Integer horseOwnerId) {
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(horseOwnerId);
        if (horseOwner == null) {
            throw new RuntimeException("Horse owner not found");
        }
        StableReview review = stableReviewRepository.findStableReviewById(reviewId);
        if (review == null) {
            throw new RuntimeException("Review not found");
        }
        review.setHorseOwner(horseOwner);
        stableReviewRepository.delete(review);
    }


}
