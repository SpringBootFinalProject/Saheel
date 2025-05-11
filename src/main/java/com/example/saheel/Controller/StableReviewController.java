package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.StableReview;
import com.example.saheel.Model.User;
import com.example.saheel.Repository.UserRepository;
import com.example.saheel.Service.StableReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/saheel/stableReview")
@RequiredArgsConstructor
public class StableReviewController {
    private final StableReviewService stableReviewService;
    private final UserRepository userRepository;

    // ( #39 of 50 endpoints )
    @GetMapping("/stables/{stableId}/reviews")
    public ResponseEntity<List<StableReview>> getReviewsByStable(@PathVariable Integer stableId) {
        List<StableReview> reviews = stableReviewService.getReviewsByStable(stableId);
        return ResponseEntity.ok(reviews);
    }

    // ( #40 of 50 endpoints )
    //  add Review of  Stable
    @PostMapping("/add/{stableId}")
    public ResponseEntity<?> addReview(@AuthenticationPrincipal User user,
                                       @PathVariable Integer stableId,
                                       @RequestBody StableReview review) {
        stableReviewService.reviewStable(review, user.getId(), stableId);
        return ResponseEntity.status(200).body(new ApiResponse("Review added"));
    }

    // Update Review
    @PutMapping("/update/{reviewId}")
    public ResponseEntity<?> updateReview(@AuthenticationPrincipal User user,
                                          @RequestBody StableReview review, @PathVariable Integer reviewId) {
        stableReviewService.updateReview(reviewId, review, user.getId());

        return ResponseEntity.status(200).body(new ApiResponse("Review Updated"));
    }

    // Delete Review
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<?> deleteReview(@AuthenticationPrincipal User user, @PathVariable Integer reviewId) {
        stableReviewService.deleteReview(reviewId, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Review Deleted"));
    }

    // ( #41 of 50 endpoints)
    // The reviews are sorted from best rating to worst rating.
    @GetMapping("/reviews/sorted")
    public ResponseEntity<List<StableReview>> getSortedReviews() {
        return ResponseEntity.ok(stableReviewService.getAllReviewsSortedByRating());
    }

}
