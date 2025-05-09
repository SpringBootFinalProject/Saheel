package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
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

    //#15
    // Get all stable reviews
    public List<StableReview> getReviewsByStable(Integer stableId) {
        Stable stable = stableRepository.findStableById(stableId);
        if (stable == null) {
            throw new ApiException("Stable not found");
        }

        return stableReviewRepository.findAllByStable(stable);
    }


    //#16
    // add Review
    public void addReview(StableReview review, Integer horseOwnerId, Integer stableId) {
        // Get the horse owner and check
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(horseOwnerId);
        if (horseOwner == null) {
            throw new RuntimeException("Horse owner not found");
        }
        // Get the stable and check
        Stable stable = stableRepository.findStableById(stableId);
        if (stable == null) {
            throw new RuntimeException("Stable not found");
        }

        // Check if
        if (!stableReviewRepository.findCourseReviewsByStableAndHorseOwner(stable, horseOwner).isEmpty())
            throw new ApiException("Customer can not make more than one review");

        // Set relationships and save the review.
        review.setHorseOwner(horseOwner);
        review.setStable(stable);

        stableReviewRepository.save(review);
    }

    //  update Review
    public void updateReview(Integer reviewId, StableReview updatedReview, Integer horseOwnerId) {
        // Get the horse owner and check
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(horseOwnerId);
        if (horseOwner == null) {
            throw new RuntimeException("Horse owner not found");
        }
        // Get the review and check
        StableReview existingReview = stableReviewRepository.findStableReviewById(reviewId);
        if (existingReview == null) {
            throw new RuntimeException("Review not found");
        }
        // Update review fields and save it.
        existingReview.setRating(updatedReview.getRating());
        existingReview.setHorseOwner(horseOwner);

        stableReviewRepository.save(existingReview);
    }


    //delete Review
    public void deleteReview(Integer reviewId, Integer horseOwnerId) {
        // Get the horse owner and check
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(horseOwnerId);
        if (horseOwner == null) {
            throw new RuntimeException("Horse owner not found");
        }
        // Get the review and check
        StableReview review = stableReviewRepository.findStableReviewById(reviewId);
        if (review == null) {
            throw new RuntimeException("Review not found");
        }

        stableReviewRepository.delete(review);
    }

    // ( #22 of 50 endpoints)
    // The reviews are sorted from best rating to worst rating.
    public List<StableReview> getAllReviewsSortedByRating() {
        return stableReviewRepository.findAllByOrderByRatingDesc();
    }


}
