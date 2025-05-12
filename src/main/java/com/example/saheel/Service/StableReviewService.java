package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.HorseOwner;
import com.example.saheel.Model.Stable;
import com.example.saheel.Model.StableReview;
import com.example.saheel.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StableReviewService {
    private final StableReviewRepository stableReviewRepository;
    private final HorseOwnerRepository horseOwnerRepository;
    private final StableRepository stableRepository;
    private final MembershipRepository membershipRepository;

    // Get all reviews for a stable
    public List<StableReview> getReviewsByStable(Integer stableId) {
        Stable stable = stableRepository.findStableById(stableId);
        if (stable == null) {
            throw new ApiException("Stable not found");
        }
        return stableReviewRepository.findAllByStable(stable);
    }

    // Add review to stable
    public void reviewStable(StableReview review, Integer horseOwnerId, Integer stableId) {
        // Get the horse owner
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(horseOwnerId);
        if (horseOwner == null) {
            throw new ApiException("Horse owner not found");
        }

        // Get the stable
        Stable stable = stableRepository.findStableById(stableId);
        if (stable == null) {
            throw new ApiException("Stable not found");
        }

        // Check subscription
        boolean isSubscribed = membershipRepository.existsByHorseOwnerAndStable(horseOwner, stable);
        if (!isSubscribed) {
            throw new ApiException("Horse owner is not subscribed to this stable");
        }

        // Prevent multiple reviews
        if (!stableReviewRepository.findCourseReviewsByStableAndHorseOwner(stable, horseOwner).isEmpty()) {
            throw new ApiException("Customer cannot make more than one review");
        }

        // Ensure stable rating fields are not null
        if (stable.getTotalRating() == null) stable.setTotalRating(0.0);
        if (stable.getTotalNumberOfRatings() == null) stable.setTotalNumberOfRatings(1.0);

        // Update stable rating
        double updatedStableRating = stable.getTotalRating() + review.getRating();
        stable.setTotalRating(updatedStableRating);
        stable.setTotalNumberOfRatings(stable.getTotalNumberOfRatings() + 1);

        // Set relationships and save the review
        review.setHorseOwner(horseOwner);
        review.setStable(stable);
        stableReviewRepository.save(review);
    }

    // Update a review
    public void updateReview(Integer reviewId, StableReview updatedReview, Integer horseOwnerId) {
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(horseOwnerId);
        if (horseOwner == null) {
            throw new ApiException("Horse owner not found");
        }

        StableReview existingReview = stableReviewRepository.findStableReviewById(reviewId);
        if (existingReview == null) {
            throw new ApiException("Review not found");
        }

        existingReview.setRating(updatedReview.getRating());
        existingReview.setHorseOwner(horseOwner);
        stableReviewRepository.save(existingReview);
    }

    // Delete a review
    public void deleteReview(Integer reviewId, Integer horseOwnerId) {
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(horseOwnerId);
        if (horseOwner == null) {
            throw new ApiException("Horse owner not found");
        }

        StableReview review = stableReviewRepository.findStableReviewById(reviewId);
        if (review == null) {
            throw new ApiException("Review not found");
        }

        stableReviewRepository.delete(review);
    }

    // Get all reviews sorted by rating
    public List<StableReview> getAllReviewsSortedByRating() {
        return stableReviewRepository.findAllByOrderByRatingDesc();
    }
}
