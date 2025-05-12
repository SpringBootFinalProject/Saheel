package com.example.saheel.Controller;

import com.example.saheel.Api.ApiResponse;
import com.example.saheel.Model.CourseEnrollment;
import com.example.saheel.Model.User;
import com.example.saheel.Service.CourseEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/saheel/course-enrollment")
public class CourseEnrollmentController {
    private final CourseEnrollmentService courseEnrollmentService;

    // ( #12 of 50 endpoints )
    @GetMapping("/get-course-enrollments/{courseId}")
    public ResponseEntity<List<CourseEnrollment>> getAllCourseEnrollmentByStableOwner(@AuthenticationPrincipal User user, @PathVariable Integer courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseEnrollmentService.getAllCourseEnrollmentByStableOwner(user.getId(), courseId));
    }

    // ( #13 of 50 endpoints )
    @PostMapping("/enroll-to-course/{courseId}")
    public ResponseEntity<ApiResponse> enrollToCourse(@AuthenticationPrincipal User user, @PathVariable Integer courseId) {
        courseEnrollmentService.enrollToCourse(user.getId(), courseId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Customer Enrolled successfully."));
    }

    // ( #14 of 50 endpoints )
    @PutMapping("/cancel-enrollment/{courseEnrollmentId}")
    public ResponseEntity<ApiResponse> cancelEnrollment(@AuthenticationPrincipal User user, @PathVariable Integer courseEnrollmentId){
        courseEnrollmentService.cancelEnrollment(user.getId(), courseEnrollmentId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Enrollment canceled successfully."));

    }
    // ( #15 of 50 endpoints )
    @GetMapping("/get-canceled-enrollments/{stableId}")
    public ResponseEntity<List<CourseEnrollment>> getCanceledCourseEnrollment(@AuthenticationPrincipal User user, @PathVariable Integer stableId){
        return ResponseEntity.status(HttpStatus.OK).body(courseEnrollmentService.getCanceledEnrollments(user.getId(), stableId));
    }
}
