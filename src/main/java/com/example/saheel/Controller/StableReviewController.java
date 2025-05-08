package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.StableReview;
import com.example.saheel.Repository.UserRepository;
import com.example.saheel.Service.StableReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/saheel/tableReview")
@RequiredArgsConstructor
public class StableReviewController {
    private final StableReviewService stableReviewService;
    private final UserRepository userRepository;

    //  add Review of  Stable
    // TODO:  @AuthenticationPrincipal User user
    @PostMapping("/add/{ownerId}/{stableId}")
    public ResponseEntity<?> addReview(@PathVariable Integer ownerId,
                                       @PathVariable Integer stableId,
                                       @RequestBody StableReview review) {
        stableReviewService.addReview(review, ownerId, stableId);
//    TODO:    stableReviewService.addReview(review, ownerId, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Review added"));
    }

    // Get All Reviews
    // TODO:  @AuthenticationPrincipal User user
    @GetMapping("/get")
    public ResponseEntity<?> getAllReviews() {
        return ResponseEntity.status(200).body(stableReviewService.getAllStableReviews());
    }


    // Update Review
    // TODO:  @AuthenticationPrincipal User user
    @PutMapping("/update/{reviewId}/{ownerId}")
    public ResponseEntity<?> updateReview(@PathVariable Integer reviewId,
                                          @RequestBody StableReview review, @PathVariable Integer ownerId) {
        stableReviewService.updateReview(reviewId, review, ownerId);
        //   TODO:      stableReviewService.updateReview(reviewId, review ,user.getId());

        return ResponseEntity.status(200).body(new ApiResponse("Review Updated"));
    }

    // Delete Review
    // TODO:  @AuthenticationPrincipal User user
    @DeleteMapping("/delete/{reviewId}/{ownerId}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer reviewId, @PathVariable Integer ownerId) {
        stableReviewService.deleteReview(reviewId, ownerId);
//   TODO     stableReviewService.deleteReview(reviewId, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Review Deleted"));
    }

}
