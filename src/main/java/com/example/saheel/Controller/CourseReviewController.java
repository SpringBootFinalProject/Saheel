package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.CourseReview;
import com.example.saheel.Model.User;
import com.example.saheel.Service.CourseReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/saheel/course-review")
public class CourseReviewController {
    private final CourseReviewService courseReviewService;

    // ( #20 of 50 endpoints ) Ayman
    @GetMapping("/get-course-reviews/{courseId}")
    public ResponseEntity<List<CourseReview>> getCourseReviews(@PathVariable Integer courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseReviewService.getAllCourseReviews(courseId));
    }
    // ( #21 of 50 endpoints ) Ayman
    @PostMapping("/review-course-by-customer/{courseId}")
    public ResponseEntity<ApiResponse> ReviewCourseByCustomer(@AuthenticationPrincipal User user, @PathVariable Integer courseId,
                                                              @RequestBody CourseReview courseReview) {
        courseReviewService.reviewCourseByCustomer(user.getId(), courseId, courseReview);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Review added successfully."));
    }

    @PutMapping("/update-course-review/{courseReviewId}")
    public ResponseEntity<ApiResponse> updateCourseReview(@AuthenticationPrincipal User user, @RequestBody CourseReview courseReview,
                                                          @PathVariable Integer courseReviewId) {
        courseReviewService.updateReview(user.getId(), courseReviewId, courseReview);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Review updated successfully."));
    }
}
